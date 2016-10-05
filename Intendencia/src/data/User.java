package data;

import java.security.MessageDigest;
import java.util.Random;

public class User {
	private long dbId;
	private String id;
	private String name;
	private String hashedPassword;

	public User(long dbId, String id, String name) {
		this.dbId = dbId;
		this.id = id;
		this.name = name;
		try {
			this.hashedPassword = hashPassword(((Integer) new Random().nextInt()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String hashPassword(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		return String.format("%064x", new java.math.BigInteger(1, md.digest()));
	}

	public long getDbId() {
		return dbId;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setPassword(String password) throws Exception {
		this.hashedPassword = hashPassword(password);
	}

}
