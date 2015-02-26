package otakuplus.straybird.othellogameserver.network;

import java.io.IOException;
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

	public OthelloGameServerEnd() throws IOException {
		Server server = new Server() {
			protected Connection newConnection() {
				return new OthelloGameConnection();
			}
		};

		KryonetUtil.register(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				System.out.println("Connect to server");
				if (object instanceof Login) {
					Login login = (Login) object;
					doLogin(connection, login);
				} else if (object instanceof Logout) {
					Logout logout = (Logout) object;
					doLogout(connection, logout);
				} else if (object instanceof GetUserInformation) {
					GetUserInformation getUserInformation = (GetUserInformation) object;
					doGetUserInformation(connection, getUserInformation);
				}
			}

			public void disconnected(Connection connection) {
			}
		});

		server.bind(KryonetUtil.SERVER_PORT);
		server.start();
	}

	public void doLogin(Connection connection, Login login) {
		String username = login.getUsername();
		String password = login.getPassword();

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
					Log.info("[Othello Game Server]" + resultUser.getUsername()
							+ " login.");
				}
			}
		}
	}

	public void doLogout(Connection connection, Logout logout) {
		int userId = logout.getUserId();
		User resultUser = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> result = session.createCriteria(User.class)
				.add(Restrictions.eq("", userId)).list();
		session.close();
		if (result.size() > 0) {
			Iterator<User> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				resultUser = resultIterator.next();
				Log.info("[Othello Game Server]" + resultUser.getUsername()
						+ " logout.");
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
				.add(Restrictions.eq("", userId)).list();
		if (result.size() > 0) {
			Iterator<UserInformation> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				userInformation = resultIterator.next();

			}
		}
		session.close();
	}

	public void doUpdateUserInformation(Connection connection,
			UpdateUserInformation update) {

	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new OthelloGameServerEnd();
	}

}
