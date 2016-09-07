package tallerjava.data;

import org.json.JSONException;
import org.json.JSONObject;

import tallerjava.model.Constants;

public class LoginParameters extends MessageData {
	private String userName;
	private String password;
	private String terminalId;

	public LoginParameters(JSONObject data) throws JSONException {
		this.userName = data.getString(Constants.JSON_IDENTFIER_USERNAME);
		this.password = data.getString(Constants.JSON_IDENTFIER_PASSWORD);
		this.terminalId = data.getString(Constants.JSON_IDENTFIER_TERMINALID);
	}

	public LoginParameters(String userName, String password, String terminalId) {
		this.userName = userName;
		this.password = password;
		this.terminalId = terminalId;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getTerminalId() {
		return terminalId;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_USERNAME, this.userName);
		result.put(Constants.JSON_IDENTFIER_PASSWORD, this.password);
		result.put(Constants.JSON_IDENTFIER_TERMINALID, this.terminalId);
		return result;
	}

}
