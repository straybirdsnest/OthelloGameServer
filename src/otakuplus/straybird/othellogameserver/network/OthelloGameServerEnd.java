package otakuplus.straybird.othellogameserver.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import otakuplus.straybird.othellogameserver.model.GameTable;
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
		kryonetServer = new Server();

		KryonetUtil.register(kryonetServer);

		kryonetServer.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof RegisterUser) {
					RegisterUser registerUser = (RegisterUser) object;
					doRegisterUser(connection, registerUser);
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
				} else if (object instanceof GetGameTableList) {
					GetGameTableList getGameTableList = (GetGameTableList) object;
					doGetGameTableList(connection, getGameTableList);
				} else if (object instanceof UpdateGameTable) {
					UpdateGameTable updateGameTable = (UpdateGameTable) object;
					doUpdateGameTable(connection, updateGameTable);
				}
			}

			public void disconnected(Connection connection) {

			}
		});

		kryonetServer.bind(KryonetUtil.SERVER_PORT);
		kryonetServer.start();
	}

	public void doRegisterUser(Connection connection, RegisterUser registerUser) {
		if (registerUser.getUser() != null
				&& registerUser.getUserInformation() != null) {
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			User user = registerUser.getUser();
			UserInformation userInformation = registerUser.getUserInformation();
			UserOnline userOnline = new UserOnline();
			userOnline.setOnlineState(UserOnline.OFFLINE);

			/*
			 * very important to set userInformation's id as in the database,
			 * userInformation table doesn't use auto increment and does use
			 * foreign key. The same as userOnline.
			 */
			int userId = (int) session.save(user);
			userInformation.setUserId(userId);
			userOnline.setUserId(userId);
			session.save(userInformation);
			session.save(userOnline);
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

						// send login message to all users
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
								// send login userinformatio to other users
								kryonetServer.sendToAllExceptTCP(
										connection.getID(), userInformation);
							}
						}
					} catch (Exception e) {
						if (transaction != null) {
							transaction.rollback();
							processResponse.setResponseState(false);
							processResponse
									.setRequestType(ProcessResponse.LOGIN);
							processResponse.setRequestBody(login);
							kryonetServer.sendToTCP(connection.getID(),
									processResponse);
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
				Transaction transaction = null;
				try {
					transaction = session.beginTransaction();
					UserOnline userOnline = new UserOnline();
					userOnline.setUserId(resultUser.getUserId());
					userOnline.setOnlineState(UserOnline.OFFLINE);
					session.update(userOnline);
					transaction.commit();

					// send feedback
					processResponse.setResponseState(true);
					kryonetServer
							.sendToTCP(connection.getID(), processResponse);
					// send logout message to other users
					List<UserInformation> userInformationList = session
							.createCriteria(UserInformation.class)
							.add(Restrictions.eq("userId",
									resultUser.getUserId())).list();
					if (userInformationList.size() > 0) {
						UserInformation userInformation = null;
						Iterator<UserInformation> userInformationIterator = userInformationList
								.iterator();
						while (userInformationIterator.hasNext()) {
							userInformation = userInformationIterator.next();
							broadcastMessageExcept(connection.getID(),
									userInformation.getNickname() + "退出了游戏大厅。");
						}
						// send logout to other users
						kryonetServer.sendToAllExceptTCP(connection.getID(),
								logout);
					}
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();

						processResponse.setResponseState(false);
						processResponse.setRequestType(ProcessResponse.LOGOUT);
						processResponse.setRequestBody(logout);
						kryonetServer.sendToTCP(connection.getID(),
								processResponse);
					}
				}
			}
		} else {
			processResponse.setResponseState(false);
			processResponse.setRequestType(ProcessResponse.LOGOUT);
			processResponse.setRequestBody(logout);
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
		session.update(userInformation);
		transaction.commit();
		session.close();
	}

	public void doGetUserOnlineList(Connection connection,
			GetUserOnlineList getUserOnlineList) {
		int fromNumber = getUserOnlineList.getFromNumber();
		int maxNumber = getUserOnlineList.getMaxNumber();
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setRequestType(ProcessResponse.GET_USER_ONLINE_LIST);
		processResponse.setRequestBody(getUserOnlineList);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			// use HQL instead of Criteria
			Query query = session
					.createQuery("select count(userInformation) from UserInformation userInformation,UserOnline userOnline where userInformation.userId = userOnline.userId and userOnline.onlineState <>"
							+ UserOnline.OFFLINE);
			long countNumber = ((Number) query.iterate().next()).longValue();
			if (countNumber > 0) {
				query = session
						.createQuery("select userInformation from UserInformation userInformation, UserOnline userOnline where userInformation.userId = userOnline.userId and userOnline.onlineState <> "
								+ UserOnline.OFFLINE
								+ " order by userInformation.userId asc");
				if (fromNumber < countNumber) {
					query.setFirstResult(fromNumber);
					if (maxNumber > 50) {
						query.setMaxResults(50);
					} else {
						query.setMaxResults(maxNumber);
					}
					ArrayList<UserInformation> queryResultList = (ArrayList<UserInformation>) query
							.list();
					if (queryResultList.size() > 0) {
						kryonetServer.sendToTCP(connection.getID(),
								queryResultList);
					}
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
			processResponse.setResponseState(false);
			kryonetServer.sendToTCP(connection.getID(), processResponse);
		}
		session.close();
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

	public void doGetGameTableList(Connection connection,
			GetGameTableList getGameTableList) {
		int fromNumber = getGameTableList.getFromNumber();
		int maxNumber = getGameTableList.getMaxNumber();
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setRequestType(ProcessResponse.GET_GAME_TABLE_LIST);
		processResponse.setRequestBody(getGameTableList);
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {

			Query query = session
					.createQuery("select count(gameTable) from GameTable gameTable where gameTable.playerAId is not null or gameTable.playerBId is not null ");
			long gameTableNumber = ((Number) query.iterate().next())
					.longValue();
			if (gameTableNumber > 0) {
				if (fromNumber < gameTableNumber) {
					Criteria gameTableCriteria = session
							.createCriteria(GameTable.class);
					gameTableCriteria.add(Restrictions.isNotNull("playerAId"));
					gameTableCriteria.add(Restrictions.isNotNull("playerBId"));
					gameTableCriteria.setFirstResult(fromNumber);
					gameTableCriteria.setMaxResults(maxNumber);
					ArrayList<GameTable> gameTableList = (ArrayList<GameTable>) gameTableCriteria
							.list();
					kryonetServer.sendToTCP(connection.getID(), gameTableList);
				}
			} else if (gameTableNumber == 0) {
				ArrayList<GameTable> gameTableList = new ArrayList<GameTable>();
				kryonetServer.sendToTCP(connection.getID(), gameTableList);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			processResponse.setResponseState(false);
			kryonetServer.sendToTCP(connection.getID(), processResponse);
		}
		session.close();
	}

	public void doUpdateGameTable(Connection connection,
			UpdateGameTable updateGameTable) {
		int userId = updateGameTable.getUserId();
		int tableId = updateGameTable.getGameTableId();
		int tablePosition = updateGameTable.getTablePosition();
		ProcessResponse processResponse = new ProcessResponse();
		processResponse.setRequestBody(updateGameTable);
		processResponse.setRequestType(ProcessResponse.UPDATE_GAME_TABLE);
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		GameTable gameTable = null;
		ArrayList<GameTable> gameTableList = null;
		Iterator<GameTable> gameTableIterator = null;
		boolean proceFlag = true;
		try {
			transaction = session.beginTransaction();
			if (updateGameTable.getAction() == UpdateGameTable.ACTION_TAKE) {
				gameTableList = (ArrayList<GameTable>) session
						.createCriteria(GameTable.class)
						.add(Restrictions.eq("gameTableId", tableId)).list();
				if (gameTableList.size() > 0) {
					gameTableIterator = gameTableList.iterator();
					while (gameTableIterator.hasNext()) {
						gameTable = gameTableIterator.next();
						if (tablePosition == 1) {
							if (gameTable.getPlayerAId() == null) {
								gameTable.setPlayerAId(userId);
								session.update(gameTable);
								kryonetServer.sendToTCP(connection.getID(),
										gameTable);
							} else {
								proceFlag = false;
							}
						}
						if (tablePosition == 2) {
							if (gameTable.getPlayerBId() == null) {
								gameTable.setPlayerBId(userId);
								session.update(gameTable);
								kryonetServer.sendToTCP(connection.getID(),
										gameTable);
							} else {
								proceFlag = false;
							}
						}
					}
				}
			}
			if (updateGameTable.getAction() == UpdateGameTable.ACTION_LEFT) {
				gameTableList = (ArrayList<GameTable>) session
						.createCriteria(GameTable.class)
						.add(Restrictions.eq("gameTableId", tableId)).list();
				if (gameTableList.size() > 0) {
					gameTableIterator = gameTableList.iterator();
					while (gameTableIterator.hasNext()) {
						boolean findPosition = false;
						gameTable = gameTableIterator.next();
						if (tablePosition == 1) {
							if (gameTable.getPlayerAId() == userId) {
								gameTable.setPlayerAId(null);
								session.update(gameTable);
								processResponse.setResponseState(true);
								kryonetServer.sendToTCP(connection.getID(),
										processResponse);
							} else {
								proceFlag = false;
							}
						}
						if (tablePosition == 2) {
							if (gameTable.getPlayerBId() == userId) {
								gameTable.setPlayerBId(null);
								session.update(gameTable);
								processResponse.setResponseState(true);
								kryonetServer.sendToTCP(connection.getID(),
										processResponse);
							} else {
								proceFlag = false;
							}
						}
					}
				}
			}
			transaction.commit();
			if (proceFlag == true) {

			} else {
				processResponse.setResponseState(false);
				kryonetServer.sendToTCP(connection.getID(), processResponse);
			}
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}
			exception.printStackTrace();
			processResponse.setResponseState(false);
			kryonetServer.sendToTCP(connection.getID(), processResponse);
		}
		session.close();
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new OthelloGameServerEnd();
	}

}
