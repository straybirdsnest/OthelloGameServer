package otakuplus.straybird.othellogameserver.controllers;

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
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.network.Login;
import otakuplus.straybird.othellogameserver.network.Logout;
import otakuplus.straybird.othellogameserver.services.UserService;

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
                logger.info("Logout with {}", authentication);
            }
        }

    }
}
