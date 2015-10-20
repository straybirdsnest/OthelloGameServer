package otakuplus.straybird.othellogameserver.network;

public class GameOperation {
    private String roomName;
    private Long seatId;
    private Long setX;
    private Long setY;
    private Boolean standBy;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public Long getSetX() {
        return setX;
    }

    public void setSetX(Long setX) {
        this.setX = setX;
    }

    public Long getSetY() {
        return setY;
    }

    public void setSetY(Long setY) {
        this.setY = setY;
    }

    public Boolean getStandBy() {
        return standBy;
    }

    public void setStandBy(Boolean standBy) {
        this.standBy = standBy;
    }
}
