package otakuplus.straybird.othellogameserver.network;

import otakuplus.straybird.othellogameserver.model.User;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class KryonetUtil {
	
	public static final int SERVER_PORT = 8123;

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(User.class);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
