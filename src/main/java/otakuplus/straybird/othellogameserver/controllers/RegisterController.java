package otakuplus.straybird.othellogameserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.daos.UserGroupRepository;
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.*;

import java.time.Instant;
import java.util.Date;

@RestController
public class RegisterController {
    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserGroupRepository userGroupRepository;

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public ResponseEntity<?> userRegister(@RequestBody Register register) {
        logger.debug("regsiter with username" + register.getUsername());
        logger.debug("register with" + register.getPassword());
        User existUser = userRepository.findOneByUsername(register.getUsername());
        if (existUser != null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        UserGroup userGroup = userGroupRepository.findOneByUserGroupName("ROLE_USER");
        User newUser = new User();
        newUser.setUsername(register.getUsername());
        newUser.setPassword(register.getPassword());
        newUser.setEmailAddress(register.getEmailAddress());

        UserInformation userInformation = new UserInformation();
        userInformation.setNickname(register.getUsername());
        userInformation.setBirthday(Date.from(Instant.parse("1990-01-01T00:00:00.00Z")));
        userInformation.setRankPoints(0);
        userInformation.setGameWins(0);
        userInformation.setGameDraws(0);
        userInformation.setGameLosts(0);
        userInformation.setGender(UserInformation.Gender.SECRET);
        userInformation.setUser(newUser);

        UserOnline userOnline = new UserOnline();
        userOnline.setOnlineState(UserOnline.OFFLINE);
        userOnline.setUser(newUser);

        newUser.setUserInformation(userInformation);
        newUser.setUserOnline(userOnline);

        if (userGroup != null) {
            newUser.setUserGroup(userGroup);
        }
        userRepository.save(newUser);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
