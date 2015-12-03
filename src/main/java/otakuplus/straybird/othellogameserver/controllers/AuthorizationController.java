package otakuplus.straybird.othellogameserver.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.config.json.UserView;
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.Register;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.network.Login;
import otakuplus.straybird.othellogameserver.network.Logout;
import otakuplus.straybird.othellogameserver.services.UserService;

import java.security.Principal;

@RestController
public class AuthorizationController {

    static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/api/authorization", method = RequestMethod.POST)
    public ResponseEntity<?> userAuthorization(@RequestBody Login login) {
        User user = userService.login(login);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public void userLogout(@RequestBody Logout logout) {
        Integer userId = logout.getUserId();
        if (userId != null) {
            User user = userRepository.findOne(userId);
            if (user != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                SecurityContextHolder.clearContext();
                logger.info("Logout with {}", authentication);
            }
        }
    }

    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    @JsonView(UserView.WebUser.class)
    public ResponseEntity<?> currentUser(Principal principal) {
        logger.debug("principal" + principal.getName());
        String username = principal.getName();
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/emailAddress", method = RequestMethod.POST)
    public ResponseEntity<?> getEncodeEmailAddress(@RequestBody String username) {
        logger.debug("find email for username " + username);
        if (username == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findOneByUsername(username);
        String email = user.getEmailAddress();
        return new ResponseEntity<>(email, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/forgetPassword", method = RequestMethod.POST)
    public ResponseEntity<?> forgetPassword(@RequestBody Register register) {
        logger.debug("call with regiseter " + register.getUsername());
        String username = register.getUsername();
        String email = register.getEmailAddress();
        String password = register.getPassword();
        if (username == null || email == null || password == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.findOneByUsername(username);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (user.getEmailAddress().equals(email)) {
            user.setPassword(password);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
