package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AuditManager {
	public static final int EVENT_LEVEL_INFO = 1;
	public static final int EVENT_LEVEL_WARNING = 2;
	public static final int EVENT_LEVEL_ERROR = 3;
	
	public static final int AUDIT_EVENT_LOGIN = 1001;
	public static final int AUDIT_EVENT_LOGIN_ERROR = 2001;
	public static final int AUDIT_EVENT_LOGOUT = 1002;
	public static final int AUDIT_EVENT_LOGOUT_ERROR = 2002;
	
	public static final int AUDIT_EVENT_SALE = 1003;
	public static final int AUDIT_EVENT_ANNULATION = 1004;
	public static final int AUDIT_EVENT_USER_CREATE = 1005;
	public static final int AUDIT_EVENT_USER_UPDATE = 1006;

	private static AuditManager instance = null;
	private DataSource ds;

	private AuditManager() throws Exception {
		InitialContext initContext = new InitialContext();
		this.ds = (DataSource) initContext.lookup(Constants.DATASOURCE_LOOKUP);
	}

	public static AuditManager getInstance() throws Exception {
		if (instance == null) {
			instance = new AuditManager();
		}
		return instance;
	}

	public void register(long user, long location, int event, int level, String detail) throws Exception {
		// Si no hay usuario pasar un valor negativo
		// Si no hay ubicación, pasar un valor negativo
		Connection connection = ds.getConnection();
		PreparedStatement pre;
		long timestamp = new Date().getTime();
		try {
			pre = connection.prepareStatement(
					"INSERT INTO Auditoria (FechaHora, Usuario, Ubicacion, Evento, Nivel, Detalle) VALUES (?, ?, ?, ?, ?, ?)");
			pre.setLong(1, timestamp);
			if (user > 0) {
				pre.setLong(2, user);
			} else {
				pre.setNull(2, java.sql.Types.BIGINT);
			}
			if (location >0) {
				pre.setLong(3, location);
			} else {
				pre.setNull(3, java.sql.Types.BIGINT);
			}
			pre.setInt(4, event);
			pre.setInt(5, level);
			pre.setString(6, detail);
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception("Error al auditar: " + e.getMessage());
		}
	}
}
