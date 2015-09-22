package otakuplus.straybird.othellogameserver.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

@Entity
@Table(name = "OthelloUserInformation")
public class UserInformation {
	public static final String GENDER_MALE = "male";
	public static final String GENDER_FEMALE = "female";
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	@Id
	private Long userInformationId;

	@JsonIgnore
	@MapsId
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userId")
	public User user;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	private String nickname;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	@NotNull
	private String gender;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	private Date birthday;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	private int gameWins = 0;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	private int gameDraws = 0;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
	private int gameLosts = 0;
	
	//@JsonView(UserInformationView.ClientUserInformation.class)
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
