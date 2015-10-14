package otakuplus.straybird.othellogameserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import otakuplus.straybird.othellogameserver.network.AuthorizationCode;
import otakuplus.straybird.othellogameserver.network.Login;

@RestController
public class AuthorizationController {

    static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/api/authorization", method = RequestMethod.POST)
    public AuthorizationCode userAuthorization(@RequestBody Login login){
        User user = userRepository.findByUsername(login.getUsername());

        AuthorizationCode authorizationCode = new AuthorizationCode();
        if(user != null && user.getPassword().equals(login.getPassword())){
            // TODO implements hash code
            authorizationCode.setAuthorizationCode(""+user.getUserId()+user.getUsername());
            SecurityUser securityUser = new SecurityUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.info("Logging in with {}", authentication);
        }
        return authorizationCode;
    }
}
