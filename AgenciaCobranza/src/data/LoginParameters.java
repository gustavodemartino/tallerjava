package data;

import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class LoginParameters extends MessageData {
	private String userId;
	private String password;
	private String terminalId;

	public LoginParameters(JSONObject data) throws JSONException {
		this.userId = data.getString(Constants.JSON_IDENTFIER_USERID);
		this.password = data.getString(Constants.JSON_IDENTFIER_PASSWORD);
		this.terminalId = data.getString(Constants.JSON_IDENTFIER_TERMINALID);
	}

	public LoginParameters(String userId, String passwordHash, String terminalId) {
		this.userId = userId;
		this.password = passwordHash;
		this.terminalId = terminalId;
	}

	public String getUserId() {
		return userId;
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
		result.put(Constants.JSON_IDENTFIER_USERID, this.userId);
		result.put(Constants.JSON_IDENTFIER_PASSWORD, this.password);
		result.put(Constants.JSON_IDENTFIER_TERMINALID, this.terminalId);
		return result;
	}

}
