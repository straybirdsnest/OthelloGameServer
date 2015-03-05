package otakuplus.straybird.othellogameserver.model;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class HibernateTest {

	public static void main(String[] args) {
		UserInformation userInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<UserInformation> result = session
				.createCriteria(UserInformation.class)
				.add(Restrictions.eq("userId",1)).list();
		System.out.println("Get User Information:"+result.size());
		if (result.size() > 0) {
			Iterator<UserInformation> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				userInformation = resultIterator.next();
				System.out.println("Username: "+userInformation.getUser().getUsername()+",UserInfor: "+userInformation.getNickname());
			}
		}
		session.close();
	}
}
