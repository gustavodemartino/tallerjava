package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import data.AuditEvent;
import data.Location;
import data.User;

public class AuditManager {
	private static AuditManager instance = null;
	private DataSource ds;

	private AuditManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE);
	}

	public static AuditManager getInstance() throws Exception {
		if (instance == null) {
			instance = new AuditManager();
		}
		return instance;
	}

	public void register(String user, String location, int type) throws Exception {
		Connection connection = ds.getConnection();
		PreparedStatement pre;
		AuditEvent event = new AuditEvent(type, "usuario: \"" + user + "\"\nubicacion: \"" + location + "\"");
		try {
			pre = connection
					.prepareStatement("INSERT INTO Auditoria (FechaHora, Evento, Nivel, Detalle) VALUES (?, ?, ?, ?)");
			pre.setLong(1, event.getDateTime().getTime());
			pre.setInt(2, event.getType());
			pre.setInt(3, event.getLevel());
			pre.setString(4, event.getDetail());
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception("Error al auditar: " + e.getMessage());
		}
	}

	public void register(User user, Location location, int type, String detail) throws Exception {
		AuditEvent event = new AuditEvent(type, detail);
		Connection connection = ds.getConnection();
		PreparedStatement pre;
		try {
			pre = connection.prepareStatement(
					"INSERT INTO Auditoria (FechaHora, Usuario, Ubicacion, Evento, Nivel, Detalle) VALUES (?, ?, ?, ?, ?, ?)");
			pre.setLong(1, event.getDateTime().getTime());
			if (user != null) {
				pre.setLong(2, user.getId());
			} else {
				pre.setNull(2, java.sql.Types.BIGINT);
			}
			if (location != null) {
				pre.setLong(3, location.getId());
			} else {
				pre.setNull(3, java.sql.Types.BIGINT);
			}
			pre.setInt(4, event.getType());
			pre.setInt(5, event.getLevel());
			if (event.getDetail() != null) {
				pre.setString(6, event.getDetail());
			} else {
				pre.setNull(6, java.sql.Types.VARCHAR);
			}
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception("Error al auditar: " + e.getMessage());
		}
	}

	public List<AuditEvent> getEvents(Date from, Date to, int level) {
		List<AuditEvent> result = new ArrayList<AuditEvent>();
		try {
			Connection connection = ds.getConnection();
			PreparedStatement pre = connection.prepareStatement(
					"SELECT a.FechaHora, a.Evento, a.Detalle, u.Id AS IdUsuario, u.Usuario, u.Nombre, u.EsAdmin, l.Id AS IdUbicacion, l.Firma, l.Nombre AS NombreUbicacion FROM Auditoria AS a LEFT JOIN Usuarios AS u ON a.Usuario = u.Id LEFT JOIN Ubicaciones AS l ON a.Ubicacion=l.Id WHERE FechaHora >= ? AND FechaHora <= ? AND Nivel >= ? ORDER BY FechaHora LIMIT ?");
			pre.setLong(1, from.getTime());
			pre.setLong(2, to.getTime());
			pre.setInt(3, level);
			pre.setInt(4, 500);
			ResultSet res = pre.executeQuery();
			while (res.next()) {
				AuditEvent ev = new AuditEvent(new Date(res.getLong("FechaHora")), res.getInt("Evento"));
				String det = res.getString("Detalle");
				if (!res.wasNull()) {
					ev.setDetail(det);
				}
				long id = res.getLong("IdUsuario");
				if (!res.wasNull()) {
					ev.setUser(
							new User(id, res.getString("Usuario"), res.getString("Nombre"), res.getBoolean("EsAdmin")));
				}
				id = res.getLong("IdUbicacion");
				if (!res.wasNull()) {
					ev.setLocation(new Location(id, res.getString("Firma"), res.getString("NombreUbicacion")));
				}
				result.add(ev);
			}
			pre.close();
			connection.close();
		} catch (Exception e) {

		}
		return result;
	}
}
