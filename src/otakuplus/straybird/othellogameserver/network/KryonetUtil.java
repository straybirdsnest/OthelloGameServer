package otakuplus.straybird.othellogameserver.network;

import java.sql.Timestamp;

import otakuplus.straybird.othellogameserver.network.Login;
import otakuplus.straybird.othellogameserver.network.Logout;
import otakuplus.straybird.othellogameserver.model.UserInformation;
import otakuplus.straybird.othellogameserver.model.User;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class KryonetUtil {
	
	public static final int SERVER_PORT = 8123;

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(Timestamp.class);
		
		kryo.register(Login.class);
		kryo.register(Logout.class);
		kryo.register(User.class);
		kryo.register(GetUserInformation.class);
		kryo.register(UserInformation.class);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}