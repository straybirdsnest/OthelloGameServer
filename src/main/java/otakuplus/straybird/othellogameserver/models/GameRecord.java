package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;

@Entity
@Table(name = "OthelloGameRecord")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameRecordId;
    private String playerA;
    private String playerB;
    private String gameBeginTime;
    private String gameEndTime;
    private int whiteNumber;
    private int blackNumber;
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] record;

    public Integer getGameRecordId() {
        return gameRecordId;
    }

    public void setGameRecordId(Integer gameRecordId) {
        this.gameRecordId = gameRecordId;
    }

    public String getPlayerA() {
        return playerA;
    }

    public void setPlayerA(String playerA) {
        this.playerA = playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public void setPlayerB(String playerB) {
        this.playerB = playerB;
    }

    public String getGameBeginTime() {
        return gameBeginTime;
    }

    public void setGameBeginTime(String gameBeginTime) {
        this.gameBeginTime = gameBeginTime;
    }

    public String getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(String gameEndTime) {
        this.gameEndTime = gameEndTime;
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

    public byte[] getRecord() {
        return record;
    }

    public void setRecord(byte[] record) {
        this.record = record;
    }
}

