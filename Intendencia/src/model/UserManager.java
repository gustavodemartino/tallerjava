package model;

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
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE);
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
					.prepareStatement("INSERT INTO Usuarios (Identificador, Nombre, Clave) VALUES (?, ?, ?)");
			pre.setString(1, "admin");
			pre.setString(2, "Administrador");
			pre.setString(3, User.hashPassword("admin"));
			pre.execute();
			pre.close();
		}
		sta.close();
		res.close();
		connection.close();
	}

	public User getUser(String userId, String password) throws Exception {
		User result = null;
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT Id, Nombre FROM Usuarios WHERE Identificador = ? AND Clave = ?");
		pre.setString(1, userId);
		pre.setString(2, User.hashPassword(password));
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			AuditManager.getInstance().register(AuditManager.Event.LOGIN_ERROR, userId);
			throw new Exception(Constants.ERROR_MSG_INVALID_LOGIN);
		}
		result = new User(res.getLong("Id"), userId, res.getString("Nombre"));
		pre.close();
		res.close();
		connection.close();
		return result;
	}
	
	public User getUser(long userId) throws Exception {
		User result = null;
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT * FROM Usuarios WHERE Id = ?");
		pre.setLong(1, userId);
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
		}
		result = new User(res.getLong("Id"), res.getString("Identificador"), res.getString("Nombre"));
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
		ResultSet res = sta.executeQuery("SELECT Id, Identificador, Nombre FROM Usuarios");
		while (res.next()) {
			result.add(new User(res.getLong("Id"), res.getString("Identificador"), res.getString("Nombre")));
		}
		sta.close();
		res.close();
		connection.close();
		return result;
	}

	public void addUser(User newUser) throws Exception {
		if (newUser.getId() == null || newUser.getId().contains(" ") || newUser.getName().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_ID);
		}
		if (newUser.getName() == null || newUser.getName().trim().length() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USERNAME);
		}
		Connection connection = this.ds.getConnection();
		try {
			PreparedStatement pre = connection
					.prepareStatement("INSERT INTO Usuarios (Identificador, Nombre, Clave) VALUES (?, ?, ?)");
			pre.setString(1, newUser.getId());
			pre.setString(2, newUser.getName());
			pre.setString(3, newUser.getHashedPassword());
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception(e.getMessage());
		}
	}
	
	public void updateUser(User updUser) throws Exception {
		if (updUser == null || updUser.getDbId() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_ID);
		}
		if (updUser.getId().isEmpty() && updUser.getName().isEmpty() && updUser.getHashedPassword().isEmpty()) {
			throw new Exception(Constants.ERROR_MSG_INVALID_USER_UPD);
		}
		Connection connection = this.ds.getConnection();
		try {
			PreparedStatement pre = null;
			
			if (!updUser.getId().isEmpty()) {
				pre = connection.prepareStatement("UPDATE Usuarios SET Identificador=? WHERE Id=?");
				pre.setString(1, updUser.getId());
				pre.setLong(2, updUser.getDbId());
				pre.execute();
			}
			
			if (!updUser.getName().isEmpty()) {
				pre = connection.prepareStatement("UPDATE Usuarios SET Nombre=? WHERE Id=?");
				pre.setString(1, updUser.getName());
				pre.setLong(2, updUser.getDbId());
				pre.execute();
			}
			
			if (!updUser.getHashedPassword().isEmpty()) {
				pre = connection.prepareStatement("UPDATE Usuarios SET Clave =? WHERE Id=?");
				pre.setString(1, updUser.getHashedPassword());
				pre.setLong(2, updUser.getDbId());
				pre.execute();
			}
			
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_USER_UPD);
		}
	}
	
	public void deleteUser(User dltUser) throws Exception {
		if (dltUser == null || dltUser.getDbId() == 0) {
			throw new Exception(Constants.ERROR_MSG_INVALID_ID);
		}		
		Connection connection = this.ds.getConnection();
		try {
			PreparedStatement pre = connection.prepareStatement("DELETE FROM Usuarios WHERE Id=?");			
			pre.setLong(1, dltUser.getDbId());
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_USER_DLT);
		}
	}
}
