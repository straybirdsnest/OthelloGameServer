package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserDao;

@RestController
public class UserController {

	@Autowired
	private UserDao userDao;
	
	@RequestMapping(value = "/User/{userId}", method = RequestMethod.GET)
	public User user(@PathVariable("userId") long userId) {
		return userDao.findOne(userId);
	}
	
}
