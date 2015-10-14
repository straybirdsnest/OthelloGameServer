package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.models.UserOnline;
import otakuplus.straybird.othellogameserver.models.UserOnlineRepository;

@RestController
public class GameHallController {

    @Autowired
    private UserOnlineRepository userOnlineRepository;

    @RequestMapping(value = "/api/gamehall/enter", method = RequestMethod.POST)
    public void enterGameHall(@RequestBody Long userId){
        UserOnline userOnline = userOnlineRepository.findOne(userId);
        if(userOnline != null){
            userOnline.setOnlineState(UserOnline.ONLINE);
            userOnlineRepository.save(userOnline);
        }
    }

    @RequestMapping(value = "/api/gamehall/leave", method = RequestMethod.POST)
    public void leaveGameHall(@RequestBody Long userId)
    {
        UserOnline userOnline = userOnlineRepository.findOne(userId);
        if(userOnline != null){
            userOnline.setOnlineState(UserOnline.OFFLINE);
            userOnlineRepository.save(userOnline);
        }
    }
}
