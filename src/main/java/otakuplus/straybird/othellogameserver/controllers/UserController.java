package otakuplus.straybird.othellogameserver.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserRepository;
import otakuplus.straybird.othellogameserver.models.UserInformation;
import otakuplus.straybird.othellogameserver.models.UserView;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	/*
	@JsonView(UserView.ClientUser.class)
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public User user(@PathVariable("userId") long userId) {
		User user = userRepository.findOne(userId);
		return user;
	}
	*/
	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
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
		
		userRepository.save(user);
		
	}
	
}
