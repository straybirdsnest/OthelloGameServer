package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {
    @RequestMapping(value = "/api/csrftoken", method = RequestMethod.GET)
    public void firstCrstToken() {
        // empty function will returning HTTP 200 OK.
    }
}
