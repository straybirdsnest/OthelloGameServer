package otakuplus.straybird.othellogameserver.network;

import org.json.JSONObject;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

public class OthelloGameSocketIOServer {
	public static final int CHAT_SERVER_PORT = 8081;

	private SocketIOServer socketIOServer = null;

	public OthelloGameSocketIOServer(Configuration configuration) {
		configuration.setHostname("localhost");
		configuration.setOrigin("http://localhost:8080");
		configuration.setPort(CHAT_SERVER_PORT);
		socketIOServer = new SocketIOServer(configuration);

		socketIOServer.addConnectListener(new ConnectListener() {

			@Override
			public void onConnect(SocketIOClient socketIOClient) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Server", "user connected.");
				socketIOServer.getBroadcastOperations().sendEvent("connect", jsonObject);
			}

		});

		socketIOServer.addDisconnectListener(new DisconnectListener() {

			@Override
			public void onDisconnect(SocketIOClient socketIOClient) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("Server", "user disconnected.");
				socketIOServer.getBroadcastOperations().sendEvent("disconnect", jsonObject);
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

}
