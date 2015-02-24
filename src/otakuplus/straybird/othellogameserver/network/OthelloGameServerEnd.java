package otakuplus.straybird.othellogameserver.network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class OthelloGameServerEnd {

	public OthelloGameServerEnd() throws IOException {
		Server server = new Server() {
			protected Connection newConnection() {
				return new OthelloGameConnection();
			}
		};

		KryonetUtil.register(server);

		server.addListener(new OthelloGameServerListner(server));

		server.bind(KryonetUtil.SERVER_PORT);
		server.start();
	}

	public static void main(String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new OthelloGameServerEnd();
	}

}
