package model;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.User;

public class UserManager {
	private static UserManager instance = null;
	private DataSource ds;

	// Singleton
	private UserManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE_LOOKUP);
		init();
	}

	public static UserManager getInstance() throws Exception {
		if (instance == null) {
			instance = new UserManager();
		}
		return instance;
	}

	private void init() throws Exception {
		// Si no hay usuarios en la base de datos crea un administrador
		Connection connection = this.ds.getConnection();
		Statement sta;
		sta = connection.createStatement();
		ResultSet res = sta.executeQuery("SELECT COUNT(Id) FROM Usuarios");
		res.next();
		if (res.getLong(1) == 0) {
			PreparedStatement pre = connection
					.prepareStatement("INSERT INTO Usuarios (Usuario, Nombre, Clave, EsAdmin) VALUES (?, ?, ?, ?)");
			pre.setString(1, "admin");
			pre.setString(2, "Administrador");
			pre.setString(3, hashPassword("admin"));
			pre.setInt(4, 1);
			pre.execute();
			pre.close();
		}
		sta.close();
		res.close();
		connection.close();
	}

	private String hashPassword(String password) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(password.getBytes("UTF-8"));
		return String.format("%064x", new java.math.BigInteger(1, md.digest()));
	}

	public User getUser(String userId, String password) throws Exception {
		User result = null;
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection
				.prepareStatement("SELECT Id, Usuario, Nombre, EsAdmin FROM Usuarios WHERE Usuario = ? AND Clave = ?");
		pre.setString(1, userId);
		pre.setString(2, hashPassword(password));
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_LOGIN);
		}
		result = new User(res.getLong("Id"), res.getString("Usuario"), res.getString("Nombre"),
				res.getInt("EsAdmin") == 1);
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public List<User> getUsers() throws SQLException {
		List<User> result = new ArrayList<User>();
		Connection connection = this.ds.getConnection();
		Statement sta;
		sta = connection.createStatement();
		ResultSet res = sta.executeQuery("SELECT Id, Usuario, Nombre, EsAdmin FROM Usuarios");
		while (res.next()) {
			result.add(new User(res.getLong("Id"), res.getString("Usuario"), res.getString("Nombre"),
					res.getInt("EsAdmin") == 1));
		}
		sta.close();
		res.close();
		connection.close();
		return result;
	}

	public void addUser(String adminId, String adminPassword, User newUser) throws Exception {
		User admin = getUser(adminId, adminPassword);
		if (!admin.getIsAdmin()) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USERMOD);
		}
		if (newUser.getShortName() == null || newUser.getShortName().contains(" ")
				|| newUser.getShortName().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_NICKNAME);
		}
		if (newUser.getName() == null || newUser.getName().trim().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USERNAME);
		}
		if (newUser.getPassword() == null || newUser.getPassword().length() == 0) {
			throw new Exception("Invalid password");
		}
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		try {
			pre = connection
					.prepareStatement("INSERT INTO Usuarios (Usuario, Nombre, Clave, EsAdmin) VALUES (?, ?, ?, ?)");
			pre.setString(1, newUser.getShortName());
			pre.setString(2, newUser.getName());
			pre.setString(3, hashPassword(newUser.getPassword()));
			pre.setInt(4, (newUser.getIsAdmin() ? 1 : 0));
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception(e.getMessage());
		}
	}

	public void modUser(String adminId, String adminPassword, User modUser) throws Exception {
		User admin = getUser(adminId, adminPassword);
		if (!admin.getIsAdmin() && admin.getId() != modUser.getId()) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USERMOD);
		}
		if (modUser.getIsAdmin() && !admin.getIsAdmin()) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USER_UPGRADE);
		}
		if (modUser.getShortName() == null || modUser.getShortName().contains(" ")
				|| modUser.getShortName().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_NICKNAME);
		}
		if (modUser.getName() == null || modUser.getName().trim().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USERNAME);
		}
		if (modUser.getPassword() == null || modUser.getPassword().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_PASSWORD);
		}

		Connection connection = this.ds.getConnection();
		connection.setAutoCommit(false);

		PreparedStatement pre;
		try {
			pre = connection.prepareStatement(
					"UPDATE Usuarios SET Usuario = ?, Nombre = ?, Clave = ?, EsAdmin = ? WHERE Id = ?");
			pre.setString(1, modUser.getShortName());
			pre.setString(2, modUser.getName());
			pre.setString(3, hashPassword(modUser.getPassword()));
			pre.setInt(4, (modUser.getIsAdmin() ? 1 : 0));
			pre.setLong(5, modUser.getId());
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

	public boolean permission(Long userId, Long locationId) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre = connection
				.prepareStatement("SELECT Id FROM Permisos WHERE Usuario = ? AND Ubicacion = ? LIMIT 1");
		pre.setLong(1, userId);
		pre.setLong(2, locationId);
		ResultSet res;
		res = pre.executeQuery();
		boolean result = true;
		if (!res.next()) {
			result = false;
		}
		pre.close();
		res.close();
		connection.close();
		return result;
	}
}
