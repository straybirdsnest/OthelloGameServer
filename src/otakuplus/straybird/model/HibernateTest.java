package otakuplus.straybird.model;

import java.util.Date;
import org.hibernate.Session;

public class HibernateTest {

	public static void main(String[] args) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        User user = new User();
        user.setUsername("TestUser");
        user.setPassword("TestUser");
        user.setEmailAddress("test@localhost.com");
        user.setCreateTime(new Date());
        
        session.save(user);
        session.getTransaction().commit();
        
        HibernateUtil.getSessionFactory().close();
	}

}
