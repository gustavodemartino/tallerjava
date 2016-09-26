package model;

import data.Location;
import data.Login;
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

	public Login login(LoginParameters data) throws Exception {
		return login(data.getUserId(), data.getPassword(), data.getLocationName());
	}

	public Login login(String userId, String password, String locationName) throws Exception {
		User user = UserManager.getInstance().getUser(userId, password);
		Location location = LocationManager.getInstance().getLocation(locationName);
		if (!(user.getIsAdmin() || UserManager.getInstance().hasPermission(user.getId(), location.getId()))) {
			throw new Exception(Constants.ERROR_MSG_LOCATION_NOT_ALLOWED);
		}
		return new Login(user, location);
	}
}
