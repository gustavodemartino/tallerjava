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

import data.Location;

public class LocationManager {

	private static LocationManager instance = null;
	private DataSource ds;

	// Singleton
	private LocationManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE_LOOKUP);
	}

	public static LocationManager getInstance() throws Exception {
		if (instance == null) {
			instance = new LocationManager();
		}
		return instance;
	}

	public Location getLocation(String terminalId) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement("SELECT Id FROM Ubicaciones WHERE Nombre = ? LIMIT 1");
		pre.setString(1, terminalId);
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_LOCATION);
		}
		Location result = new Location(res.getLong(1), terminalId);
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public void addLocation(String nombre) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre = connection.prepareStatement("INSERT INTO Ubicaciones (Nombre) VALUES (?)");
		pre.execute();
		pre.close();
		connection.close();
	}

	public List<Location> getLocations() throws Exception {
		List<Location> result = new ArrayList<Location>();
		Connection connection = this.ds.getConnection();
		Statement sta = connection.createStatement();
		ResultSet res = sta.executeQuery("SELECT Id, Nombre FROM Ubicaciones");
		while (res.next()) {
			result.add(new Location(res.getLong(1), res.getString(2)));
		}
		sta.close();
		res.close();
		connection.close();
		return result;
	}
}