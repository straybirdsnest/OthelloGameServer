package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import otakuplus.straybird.othellogameserver.daos.UserOnlineRepository;
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserInformation;
import otakuplus.straybird.othellogameserver.models.UserOnline;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateUserInformations;
import otakuplus.straybird.othellogameserver.network.SendMessage;
import otakuplus.straybird.othellogameserver.services.SocketIOService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
public class GameHallController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserOnlineRepository userOnlineRepository;
    @Autowired
    SocketIOService socketIOService;

    @RequestMapping(value = "/api/gameHall/enter", method = RequestMethod.POST)
    public void enterGameHall(@RequestBody Integer userId) {
        if (userId != null) {
            User user = userRepository.findOne(userId);
            UserOnline userOnline = user.getUserOnline();
            UserInformation userInformation = user.getUserInformation();
            if (user != null && userOnline != null) {
                userOnline.setOnlineState(UserOnline.ONLINE);
                userOnlineRepository.save(userOnline);
                String socketIOId = user.getSocketIOId();
                if (socketIOId != null) {
                    socketIOService.joinClientToRoom(socketIOId, SocketIOService.GAME_HALL_ROOM);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setNickname("[Othello Server]");
                    sendMessage.setMessage(userInformation.getNickname() + "进入游戏大厅");
                    sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                    sendMessage.setRoomName(SocketIOService.GAME_HALL_ROOM);
                    socketIOService.sendMessage(sendMessage);
                    NotifyUpdateUserInformations notifyUpdateUserInformations = new NotifyUpdateUserInformations();
                    notifyUpdateUserInformations.setRoomName(SocketIOService.GAME_HALL_ROOM);
                    socketIOService.notifyUpdateUserInformationList(notifyUpdateUserInformations);
                }
            }
        }
    }

    @RequestMapping(value = "/api/gameHall/leave", method = RequestMethod.POST)
    public void leaveGameHall(@RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        UserOnline userOnline = user.getUserOnline();
        UserInformation userInformation = user.getUserInformation();
        if (user != null && userOnline != null) {
            userOnline.setOnlineState(UserOnline.OFFLINE);
            userOnlineRepository.save(userOnline);
            String socketIOId = user.getSocketIOId();
            if (socketIOId != null) {
                socketIOService.leaveClientFromRoom(socketIOId, SocketIOService.GAME_HALL_ROOM);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setNickname("[Othello Server]");
                sendMessage.setMessage(userInformation.getNickname() + "离开游戏大厅");
                sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                sendMessage.setRoomName(SocketIOService.GAME_HALL_ROOM);
                NotifyUpdateUserInformations notifyUpdateUserInformations = new NotifyUpdateUserInformations();
                notifyUpdateUserInformations.setRoomName(SocketIOService.GAME_HALL_ROOM);
                socketIOService.notifyUpdateUserInformationList(notifyUpdateUserInformations);
            }
        }
    }
}
