package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.models.*;
import otakuplus.straybird.othellogameserver.network.OthelloGameSocketIOServer;
import otakuplus.straybird.othellogameserver.network.SendMessage;

import java.time.ZoneId;
import java.time.ZonedDateTime;

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
            UserOnline userOnline = user.getUserOnline();
            UserInformation userInformation = user.getUserInformation();
            if (user != null && userOnline != null) {
                userOnline.setOnlineState(UserOnline.ONLINE);
                userOnlineRepository.save(userOnline);
                String socketIOId = user.getSocketIOId();
                if(socketIOId != null){
                    othelloGameSocketIOServer.joinClientToRoom(socketIOId, OthelloGameSocketIOServer.GAME_HALL_ROOM);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setNickname("[Othello Server]");
                    sendMessage.setMessage(userInformation.getNickname()+"进入游戏大厅");
                    sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                    sendMessage.setRoomName(OthelloGameSocketIOServer.GAME_HALL_ROOM);
                    othelloGameSocketIOServer.sendMessage(sendMessage);
                }
            }
        }
    }

    @RequestMapping(value = "/api/gamehall/leave", method = RequestMethod.POST)
    public void leaveGameHall(@RequestBody Long userId)
    {
        User user = userRepository.findOne(userId);
        UserOnline userOnline = user.getUserOnline();
        UserInformation userInformation = user.getUserInformation();
        if(user != null && userOnline != null){
            userOnline.setOnlineState(UserOnline.OFFLINE);
            userOnlineRepository.save(userOnline);
            String socketIOId = user.getSocketIOId();
            if(socketIOId != null){
                othelloGameSocketIOServer.leaveClientFromRoom(socketIOId, OthelloGameSocketIOServer.GAME_HALL_ROOM);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setNickname("[Othello Server]");
                sendMessage.setMessage(userInformation.getNickname()+"离开游戏大厅");
                sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                sendMessage.setRoomName(OthelloGameSocketIOServer.GAME_HALL_ROOM);
            }
        }
    }
}
