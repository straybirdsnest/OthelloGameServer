package otakuplus.straybird.othellogameserver.models;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OthelloGameRecord")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameRecordId;
    @ManyToOne
    @JoinColumn(name = "playera")
    private User playerA;
    @ManyToOne
    @JoinColumn(name = "playerb")
    private User playerB;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:00", timezone = "GMT+8")
    private Date gameBeginTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:00", timezone = "GMT+8")
    private Date gameEndTime;
    private int whiteNumber;
    private int blackNumber;
    @Column(columnDefinition = "TINYBLOB")
    private byte[] record;

    public Integer getGameRecordId() {
        return gameRecordId;
    }

    public void setGameRecordId(Integer gameRecordId) {
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

    public Date getGameEndTime() {
        return gameEndTime;
    }

    public void setGameEndTime(Date gameEndTime) {
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

