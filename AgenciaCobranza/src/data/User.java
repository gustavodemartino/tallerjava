package data;

import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class User extends MessageData {
	private long id;
	private boolean isAdmin;
	private String userId;

	private String userName;

	public User(long id, String userName, boolean isAdmin) {
		this.id = id;
		this.userName = userName;
		this.isAdmin = isAdmin;
	}

	public User(JSONObject data) throws JSONException {
		this.isAdmin = data.getBoolean(Constants.JSON_IDENTFIER_IS_ADMIN);
		this.userName = data.getString(Constants.JSON_IDENTFIER_USERNAME);
	}

	public long getId() {
		return this.id;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public boolean getIsAdmin() {
		return this.isAdmin;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_IS_ADMIN, this.isAdmin);
		result.put(Constants.JSON_IDENTFIER_USERNAME, this.userName);
		return result;
	}
}
