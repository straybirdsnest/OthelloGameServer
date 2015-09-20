package otakuplus.straybird.othellogameserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="OthelloGameTable")
public class GameTable {

	private long gameTableId;
	private Long playerAId;
	private Long playerBId;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getGameTableId() {
		return gameTableId;
	}

	public void setGameTableId(int gameTableId) {
		this.gameTableId = gameTableId;
	}

	public Long getPlayerAId() {
		return playerAId;
	}

	public void setPlayerAId(Long playerAId) {
		this.playerAId = playerAId;
	}

	public Long getPlayerBId() {
		return playerBId;
	}

	public void setPlayerBId(Long playerBId) {
		this.playerBId = playerBId;
	}
	
}
