package data;

import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class User extends MessageData {
	private long id;
	private String shortName;
	private String name;
	private String password;
	private boolean admin;

	public User(long id, String userId, String userName, boolean isAdmin) {
		this.id = id;
		this.shortName = userId;
		this.name = userName;
		this.admin = isAdmin;
	}

	public User(String shortName, String name, String password, boolean isAdmin) {
		this.shortName = shortName;
		this.name = name;
		this.password = password;
		this.admin = isAdmin;
	}

	public long getId() {
		return this.id;
	}

	public String getShortName() {
		return shortName;
	}

	public String getName() {
		return this.name;
	}

	public boolean getIsAdmin() {
		return this.admin;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(JSONObject data) throws JSONException {
		this.admin = data.getBoolean(Constants.JSON_IDENTFIER_IS_ADMIN);
		this.name = data.getString(Constants.JSON_IDENTFIER_USERNAME);
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_IS_ADMIN, this.admin);
		result.put(Constants.JSON_IDENTFIER_USERNAME, this.name);
		return result;
	}
}
