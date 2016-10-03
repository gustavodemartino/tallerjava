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
		
		String descripcion = null;
		String[] detailItem = detail.split(";");
		
		switch (event) {
	        case AUDIT_EVENT_LOGIN:
	        	//infoParamLogin.getUserId() + ";" + infoParamLogin.getLocationName() + ";";
	        	if (level == EVENT_LEVEL_INFO)
	        		descripcion = "OK Ingreso al sistema por " + detailItem[0] + " en " + detailItem[1];
	        	else if (level == EVENT_LEVEL_ERROR)
	        		descripcion = "FAIL Ingreso al sistema " + detailItem[0] + " en " + detailItem[1];
	            break;
	            
	        case AUDIT_EVENT_LOGOUT:  
	        	if (level == EVENT_LEVEL_INFO)
	        		descripcion = "OK Salida del sistema " + detail;
	        	else if (level == EVENT_LEVEL_ERROR)
	        		descripcion = "FAIL Salida del sistema " + detail;
	            break;
	            
	        case AUDIT_EVENT_SALE:  
	        	//infoParamSale.getPlate() + ";" + infoParamSale.getMinutes() + ";" + infoParamSale.getStartTime() + ";";
	        	if (level == EVENT_LEVEL_INFO)
	        		descripcion = "OK Venta para " + detailItem[0] + " por " + detailItem[1] + " desde " + detailItem[2];
	        	else if (level == EVENT_LEVEL_ERROR)
	        		descripcion = "FAIL Venta para "  + detailItem[0] + " por " + detailItem[1] + " desde " + detailItem[2];
	            break;
	                 
	        case AUDIT_EVENT_ANNULATION: 
	        	//infoParamCancel.getTicketNumber() + ";";
	        	if (level == EVENT_LEVEL_INFO)
	        		descripcion = "OK Anulación para ticket " + detailItem[0];
	        	else if (level == EVENT_LEVEL_ERROR)
	        		descripcion = "FAIL Anulación para ticket " + detailItem[0];
	            break;
	                 
	        case AUDIT_EVENT_USER_CREATE:  
	        	if (level == EVENT_LEVEL_INFO)
	        		descripcion = "OK Creación de usuario " + detail;
	        	else if (level == EVENT_LEVEL_ERROR)
	        		descripcion = "FAIL Creación de usuario " + detail;
	            break;
	                 
	        case AUDIT_EVENT_USER_UPDATE:  
	        	if (level == EVENT_LEVEL_INFO)
	        		descripcion = "OK Modificación de usuario " + detail;
	        	else if (level == EVENT_LEVEL_ERROR)
	        		descripcion = "FAIL Modificación de usuario " + detail;
	            break;
	                 
	        default: 
	        	descripcion = "Sin descripción";
	            break;
	    }
		
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
			pre.setString(6, descripcion);
			pre.execute();
			pre.close();
			connection.close();
		} catch (Exception e) {
			connection.close();
			throw new Exception("Error al auditar: " + e.getMessage());
		}
	}
}
