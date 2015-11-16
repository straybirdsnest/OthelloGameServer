package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.models.User;

@RestController
public class RegisterController {
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void userRegister(@RequestBody User user) {

    }
}
