package otakuplus.straybird.model;

import java.util.Date;

public class UserInformation {
	public static final String SEXUALITY_MALE = "male";
	public static final String SEXUALITY_FEMALE = "female";
	private int userInformationId = 0;
	private Date birthday = null;
	private String nickname = null;
	private int gameWins = 0;
	private int gameDraws = 0;
	private int gameLosts = 0;

	public int getUserInformationId() {
		return userInformationId;
	}

	public void setUserInformationId(int userInformationId) {
		this.userInformationId = userInformationId;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getGameWins() {
		return gameWins;
	}

	public void setGameWins(int gameWins) {
		this.gameWins = gameWins;
	}

	public int getGameDraws() {
		return gameDraws;
	}

	public void setGameDraws(int gameDraws) {
		this.gameDraws = gameDraws;
	}

	public int getGameLosts() {
		return gameLosts;
	}

	public void setGameLosts(int gameLosts) {
		this.gameLosts = gameLosts;
	}

	public static void main(String[] args) {
		
	}
}
