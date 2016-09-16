package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.LoginParameters;
import data.User;

public class UserManager {
	private static UserManager instance = null;
	private DataSource ds;

	private UserManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup("java:jboss/datasources/AgenciaDS");
	}

	public static UserManager getInstance() throws Exception {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	public User getWebUser(String userId, String password) throws Exception {
		User u = getUser(userId, password);
		if (!u.getIsAdmin()) {
			throw new Exception("Usuario no administrativo");
		}
		return u;
	}

	public User getUser(String userId, String password) throws Exception {
		User result = null;
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		String passwordHash = String.format("%064x", new java.math.BigInteger(1, md.digest()));

		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection
				.prepareStatement("SELECT Id, UserName, IsAdmin FROM Usuarios WHERE UserId = ? AND Password = ?");
		pre.setString(1, userId);
		pre.setString(2, passwordHash);
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_LOGIN);
		}
		result = new User(res.getLong("Id"), res.getString("UserName"), res.getInt("IsAdmin") == 1);
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public User login(LoginParameters data) throws Exception {
		User result = getUser(data.getUserId(), data.getPassword());
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT Id FROM Permisos WHERE Usuario = ? AND Location = ?");
		pre.setLong(1, result.getId());
		pre.setString(2, data.getTerminalId());
		ResultSet res;
		res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_LOCATION);
		}
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public void addUser(String userId, String UserName, String password, boolean isAdmin) throws Exception {
		if (userId == null || userId.contains(" ") || userId.length() == 0) {
			throw new Exception("Invalid usernanme");
		}
		if (password == null || password.length() == 0) {
			throw new Exception("Invalid password");
		}
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		String passwordHash = String.format("%064x", new java.math.BigInteger(1, md.digest()));

		Connection connection = this.ds.getConnection();
		connection.setAutoCommit(false);

		PreparedStatement pre;
		try {
			pre = connection
					.prepareStatement("INSERT INTO Usuarios (UserId, UserName, Password, IsAdmin) VALUES (?, ?, ?, ?)");
			pre.setString(1, userId);
			pre.setString(2, UserName);
			pre.setString(3, passwordHash);
			pre.setInt(4, (isAdmin ? 1 : 0));
			pre.execute();
			pre.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			connection.rollback();
			connection.close();
			throw new Exception(e.getMessage());
		}
	}
}
