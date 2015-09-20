package otakuplus.straybird.othellogameserver.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "OthelloUserInformation")
public class UserInformation {
	public static final String SEXUALITY_MALE = "male";
	public static final String SEXUALITY_FEMALE = "female";
	@Id
	private Long userInformationId;
	@MapsId
	@OneToOne
	@JoinColumn(name="userId")
	public User user;
	private String nickname;
	private String sexuality;
	private Date birthday;
	private int gameWins = 0;
	private int gameDraws = 0;
	private int gameLosts = 0;
	private int rankPoints = 0;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getUserInformationId() {
		return userInformationId;
	}

	public void setUserInformationId(Long userInformationId) {
		this.userInformationId = userInformationId;
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

}
