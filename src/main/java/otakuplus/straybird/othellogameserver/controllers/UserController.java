package otakuplus.straybird.othellogameserver.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserDao;
import otakuplus.straybird.othellogameserver.models.UserInformation;
import otakuplus.straybird.othellogameserver.models.UserView;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;
	
	@JsonView(UserView.ClientUser.class)
	@RequestMapping(value = "/User/{userId}", method = RequestMethod.GET)
	public User user(@PathVariable("userId") long userId) {
		User user = userDao.findOne(userId);
		return user;
	}
	
	@RequestMapping(value = "/User/create", method = RequestMethod.GET)
	public void createUser() {
		User user = new User();
		user.setUserId(1L);
		user.setUsername("someuser");
		user.setPassword("somepassword");
		user.setEmailAddress("user@example");
		user.setIsActive(true);
		
		UserInformation userInformation = new UserInformation();
		userInformation.setNickname("somenickname");
		userInformation.setGameWins(0);
		userInformation.setGameLosts(0);
		userInformation.setGameDraws(0);
		userInformation.setBirthday(new Date());
		userInformation.setGender(UserInformation.GENDER_MALE);
		
		user.setUserInformation(userInformation);
		userInformation.setUser(user);
		
		userDao.save(user);
		
	}
	
}
