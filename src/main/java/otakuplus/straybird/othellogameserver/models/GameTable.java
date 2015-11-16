package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;

@Entity
@Table(name = "OthelloGameTable")
public class GameTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameTableId;
    @OneToOne
    @JoinColumn(name = "playera")
    private User playerA;
    @OneToOne
    @JoinColumn(name = "playerb")
    private User playerB;

    public Integer getGameTableId() {
        return gameTableId;
    }

    public void setGameTableId(Integer gameTableId) {
        this.gameTableId = gameTableId;
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

}
