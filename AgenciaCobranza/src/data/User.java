package data;

import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class User extends MessageData {
	private boolean isAdmin;
	private String userName;

	public User(String userName, boolean isAdmin) {
		this.userName = userName;
		this.isAdmin = isAdmin;
	}

	public User(JSONObject data) throws JSONException {
		this.isAdmin = data.getBoolean(Constants.JSON_IDENTFIER_IS_ADMIN);
		this.userName = data.getString(Constants.JSON_IDENTFIER_USERNAME);
	}

	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	public String getUserName() {
		return this.userName;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_IS_ADMIN, this.isAdmin);
		result.put(Constants.JSON_IDENTFIER_USERNAME, this.userName);
		return result;
	}
}
