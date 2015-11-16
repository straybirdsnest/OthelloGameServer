package otakuplus.straybird.othellogameserver.config;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import otakuplus.straybird.othellogameserver.network.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class OthelloGameServerConfig {

	public static final String SERVER_ORIGIN = "http://localhost:8080";

	public static final String GAME_HALL_ROOM = "gamehall";
	public static final String GAME_TABLE_ROOM = "gametable";
	public static final String SEND_MESSAGE_EVENT = "sendMessage";
	public static final String GAME_OPERATION_EVENT = "gameOperation";
	public static final String NOTIFY_USER_INFORMATIONS_UPDATE = "notifyUserInformationsUpdate";
	public static final String NOTIFY_GAME_TABLES_UPDATE="notifyGameTablesUpdate";

	private static final Logger logger = LoggerFactory.getLogger(OthelloGameServerConfig.class);

	@Autowired
	Environment env;

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration configuration =
				new com.corundumstudio.socketio.Configuration();
		configuration.setHostname(env.getProperty("othellogameserver.socketio.host"));
		configuration.setPort(env.getProperty("othellogameserver.socketio.port", Integer.class));
		configuration.setOrigin(SERVER_ORIGIN);

		final SocketIOServer socketIOServer = new SocketIOServer(configuration);

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

		socketIOServer.addEventListener(SEND_MESSAGE_EVENT, SendMessage.class, new DataListener<SendMessage>() {
			@Override
			public void onData(SocketIOClient socketIOClient, SendMessage sendMessage, AckRequest ackRequest) throws Exception {
				socketIOServer.getRoomOperations(sendMessage.getRoomName())
						.sendEvent(SEND_MESSAGE_EVENT, sendMessage);
			}
		});

		socketIOServer.addEventListener(GAME_OPERATION_EVENT, GameOperation.class, new DataListener<GameOperation>() {
			@Override
			public void onData(SocketIOClient socketIOClient, GameOperation gameOperation, AckRequest ackRequest) throws Exception {
				socketIOServer.getRoomOperations(gameOperation.getRoomName())
						.sendEvent(GAME_OPERATION_EVENT, gameOperation);
			}
		});

        return socketIOServer;
	}

}
