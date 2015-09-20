package otakuplus.straybird.othellogameserver.models;

public class UserOnline {
	public static int ONLINE = 100;
	public static int STAND_BY = 101;
	public static int GAMING = 102;
	public static int OFFLINE= 199;
	
	private int userId;
	private int onlineState;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOnlineState() {
		return onlineState;
	}

	public void setOnlineState(int onlineState) {
		this.onlineState = onlineState;
	}

}
