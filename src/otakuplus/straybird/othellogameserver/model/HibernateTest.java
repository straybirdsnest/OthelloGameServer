package otakuplus.straybird.othellogameserver.model;

import java.util.ArrayList;

import org.hibernate.Session;

public class HibernateTest {

	public static void main(String[] args) {

		UserInformation userInformation = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		/*
		List<UserInformation> result = session
				.createCriteria(UserInformation.class)
				.add(Restrictions.eq("userId", 1)).list();
		System.out.println("Get User Information:" + result.size());
		if (result.size() > 0) {
			Iterator<UserInformation> resultIterator = result.iterator();
			while (resultIterator.hasNext()) {
				userInformation = resultIterator.next();
				System.out.println("UserInfor: "
						+ userInformation.getNickname());
			}
		}
		*/
		/*
		Transaction transaction = session.beginTransaction();
		// Hibernate can auto set id when id = 0 or null
		User user = new User();
		userInformation = new UserInformation();
		user.setUsername("jack");
		user.setPassword("jack");
		user.setEmailAddress("jack@test.com");
		user.setIsActive(true);
		
		userInformation.setNickname("another Jack");
		userInformation.setSexuality(UserInformation.SEXUALITY_MALE);
		userInformation.setBirthday(new Date());
		userInformation.setGameWins(10);
		userInformation.setGameDraws(3);
		userInformation.setGameLosts(20);
		userInformation.setRankPoints(23);
		 
		session.save(user);
		session.save(userInformation);
		transaction.commit();
		*/
		ArrayList<UserInformation> queryResult = (ArrayList<UserInformation>) session.createQuery(
				"select userInformation from UserInformation userInformation, UserOnline userOnline where userOnline.onlineState <> "
						+ UserOnline.OFFLINE).list();
		System.out.println(queryResult.size());
		session.close();
	}
}
