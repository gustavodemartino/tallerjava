package model;

import data.LoginParameters;

public class UserManager {
	private static UserManager instance = null;

	private UserManager() {
	}

	public static UserManager getInstance() {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	public User getUser(LoginParameters data) throws Exception {
		User result = null;
		if (data.getUserName().equals("admin") && data.getPassword().equals("admin")) {
			result = new User();
		} else {
			throw new Exception(Constants.ERROR_MSG_INVALID_LOGIN);
		}
		return result;
	}
}
