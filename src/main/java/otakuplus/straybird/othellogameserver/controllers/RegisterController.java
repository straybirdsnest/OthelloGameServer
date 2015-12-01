package otakuplus.straybird.othellogameserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {
    private final static Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public void userRegister(@RequestBody String username) {
        logger.debug("regsiter with" + username);
    }
}
