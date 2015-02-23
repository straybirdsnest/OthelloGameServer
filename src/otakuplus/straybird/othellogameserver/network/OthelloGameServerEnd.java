package otakuplus.straybird.othellogameserver.network;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import otakuplus.straybird.othellogameserver.model.HibernateUtil;
import otakuplus.straybird.othellogameserver.model.User;

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
		server.start();

		KryonetUtil.register(server);

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof User) {
					User user = (User) object;
					String username = user.getUsername();
					String password = user.getPassword();

					if (user == null || password == null) {
						return;
					} else {
						Session session = HibernateUtil.getSessionFactory()
								.getCurrentSession();
						List<User> result = session.createCriteria(User.class)
								.add(Restrictions.eq("username", username))
								.add(Restrictions.eq("password", password))
								.list();
						if (result.size() > 0) {
							Iterator<User> usersIterator = result.iterator();
							while (usersIterator.hasNext()) {
								System.out.println("Query Result: "
										+ usersIterator.next().getUserId());
							}
						}
					}
				}
			}

			public void disconnected(Connection connection) {

			}
		});
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		OthelloGameServerEnd serverEnd = new OthelloGameServerEnd();
	}

}
