package otakuplus.straybird.othellogameserver.network;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import otakuplus.straybird.othellogameserver.model.HibernateUtil;
import otakuplus.straybird.othellogameserver.model.User;
import otakuplus.straybird.othellogameserver.model.UserInformation;
import otakuplus.straybird.othellogameserver.model.UserOnline;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class OthelloGameServerEnd {

	protected Server kryonetServer;

	public OthelloGameServerEnd() throws IOException {
		kryonetServer = new Server() {
			protected Connection newConnection() {
				return new OthelloGameConnection();
			}
		};

		KryonetUtil.register(kryonetServer);

		kryonetServer.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof RegisterUser) {
					RegisterUser registerUser = (RegisterUser) object;
					doRegisterUser(registerUser);
				} else if (object instanceof Login) {
					Login login = (Login) object;
					doLogin(connection, login);
				} else if (object instanceof Logout) {
					Logout logout = (Logout) object;
					doLogout(connection, logout);
				} else if (object instanceof GetUserInformation) {
					GetUserInformation getUserInformation = (GetUserInformation) object;
					doGetUserInformation(connection, getUserInformation);
				} else if (object instanceof GetUserOnlineList) {
					GetUserOnlineList getUserOnlineList = (GetUserOnlineList) object;
					doGetUserOnlineList(connection, getUserOnlineList);
				} else if (object instanceof SendMessage) {
					SendMessage sendMessage = (SendMessage) object;
					doSendMessage(sendMessage);
				}
			}

			public void disconnected(Connection connection) {
			}
		});

		kryonetServer.bind(KryonetUtil.SERVER_PORT);
		kryonetServer.start();
	}

	public void doRegisterUser(RegisterUser registerUser) {
		if (registerUser.getUser() != null
				&& registerUser.getUserInformation() != null) {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			User user = registerUser.getUser();
			UserInformation userInformation = registerUser.getUserInformation();

			/*
			 * very important to set userInformation's id as in the database,
			 * userInformation table doesn't use auto increment and does use
			 * foreign key.
			 */
			int userId = (int) session.save(user);
			userInformation.setUserId(userId);
			session.save(userInformation);
			transaction.commit();
			session.close();
		}

	}

	public void doLogin(Connection connection, Login login) {
		String username = login.getUsername();
		String password = login.getPassword();
		ProcessResponse processResponse = new ProcessResponse();

		if (username == null || password == null) {
			return;
		} else {
			Session session = HibernateUtil.getSessionFactory().openSession();
			List<User> result = session.createCriteria(User.class)
					.add(Restrictions.eq("username", username))
					.add(Restrictions.eq("password", password)).list();
			if (result.size() > 0) {
				Iterator<User> usersIterator = result.iterator();
				User resultUser = null;
				while (usersIterator.hasNext()) {
					resultUser = usersIterator.next();
					Log.info("[Othello Game Server]" + resultUser.getUsername()
							+ " login.");

					processResponse.setRequestType(ProcessResponse.LOGIN);
					processResponse.setResponseState(true);
					processResponse.setRequestBody(login);
					kryonetServer
							.sendToTCP(connection.getID(), processResponse);
					// update useronline table
					Transaction transaction = null;
					UserOnline userOnline = new UserOnline();
					userOnline.setUserId(resultUser.getUserId());
					System.out.println("login id" + userOnline.getUserId());
					userOnline.setOnlineState(UserOnline.ONLINE);
					try {
						// use save or update in case that row is exist
						transaction = session.beginTransaction();
						session.saveOrUpdate(userOnline);
						transaction.commit();
						// send back user to client
						kryonetServer.sendToTCP(connection.getID(), resultUser);

						// send login message
						List<UserInformation> userInformationList = session
								.createCriteria(UserInformation.class)
								.add(Restrictions.eq("userId",
										resultUser.getUserId())).list();
						if (userInformationList.size() > 0) {
							UserInformation userInformation = null;
							Iterator<UserInformation> userInformationIterator = userInformationList
									.iterator();
							while (userInformationIterator.hasNext()) {
								userInformation = userInformationIterator
										.next();
								broadcastMessage(userInformation.getNickname()
										+ "进入了游戏大厅。");
							}
						}
					} catch (Exception e) {
						if (transaction != null) {
							transaction.rollback();
							e.printStackTrace();
						}
					}
				}
				session.close();

			} else {
				Log.info("[Othello Game Server] Connection "
						+ connection.getID() + " Login failed!");
				processResponse.setResponseState(false);
				processResponse.setRequestType(ProcessResponse.LOGIN);
				processResponse.setRequestBody(login);
				kryonetServer.sendToTCP(connection.getID(), processResponse);
			}
		}
	}

	public void doLogout(Connection connection, Logout logout) {
		int userId = logout.getUserId();
		User resultUser = null;
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setRequestType(ProcessResponse.LOGOUT);
		processResponse.setRequestBody(logout);

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> result = session.createCriteria(User.class)
				.add(Restrictions.eq("userId", userId)).list();
		if (result.size() > 0) {
			Iterator<User> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				resultUser = resultIterator.next();
				Log.info("[Othello Game Server]" + resultUser.getUsername()
						+ " logout.");

				// update useronline table
				Transaction transaction = session.beginTransaction();
				UserOnline userOnline = new UserOnline();
				userOnline.setUserId(resultUser.getUserId());
				userOnline.setOnlineState(UserOnline.OFFLINE);
				session.saveOrUpdate(userOnline);
				transaction.commit();

				// send feedback
				processResponse.setResponseState(true);
				kryonetServer.sendToTCP(connection.getID(), processResponse);

				// send to other user
				List<UserInformation> userInformationList = session
						.createCriteria(UserInformation.class)
						.add(Restrictions.eq("userId", resultUser.getUserId()))
						.list();
				if (userInformationList.size() > 0) {
					UserInformation userInformation = null;
					Iterator<UserInformation> userInformationIterator = userInformationList
							.iterator();
					while (userInformationIterator.hasNext()) {
						userInformation = userInformationIterator.next();
						broadcastMessageExcept(connection.getID(),
								userInformation.getNickname() + "退出了游戏大厅。");
					}
				}
			}
		} else {
			processResponse.setResponseState(false);
			kryonetServer.sendToTCP(connection.getID(), processResponse);
		}
		session.close();
	}

	public void doGetUserInformation(Connection connection,
			GetUserInformation getUserInformation) {
		int userId = getUserInformation.getUserId();
		UserInformation userInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<UserInformation> result = session
				.createCriteria(UserInformation.class)
				.add(Restrictions.eq("userId", userId)).list();
		session.close();
		System.out.println("Get User Information:" + result.size());
		if (result.size() > 0) {
			Iterator<UserInformation> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				userInformation = resultIterator.next();
				kryonetServer.sendToTCP(connection.getID(), userInformation);
			}
		}
	}

	public void doUpdateUserInformation(Connection connection,
			UpdateUserInformation updateInformation) {
		UserInformation userInformation = updateInformation
				.getUserInformation();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(userInformation);
		transaction.commit();
		session.close();
	}

	public void doGetUserOnlineList(Connection connection,
			GetUserOnlineList getUserOnlineList) {

	}

	public void doSendMessage(SendMessage sendMessage) {
		if (sendMessage.getNickname() != null
				&& sendMessage.getMessage() != null
				&& sendMessage.getMessageTime() != null) {
			kryonetServer.sendToAllTCP(sendMessage);
		}
	}

	public void broadcastMessage(String message) {
		if (message != null && message.length() > 0) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setNickname("[服务器]");
			sendMessage.setMessage(message);
			sendMessage.setMessageTime(new Date());
			kryonetServer.sendToAllTCP(sendMessage);
		}
	}

	public void broadcastMessageExcept(int connectId, String message) {
		if (message != null && message.length() > 0) {
			SendMessage sendMessage = new SendMessage();
			sendMessage.setNickname("[服务器]");
			sendMessage.setMessage(message);
			sendMessage.setMessageTime(new Date());
			kryonetServer.sendToAllExceptTCP(connectId, sendMessage);
		}
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new OthelloGameServerEnd();
	}

}
