package otakuplus.straybird.othellogameserver.network;

import otakuplus.straybird.othellogameserver.network.GetUserInformation;

public class GetUserInformation {
	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public static void main(String[] args) {
		GetUserInformation getUserInformation = new GetUserInformation();
		getUserInformation.setUserId(10086);
		assert (getUserInformation.getUserId() == 10086);
		System.out.println("Test on setter and getter: OK");
	}

}
