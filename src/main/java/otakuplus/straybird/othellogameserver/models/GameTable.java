package otakuplus.straybird.othellogameserver.models;

import javax.persistence.*;

@Entity
@Table(name="OthelloGameTable")
public class GameTable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gameTableId;
	@OneToOne
	@JoinColumn
	private User playerA;
	@OneToOne
	@JoinColumn
	private User playerB;

	public Long getGameTableId() {
		return gameTableId;
	}

	public void setGameTableId(Long gameTableId) {
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
