package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import otakuplus.straybird.othellogameserver.models.UserInformation;
import otakuplus.straybird.othellogameserver.models.UserInformationRepository;
import otakuplus.straybird.othellogameserver.models.UserInformationView;

@RestController
public class UserInformationController {

	@Autowired
	private UserInformationRepository userInformationRepository;

	@JsonView(UserInformationView.ClientUserInformation.class)
	@RequestMapping(value = "/userinformation/{userInformationId}", method = RequestMethod.GET)
	public UserInformation userInformation(@PathVariable("userInformationId") long userInformationId) {
		UserInformation userInformation = userInformationRepository.findOne(userInformationId);
		return userInformation;
	}
}
