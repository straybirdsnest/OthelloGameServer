package otakuplus.straybird.othellogameserver.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import otakuplus.straybird.othellogameserver.daos.GameRecordRepository;
import otakuplus.straybird.othellogameserver.daos.GameTableRepository;
import otakuplus.straybird.othellogameserver.daos.UserInformationRepository;
import otakuplus.straybird.othellogameserver.daos.UserRepository;
import otakuplus.straybird.othellogameserver.models.GameTable;
import otakuplus.straybird.othellogameserver.models.User;
import otakuplus.straybird.othellogameserver.models.UserInformation;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateGameTables;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateUserInformations;
import otakuplus.straybird.othellogameserver.network.SendMessage;
import otakuplus.straybird.othellogameserver.services.SocketIOService;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
public class GameTableController {

    private static final Logger logger = LoggerFactory.getLogger(GameTableController.class);

    @Autowired
    SocketIOService socketIOService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GameTableRepository gameTableRepository;

    @Autowired
    GameRecordRepository gameRecordRepository;

    @Autowired
    UserInformationRepository userInformationRepository;

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/enter", method = RequestMethod.POST)
    public ResponseEntity<?> enterGameTable(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        UserInformation userInformation = user.getUserInformation();
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user == null || gameTable == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (seatId == 0 && gameTable.getPlayerA() == null) {
            gameTable.setPlayerA(user);
        } else if (seatId == 1 && gameTable.getPlayerB() == null) {
            gameTable.setPlayerB(user);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
            logger.debug("room name" + SocketIOService.GAME_TABLE_ROOM + gameTableId);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/leave", method = RequestMethod.POST)
    public void leaveGameTable(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        UserInformation userInformation = user.getUserInformation();
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user != null && gameTable != null) {
            if (seatId == 0) {
                gameTable.setPlayerA(null);
            } else if (seatId == 1) {
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

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/giveUp", method = RequestMethod.POST)
    public void giveUp(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        User anotherUser = null;
        UserInformation userInformation = user.getUserInformation();
        userInformation.setGameLosts(userInformation.getGameLosts() + 1);
        userInformationRepository.save(userInformation);
        UserInformation anotherUserInformation = null;
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user != null && gameTable != null) {
            if (seatId == 0) {
                anotherUser = gameTable.getPlayerB();
            } else if (seatId == 1) {
                anotherUser = gameTable.getPlayerA();
            }
            if (anotherUser != null) {
                anotherUserInformation = anotherUser.getUserInformation();
                anotherUserInformation.setGameWins(anotherUserInformation.getGameWins() + 1);
                anotherUserInformation.setRankPoints(anotherUserInformation.getRankPoints() + 3);
                userInformationRepository.save(anotherUserInformation);
                NotifyUpdateUserInformations notifyUpdateUserInformations = new NotifyUpdateUserInformations();
                notifyUpdateUserInformations.setRoomName(SocketIOService.GAME_HALL_ROOM);
                socketIOService.notifyUpdateUserInformationList(notifyUpdateUserInformations);
            }
        }
    }

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/draw", method = RequestMethod.POST)
    public void draw(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        User anotherUser = null;
        UserInformation userInformation = user.getUserInformation();
        userInformation.setGameDraws(userInformation.getGameDraws() + 1);
        userInformation.setRankPoints(userInformation.getRankPoints() + 1);
        userInformationRepository.save(userInformation);
        UserInformation anotherUserInformation = null;
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user != null && gameTable != null) {
            if (seatId == 0) {
                anotherUser = gameTable.getPlayerB();
            } else if (seatId == 1) {
                anotherUser = gameTable.getPlayerA();
            }
            if (anotherUser != null) {
                anotherUserInformation = anotherUser.getUserInformation();
                anotherUserInformation.setGameDraws(anotherUserInformation.getGameDraws() + 1);
                anotherUserInformation.setRankPoints(anotherUserInformation.getRankPoints() + 1);
                userInformationRepository.save(anotherUserInformation);
                NotifyUpdateUserInformations notifyUpdateUserInformations = new NotifyUpdateUserInformations();
                notifyUpdateUserInformations.setRoomName(SocketIOService.GAME_HALL_ROOM);
                socketIOService.notifyUpdateUserInformationList(notifyUpdateUserInformations);
            }
        }
    }

    @RequestMapping(value = "/api/gameTables/{gameTableId}/seats/{seatId}/win", method = RequestMethod.POST)
    public void win(@PathVariable Integer gameTableId, @PathVariable Integer seatId, @RequestBody Integer userId) {
        User user = userRepository.findOne(userId);
        User anotherUser = null;
        UserInformation userInformation = user.getUserInformation();
        userInformation.setGameWins(userInformation.getGameWins() + 1);
        userInformation.setRankPoints(userInformation.getRankPoints() + 3);
        userInformationRepository.save(userInformation);
        UserInformation anotherUserInformation = null;
        GameTable gameTable = gameTableRepository.findOne(gameTableId);
        if (user != null && gameTable != null) {
            if (seatId == 0) {
                anotherUser = gameTable.getPlayerB();
            } else if (seatId == 1) {
                anotherUser = gameTable.getPlayerA();
            }
            if (anotherUser != null) {
                anotherUserInformation = anotherUser.getUserInformation();
                anotherUserInformation.setGameLosts(anotherUserInformation.getGameLosts() + 1);
                userInformationRepository.save(anotherUserInformation);
                NotifyUpdateUserInformations notifyUpdateUserInformations = new NotifyUpdateUserInformations();
                notifyUpdateUserInformations.setRoomName(SocketIOService.GAME_HALL_ROOM);
                socketIOService.notifyUpdateUserInformationList(notifyUpdateUserInformations);
            }
        }
    }
}
