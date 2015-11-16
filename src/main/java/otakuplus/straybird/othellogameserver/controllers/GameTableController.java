package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import otakuplus.straybird.othellogameserver.daos.GameTableRepository;
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.GameTable;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserInformation;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateGameTables;
import otakuplus.straybird.othellogameserver.network.SendMessage;
import otakuplus.straybird.othellogameserver.services.SocketIOService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
public class GameTableController {

    @Autowired
    SocketIOService socketIOService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameTableRepository gameTableRepository;

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/enter", method = RequestMethod.POST)
    public void enterGameTable(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        UserInformation userInformation = user.getUserInformation();
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user != null && gameTable != null) {
            if (seatId == 0L &&
                    (gameTable.getPlayerA() == null ||
                            (gameTable.getPlayerA() != null && gameTable.getPlayerA().getUserId() != userId))) {
                gameTable.setPlayerA(user);
            } else if (seatId == 1L &&
                    (gameTable.getPlayerB() == null ||
                            (gameTable.getPlayerB() != null && gameTable.getPlayerB().getUserId() != userId))) {
                gameTable.setPlayerB(user);
            }
            String socketIOId = user.getSocketIOId();
            gameTableRepository.save(gameTable);
            if (socketIOId != null) {
                socketIOService.joinClientToRoom(user.getSocketIOId(),
                        SocketIOService.GAME_TABLE_ROOM + gameTableId);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setNickname("[Othello Server]");
                sendMessage.setMessage(userInformation.getNickname() + "进入游戏房间");
                sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                sendMessage.setRoomName(SocketIOService.GAME_TABLE_ROOM + gameTableId);
                socketIOService.sendMessage(sendMessage);
                NotifyUpdateGameTables notifyUpdateGameTables = new NotifyUpdateGameTables();
                notifyUpdateGameTables.setRoomName(SocketIOService.GAME_HALL_ROOM);
                socketIOService.notifyUpdateGameTableList(notifyUpdateGameTables);
            }
            System.out.println("room name" + SocketIOService.GAME_TABLE_ROOM + gameTableId);
        }
    }

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/leave", method = RequestMethod.POST)
    public void leaveGameTable(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        UserInformation userInformation = user.getUserInformation();
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user != null && gameTable != null) {
            if (seatId == 0L) {
                gameTable.setPlayerA(null);
            } else if (seatId == 1L) {
                gameTable.setPlayerB(null);
            }
            gameTableRepository.save(gameTable);
            String socketIOId = user.getSocketIOId();
            gameTableRepository.save(gameTable);
            if (socketIOId != null) {
                socketIOService.leaveClientFromRoom(user.getSocketIOId(),
                        SocketIOService.GAME_TABLE_ROOM + gameTableId);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setNickname("[Othello Server]");
                sendMessage.setMessage(userInformation.getNickname() + "离开游戏房间");
                sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                sendMessage.setRoomName(SocketIOService.GAME_TABLE_ROOM + gameTableId);
                socketIOService.sendMessage(sendMessage);
                NotifyUpdateGameTables notifyUpdateGameTables = new NotifyUpdateGameTables();
                notifyUpdateGameTables.setRoomName(SocketIOService.GAME_HALL_ROOM);
                socketIOService.notifyUpdateGameTableList(notifyUpdateGameTables);
            }
        }
    }
}
