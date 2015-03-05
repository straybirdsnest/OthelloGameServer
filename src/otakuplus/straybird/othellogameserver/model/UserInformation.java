package otakuplus.straybird.othellogameserver.model;

import java.util.Date;

public class UserInformation {
	public static final String SEXUALITY_MALE = "male";
	public static final String SEXUALITY_FEMALE = "female";
	private int userId;
	private User user;
	private String nickname = null;
	private String sexuality = null;
	private Date birthday = null;
	private int gameWins = 0;
	private int gameDraws = 0;
	private int gameLosts = 0;
	private int rankPoints = 0;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getSexuality() {
		return sexuality;
	}

	public void setSexuality(String sexuality) {
		this.sexuality = sexuality;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public int getRankPoints() {
		return rankPoints;
	}

	public void setRankPoints(int rankPoints) {
		this.rankPoints = rankPoints;
	}

	public static void main(String[] args) {

	}
}
