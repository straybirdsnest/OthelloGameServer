package otakuplus.straybird.othellogameserver.network;

public class GameOperation {
    public static final String STAND_BY = "standBy";
    public static final String STAND_BY_CANCLE = "standByCancle";
    public static final String BLACK_SET = "blackSet";
    public static final String WHITE_SET = "whiteSet";

    private String roomName;
    private Long seatId;
    private Long setX;
    private Long setY;
    private String operation;

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

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
