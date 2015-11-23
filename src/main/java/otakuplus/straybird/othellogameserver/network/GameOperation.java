package otakuplus.straybird.othellogameserver.network;

public class GameOperation {
    public static final String STAND_BY = "standBy";
    public static final String STAND_BY_CANCLE = "standByCancle";
    public static final String BLACK_SET = "blackSet";
    public static final String WHITE_SET = "whiteSet";
    public static final String GIVE_UP = "giveUp";

    private String roomName;
    private Integer seatId;
    private Integer setX;
    private Integer setY;
    private String operation;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public Integer getSetX() {
        return setX;
    }

    public void setSetX(Integer setX) {
        this.setX = setX;
    }

    public Integer getSetY() {
        return setY;
    }

    public void setSetY(Integer setY) {
        this.setY = setY;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
