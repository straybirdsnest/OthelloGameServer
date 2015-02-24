package otakuplus.straybird.othellogameserver.network;

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

public class OthelloGameServerListner extends Listener {
	private Server server;
	public OthelloGameServerListner(Server server){
		this.server = server;
	}
	
	public void received(Connection connection, Object object) {
		System.out.println("Connect to server");
		if (object instanceof User) {
			User user = (User) object;
			String username = user.getUsername();
			String password = user.getPassword();

			if (username == null || password == null) {
				return;
			} else {
				Session session = HibernateUtil.getSessionFactory()
						.openSession();
				List<User> result = session.createCriteria(User.class)
						.add(Restrictions.eq("username", username))
						.add(Restrictions.eq("password", password)).list();
				session.close();
				if (result.size() > 0) {
					Iterator<User> usersIterator = result.iterator();
					User resultUser = null;
					while (usersIterator.hasNext()) {
						resultUser = usersIterator.next();
						System.out.println("Send user information");
						server.sendToTCP(connection.getID(), resultUser);
					}
				}
			}
		}else if(object instanceof UserInformation){
			
		}
	}

	public void disconnected(Connection connection) {

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
