package otakuplus.straybird.othellogameserver.network;

public class UpdateGameTable {

	public final static int ACTION_TAKE = 300;
	public final static int ACTION_READY = 301;
	public final static int ACTION_BUSY = 302;
	public final static int ACTION_LEFT = 399;

	private int gameTableId;
	private int userId;
	private int tablePosition;
	private int action;

	public int getGameTableId() {
		return gameTableId;
	}

	public void setGameTableId(int gameTableId) {
		this.gameTableId = gameTableId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTablePosition() {
		return tablePosition;
	}

	public void setTablePosition(int tablePosition) {
		this.tablePosition = tablePosition;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public static void main(String[] args) {

	}

}
