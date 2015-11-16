package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;

@Entity
@Table(name = "OthelloUserOnline")
public class UserOnline {
    public static final int ONLINE = 100;
    public static final int STAND_BY = 101;
    public static final int GAMING = 102;
    public static final int OFFLINE = 199;

    @Id
    private Integer userId;
    private int onlineState;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(int onlineState) {
        this.onlineState = onlineState;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
