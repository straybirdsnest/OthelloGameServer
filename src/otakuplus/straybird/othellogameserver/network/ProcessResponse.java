package otakuplus.straybird.othellogameserver.network;

public class ProcessResponse {
	public static int LOGIN = 100;
	public static int LOGOUT = 101;
	public static int GET_USER_INFO = 102;
	public static int GET_USER_ONLINE_LIST = 103;

	private int requestType;
	private boolean responseState = false;

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
	}

	public boolean isResponseState() {
		return responseState;
	}

	public void setResponseState(boolean responseState) {
		this.responseState = responseState;
	}

	public static void main(String[] args) {
	}

}
