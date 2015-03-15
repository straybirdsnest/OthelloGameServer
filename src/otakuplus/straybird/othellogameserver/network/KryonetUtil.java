package otakuplus.straybird.othellogameserver.network;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import otakuplus.straybird.othellogameserver.model.GameTable;
import otakuplus.straybird.othellogameserver.model.User;
import otakuplus.straybird.othellogameserver.model.UserInformation;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import de.javakaffee.kryoserializers.DateSerializer;

public class KryonetUtil {

	public static final int SERVER_PORT = 8123;

	public static void register(EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.addDefaultSerializer(Date.class, DateSerializer.class);
		kryo.addDefaultSerializer(Timestamp.class, DateSerializer.class);
		kryo.register(Timestamp.class);
		kryo.register(Date.class);
		kryo.register(java.util.Date.class);
		kryo.register(ArrayList.class);

		kryo.register(Login.class);
		kryo.register(Logout.class);
		kryo.register(ProcessResponse.class);
		kryo.register(User.class);
		kryo.register(GetUserInformation.class);
		kryo.register(UserInformation.class);
		kryo.register(SendMessage.class);
		kryo.register(GetUserOnlineList.class);
		kryo.register(GameTable.class);
		kryo.register(UpdateGameTable.class);
		kryo.register(GetGameTableList.class);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}