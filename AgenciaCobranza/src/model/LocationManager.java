package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE);
		init();
	}

	public static LocationManager getInstance() throws Exception {
		if (instance == null) {
			instance = new LocationManager();
		}
		return instance;
	}

	private void init() throws Exception {
		// Si no existe la ubicación web, la crea
		Connection connection = this.ds.getConnection();
		PreparedStatement pre;
		pre = connection.prepareStatement(
				"SELECT Id FROM Ubicaciones WHERE Firma = '" + Constants.DB_IDENTFIER_WEB_LOCATION_NAME + "'");
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			Statement sta = connection.createStatement();
			sta.executeUpdate("INSERT INTO Ubicaciones (Firma, Nombre) VALUES ('"
					+ Constants.DB_IDENTFIER_WEB_LOCATION_NAME + "', 'BackOffice')");
			sta.close();
		}
		pre.close();
		res.close();
		connection.close();
	}

	public Location getLocation(String locationId) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre = connection
				.prepareStatement("SELECT Id, Nombre FROM Ubicaciones WHERE Firma = ? LIMIT 1");
		pre.setString(1, locationId);
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_LOCATION);
		}
		Location result = new Location(res.getLong("Id"), locationId, res.getString("Nombre"));
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public Location getLocation(long locationId) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre = connection
				.prepareStatement("SELECT Firma, Nombre FROM Ubicaciones WHERE Id = ? LIMIT 1");
		pre.setLong(1, locationId);
		ResultSet res = pre.executeQuery();
		if (!res.next()) {
			pre.close();
			res.close();
			connection.close();
			throw new Exception(Constants.ERROR_MSG_INVALID_LOCATION);
		}
		Location result = new Location(locationId, res.getString("Firma"), res.getString("Nombre"));
		pre.close();
		res.close();
		connection.close();
		return result;
	}

	public void addLocation(String locationId, String name) throws Exception {
		Connection connection = this.ds.getConnection();
		PreparedStatement pre = connection.prepareStatement("INSERT INTO Ubicaciones (Firma, Nombre) VALUES (?, ?)");
		pre.setString(1, locationId);
		pre.setString(2, name);
		pre.execute();
		pre.close();
		connection.close();
	}

	public List<Location> getLocations() throws Exception {
		List<Location> result = new ArrayList<Location>();
		Connection connection = this.ds.getConnection();
		Statement sta = connection.createStatement();
		ResultSet res = sta.executeQuery("SELECT Id, Firma, Nombre FROM Ubicaciones");
		while (res.next()) {
			result.add(new Location(res.getLong("Id"), res.getString("Firma"), res.getString("Nombre")));
		}
		sta.close();
		res.close();
		connection.close();
		return result;
	}
}
