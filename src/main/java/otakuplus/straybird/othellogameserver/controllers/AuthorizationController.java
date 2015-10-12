package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserRepository;
import otakuplus.straybird.othellogameserver.network.AuthorizationCode;
import otakuplus.straybird.othellogameserver.network.Login;

@RestController
public class AuthorizationController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/api/authorization", method = RequestMethod.GET)
    public void firstCrstToken(){
        // empty function will returning HTTP 200 OK.
    }


    @RequestMapping(value = "/api/authorization", method = RequestMethod.POST)
    public AuthorizationCode userAuthorization(@RequestBody Login login){
        User user = userRepository.findByUsername(login.getUsername());
        AuthorizationCode authorizationCode = new AuthorizationCode();
        if(user != null && user.getPassword().equals(login.getPassword())){
            // TODO implements hash code
            authorizationCode.setAuthorizationCode(""+user.getUserId()+user.getUsername());
        }
        return authorizationCode;
    }
}
