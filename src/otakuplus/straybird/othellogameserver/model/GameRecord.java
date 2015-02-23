package otakuplus.straybird.othellogameserver.model;

import java.util.Date;

public class GameRecord {

	private int gameRecordId = 0;
	private int playerAId = 0;
	private int playerBId = 0;
	private Date gameBeginTime;
	private Date gameEndTIme;
	private int whiteNumber = 0;
	private int blackNumber = 0;
	
	public int getGameRecordId() {
		return gameRecordId;
	}

	public void setGameRecordId(int gameRecordId) {
		this.gameRecordId = gameRecordId;
	}

	public int getPlayerAId() {
		return playerAId;
	}

	public void setPlayerAId(int playerAId) {
		this.playerAId = playerAId;
	}

	public int getPlayerBId() {
		return playerBId;
	}

	public void setPlayerBId(int playerBId) {
		this.playerBId = playerBId;
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
