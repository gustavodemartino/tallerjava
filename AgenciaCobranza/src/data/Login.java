package data;

public class Login {
	private User user;
	private Location location;

	public Login(User user, Location location) {
		this.user = user;
		this.location = location;
	}

	public User getUser() {
		return user;
	}

	public Location getLocation() {
		return location;
	}

}
