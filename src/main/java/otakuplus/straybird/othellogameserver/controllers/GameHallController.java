package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameHallController {

    @RequestMapping(value = "/api/gamehall", method = RequestMethod.GET)
    public void enterGameHall(){

    }
}
