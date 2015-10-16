package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserOnline;
import otakuplus.straybird.othellogameserver.models.UserOnlineRepository;
import otakuplus.straybird.othellogameserver.models.UserRepository;
import otakuplus.straybird.othellogameserver.network.OthelloGameSocketIOServer;

@RestController
public class GameHallController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserOnlineRepository userOnlineRepository;
    @Autowired
    private OthelloGameSocketIOServer othelloGameSocketIOServer;

    @RequestMapping(value = "/api/gamehall/enter", method = RequestMethod.POST)
    public void enterGameHall(@RequestBody Long userId){
        if(userId != null) {
            User user = userRepository.findOne(userId);
            UserOnline userOnline = userOnlineRepository.findOne(userId);
            if (user != null && userOnline != null) {
                userOnline.setOnlineState(UserOnline.ONLINE);
                userOnlineRepository.save(userOnline);
                String socketIOId = user.getSocketIOId();
                if(socketIOId != null){
                    othelloGameSocketIOServer.joinClientToRoom(socketIOId, OthelloGameSocketIOServer.GAME_HALL_ROOM);
                }
            }
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
