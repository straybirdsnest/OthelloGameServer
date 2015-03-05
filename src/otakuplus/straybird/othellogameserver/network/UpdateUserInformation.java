package otakuplus.straybird.othellogameserver.network;

import otakuplus.straybird.othellogameserver.model.UserInformation;

public class UpdateUserInformation {
	private UserInformation userInformation;

	public UserInformation getUserInformation() {
		return userInformation;
	}

	public void setUserInformation(UserInformation userInformation) {
		this.userInformation = userInformation;
	}

	public static void main(String[] args) {
		UserInformation userInformation = new UserInformation();
		userInformation.setNickname("Jack");
		userInformation.setGameWins(50);
		userInformation.setGameDraws(100);
		userInformation.setGameLosts(50);
		UpdateUserInformation updateUserInformation = new UpdateUserInformation();
		updateUserInformation.setUserInformation(userInformation);
		assert (updateUserInformation.getUserInformation().getNickname()
				.equals("Jack"));
		assert (updateUserInformation.getUserInformation().getGameWins() == 50);
		assert (updateUserInformation.getUserInformation().getGameDraws() == 100);
		assert (updateUserInformation.getUserInformation().getGameLosts() == 50);
		System.out.println("Test on setter and getter: OK");
	}

}
