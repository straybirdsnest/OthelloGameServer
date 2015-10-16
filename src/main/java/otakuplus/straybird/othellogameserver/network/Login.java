package otakuplus.straybird.othellogameserver.network;

public class Login {
	private String username;
	private String password;
	private String socketIOId;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public String getSocketIOId() {
        return socketIOId;
    }

    public void setSocketIOId(String socketIOId) {
        this.socketIOId = socketIOId;
    }
}
