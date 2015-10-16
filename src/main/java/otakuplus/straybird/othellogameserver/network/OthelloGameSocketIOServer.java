package otakuplus.straybird.othellogameserver.network;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class OthelloGameSocketIOServer {
	public static final  String SERVER_HOSTNAME = "localhost";
    public static final String SERVER_ORIGIN = "http://localhost:8080";
	public static final int CHAT_SERVER_PORT = 8081;

    public static final String GAME_HALL_ROOM = "gamehall";

	private static final Logger logger = LoggerFactory.getLogger(OthelloGameSocketIOServer.class);
	private SocketIOServer socketIOServer = null;

	public OthelloGameSocketIOServer(Configuration configuration) {
		configuration.setHostname(SERVER_HOSTNAME);
		configuration.setOrigin(SERVER_ORIGIN);
		configuration.setPort(CHAT_SERVER_PORT);
		socketIOServer = new SocketIOServer(configuration);

		socketIOServer.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient socketIOClient) {
                UUID sessionId = socketIOClient.getSessionId();
				logger.info("socket.io client"+sessionId+" connect to server.");
			}

		});

		socketIOServer.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(SocketIOClient socketIOClient) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Server", "user disconnected.");
				//socketIOServer.getBroadcastOperations().sendEvent("disconnect", jsonObject);
                UUID sessionId = socketIOClient.getSessionId();
                logger.info("socket.io client"+sessionId+" disconnect to server.");
			}
		});

	}

	public void startServer() {
		if (socketIOServer != null) {
			socketIOServer.start();
		}
	}

	public void stopServer() {
		if (socketIOServer != null) {
			socketIOServer.stop();
		}
	}

	public SocketIOServer getSocketIOServer() {
		return socketIOServer;
	}

    public void joinClientToRoom(String clientId, String room){
        if(socketIOServer != null && room != null){
            UUID uuid = UUID.fromString(clientId);
            SocketIOClient client = socketIOServer.getClient(uuid);
            if(client != null){
                client.joinRoom(room);
            }
        }
    }

}
