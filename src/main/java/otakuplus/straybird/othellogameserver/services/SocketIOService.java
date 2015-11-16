package otakuplus.straybird.othellogameserver.services;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateGameTables;
import otakuplus.straybird.othellogameserver.network.NotifyUpdateUserInformations;
import otakuplus.straybird.othellogameserver.network.SendMessage;

import java.util.UUID;

@Service
public class SocketIOService {

    public static final String GAME_HALL_ROOM = "gamehall";
    public static final String GAME_TABLE_ROOM = "gametable";
    public static final String SEND_MESSAGE_EVENT = "sendMessage";
    public static final String GAME_OPERATION_EVENT = "gameOperation";
    public static final String NOTIFY_USER_INFORMATIONS_UPDATE = "notifyUserInformationsUpdate";
    public static final String NOTIFY_GAME_TABLES_UPDATE = "notifyGameTablesUpdate";

    @Autowired
    SocketIOServer socketIOServer;

    public void joinClientToRoom(String clientId, String roomName) {
        if (roomName != null) {
            UUID uuid = UUID.fromString(clientId);
            SocketIOClient client = socketIOServer.getClient(uuid);
            if (client != null) {
                client.joinRoom(roomName);
            }
        }
    }

    public void leaveClientFromRoom(String clientId, String roomName) {
        if (roomName != null) {
            UUID uuid = UUID.fromString(clientId);
            SocketIOClient client = socketIOServer.getClient(uuid);
            if (client != null) {
                client.leaveRoom(roomName);
            }
        }
    }

    public void sendMessage(SendMessage sendMessage) {
        if (sendMessage.getRoomName() != null) {
            socketIOServer.getRoomOperations(sendMessage.getRoomName()).sendEvent(SEND_MESSAGE_EVENT, sendMessage);
        }
    }

    public void notifyUpdateUserInformationList(NotifyUpdateUserInformations notifyUpdateUserInformations) {
        if (notifyUpdateUserInformations != null) {
            socketIOServer.getRoomOperations(notifyUpdateUserInformations.getRoomName())
                    .sendEvent(NOTIFY_USER_INFORMATIONS_UPDATE, notifyUpdateUserInformations);
        }
    }

    public void notifyUpdateGameTableList(NotifyUpdateGameTables notifyUpdateGameTables) {
        if (notifyUpdateGameTables != null) {
            socketIOServer.getRoomOperations(notifyUpdateGameTables.getRoomName())
                    .sendEvent(NOTIFY_GAME_TABLES_UPDATE, notifyUpdateGameTables);
        }
    }
}
