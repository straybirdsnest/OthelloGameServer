package otakuplus.straybird.othellogameserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.models.SecurityUser;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserRepository;
import otakuplus.straybird.othellogameserver.network.Login;
import otakuplus.straybird.othellogameserver.network.Logout;

@RestController
public class AuthorizationController {

    static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/api/authorization", method = RequestMethod.POST)
    public ResponseEntity<?> userAuthorization(@RequestBody Login login){
        User user = userRepository.findByUsername(login.getUsername());

        if(user != null && user.getPassword().equals(login.getPassword()) && login.getSocketIOId() != null){
            // TODO implements hash code
            SecurityUser securityUser = new SecurityUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Login  with {}", authentication);
            // Save socketIOId
            user.setSocketIOId(login.getSocketIOId());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        logger.info("Login fail with "+login.getUsername());
        String error = "login fail.";
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/api/logout", method = RequestMethod.POST)
    public void userLogout(@RequestBody Logout logout){
        Long userId = logout.getUserId();
        if(userId != null){
            User user = userRepository.findOne(userId);
            if(user != null ){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                logger.info("Logout with {}", authentication);
            }
        }

    }
}
