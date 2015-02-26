package otakuplus.straybird.othellogameserver.network;

import otakuplus.straybird.othellogameserver.network.Login;

public class Login {
	private String username;
	private String password;

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

	public static void main(String[] args) {
		Login login = new Login();
		login.setUsername("TestUser");
		login.setPassword("TestPassowrd");
		assert (login.getUsername().equals("TestUser"));
		assert (login.getPassword().equals("TestPassword"));
		System.out.println("Test on setter and getter :OK");

	}

}
