package otakuplus.straybird.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OthelloGameServerSocket {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(58768);
			while((socket = serverSocket.accept()) != null){
				
			}
		} catch (IOException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}finally{
			try {
				serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
