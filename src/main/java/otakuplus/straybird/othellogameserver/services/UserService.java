package otakuplus.straybird.othellogameserver.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.SecurityUser;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.network.Login;

@Service
public class UserService {

    static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    public User login(Login login) {
        if (login.getUsername() == null || login.getPassword() == null) {
            return null;
        }
        User user = userRepository.findOneByUsername(login.getUsername());
        if (user != null && user.getPassword().equals(login.getPassword()) && user.getIsActive()) {
            SecurityUser securityUser = new SecurityUser(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            if (login.getSocketIOId() != null) {
                // Save socketIOId
                user.setSocketIOId(login.getSocketIOId());
                logger.debug("User " + login.getUsername() + " with socketIO client");
            }
            logger.debug("User " + login.getUsername() + " login");
            userRepository.save(user);
            return user;
        }
        return null;
    }
}
