package model;

import data.Location;
import data.LoginParameters;
import data.User;

public class LoginManager {

	private static LoginManager instance = null;

	// Singleton
	private LoginManager() {
	}

	public static LoginManager getInstance() {
		if (instance == null) {
			instance = new LoginManager();
		}
		return instance;
	}

	public User terminalLogin(LoginParameters data) throws Exception {
		User user = UserManager.getInstance().getUser(data.getUserId(), data.getPassword());
		Location location = LocationManager.getInstance().getLocation(data.getTerminalId());
		if (!UserManager.getInstance().hasPermission(user.getId(), location.getId())) {
			throw new Exception(Constants.ERROR_MSG_LOCATION_NOT_ALLOWED);
		}
		return user;
	}

	public User webLogin(String userId, String password) throws Exception {
		User u;
		try {
			u = UserManager.getInstance().getUser(userId, password);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		if (!u.getIsAdmin()) {
			throw new Exception(Constants.ERROR_MSG_NOT_USER_PRIVILEGES);
		}
		return u;
	}
}
