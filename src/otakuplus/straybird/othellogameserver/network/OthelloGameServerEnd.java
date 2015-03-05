package otakuplus.straybird.othellogameserver.network;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import otakuplus.straybird.othellogameserver.model.HibernateUtil;
import otakuplus.straybird.othellogameserver.model.User;
import otakuplus.straybird.othellogameserver.model.UserInformation;

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
				if (object instanceof Login) {
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
			session.close();
			if (result.size() > 0) {
				Iterator<User> usersIterator = result.iterator();
				User resultUser = null;
				while (usersIterator.hasNext()) {
					resultUser = usersIterator.next();
					System.out.println(resultUser.getUsername()+"create_at"+resultUser.getCreateTime());
					Log.info("[Othello Game Server]" + resultUser.getUsername()
							+ " login.");
					processResponse.setRequestType(ProcessResponse.LOGIN);
					processResponse.setResponseState(true);
					processResponse.setRequestBody(login);
					kryonetServer
							.sendToTCP(connection.getID(), processResponse);

					// send back user to client
					kryonetServer.sendToTCP(connection.getID(), resultUser);

					SendMessage sendMessage = new SendMessage();
					sendMessage.setNickname("[服务器]");
					sendMessage.setMessage("Login Message.");
					sendMessage.setMessageTime(new Date());
					kryonetServer.sendToAllTCP(sendMessage);
				}
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

		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> result = session.createCriteria(User.class)
				.add(Restrictions.eq("userId", userId)).list();
		session.close();
		if (result.size() > 0) {
			Iterator<User> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				resultUser = resultIterator.next();
				Log.info("[Othello Game Server]" + resultUser.getUsername()
						+ " logout.");
				processResponse.setResponseState(true);
				kryonetServer.sendToTCP(connection.getID(), processResponse);
			}
		}
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
		System.out.println("Get User Information:"+result.size());
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
		session.save(userInformation);
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

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new OthelloGameServerEnd();
	}

}
