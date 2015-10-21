package otakuplus.straybird.othellogameserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import otakuplus.straybird.othellogameserver.models.*;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateGameTables;
import otakuplus.straybird.othellogameserver.network.OthelloGameSocketIOServer;
import otakuplus.straybird.othellogameserver.network.SendMessage;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
public class GameTableController {

    @Autowired
    private OthelloGameSocketIOServer othelloGameSocketIOServer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameTableRepository gameTableRepository;

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/enter", method = RequestMethod.POST)
    public void enterGameTable(@PathVariable Long gameTableId,@PathVariable Long seatId, @RequestBody Long userId)
    {
        User user = userRepository.findOne(userId);
        UserInformation userInformation = user.getUserInformation();
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if(user != null && gameTable != null){
            if(seatId == 0L){
                gameTable.setPlayerA(user);
            }else if(seatId == 1L){
                gameTable.setPlayerB(user);
            }
            String socketIOId = user.getSocketIOId();
            gameTableRepository.save(gameTable);if(socketIOId != null){
                othelloGameSocketIOServer.joinClientToRoom(user.getSocketIOId(),
                        OthelloGameSocketIOServer.GAME_TABLE_ROOM + gameTableId);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setNickname("[Othello Server]");
                sendMessage.setMessage(userInformation.getNickname()+"进入游戏房间");
                sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                sendMessage.setRoomName(OthelloGameSocketIOServer.GAME_TABLE_ROOM+gameTableId);
                othelloGameSocketIOServer.sendMessage(sendMessage);
                NotifyUpdateGameTables notifyUpdateGameTables = new NotifyUpdateGameTables();
                notifyUpdateGameTables.setRoomName(OthelloGameSocketIOServer.GAME_HALL_ROOM);
                othelloGameSocketIOServer.notifyUpdateGameTableList(notifyUpdateGameTables);
            }
            System.out.println("room name"+OthelloGameSocketIOServer.GAME_TABLE_ROOM+gameTableId);
        }
    }

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/leave", method = RequestMethod.POST)
    public void leaveGameTable(@PathVariable Long gameTableId, @PathVariable Long seatId, @RequestBody Long userId){
        User user = userRepository.findOne(userId);
        UserInformation userInformation = user.getUserInformation();
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if(user != null && gameTable != null){
            if(seatId == 0L){
                gameTable.setPlayerA(null);
            }else if(seatId == 1L){
                gameTable.setPlayerB(null);
            }
            gameTableRepository.save(gameTable);
            String socketIOId = user.getSocketIOId();
            gameTableRepository.save(gameTable);if(socketIOId != null){
                othelloGameSocketIOServer.leaveClientFromRoom(user.getSocketIOId(),
                        OthelloGameSocketIOServer.GAME_TABLE_ROOM + gameTableId);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setNickname("[Othello Server]");
                sendMessage.setMessage(userInformation.getNickname()+"离开游戏房间");
                sendMessage.setSendTime(ZonedDateTime.now(ZoneId.of("GMT+8")).toString());
                sendMessage.setRoomName(OthelloGameSocketIOServer.GAME_TABLE_ROOM+gameTableId);
                othelloGameSocketIOServer.sendMessage(sendMessage);
                NotifyUpdateGameTables notifyUpdateGameTables = new NotifyUpdateGameTables();
                notifyUpdateGameTables.setRoomName(OthelloGameSocketIOServer.GAME_HALL_ROOM);
                othelloGameSocketIOServer.notifyUpdateGameTableList(notifyUpdateGameTables);
            }
        }
    }
}
