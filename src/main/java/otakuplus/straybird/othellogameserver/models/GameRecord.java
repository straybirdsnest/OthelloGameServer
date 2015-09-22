package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "OthelloGameRecord")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameRecordId;
    @OneToOne
	private User playerA;
	@OneToOne
    private User playerB;
	private Date gameBeginTime;
	private Date gameEndTIme;
	private int whiteNumber;
	private int blackNumber;
	
	public Long getGameRecordId() {
		return gameRecordId;
	}

	public void setGameRecordId(Long gameRecordId) {
		this.gameRecordId = gameRecordId;
	}

	public User getPlayerA() {
		return playerA;
	}

	public void setPlayerA(User playerA) {
		this.playerA = playerA;
	}

	public User getPlayerB() {
		return playerB;
	}

	public void setPlayerB(User playerB) {
		this.playerB = playerB;
	}

	public Date getGameBeginTime() {
		return gameBeginTime;
	}

	public void setGameBeginTime(Date gameBeginTime) {
		this.gameBeginTime = gameBeginTime;
	}

	public Date getGameEndTIme() {
		return gameEndTIme;
	}

	public void setGameEndTIme(Date gameEndTIme) {
		this.gameEndTIme = gameEndTIme;
	}

	public int getWhiteNumber() {
		return whiteNumber;
	}

	public void setWhiteNumber(int whiteNumber) {
		this.whiteNumber = whiteNumber;
	}

	public int getBlackNumber() {
		return blackNumber;
	}

	public void setBlackNumber(int blackNumber) {
		this.blackNumber = blackNumber;
	}

}

