package data;

import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class LoginParameters extends MessageData {
	private String userId;
	private String password;
	private String locationName;

	public LoginParameters(JSONObject data) throws JSONException {
		this.userId = data.getString(Constants.JSON_IDENTFIER_USERID);
		this.password = data.getString(Constants.JSON_IDENTFIER_PASSWORD);
		this.locationName = data.getString(Constants.JSON_IDENTFIER_LOCATION_NAME);
	}

	public LoginParameters(String userId, String passwordHash, String locationName) {
		this.userId = userId;
		this.password = passwordHash;
		this.locationName = locationName;
	}

	public String getUserId() {
		return userId;
	}

	public String getPassword() {
		return password;
	}

	public String getLocationName() {
		return locationName;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_USERID, this.userId);
		result.put(Constants.JSON_IDENTFIER_PASSWORD, this.password);
		result.put(Constants.JSON_IDENTFIER_LOCATION_NAME, this.locationName);
		return result;
	}

}
