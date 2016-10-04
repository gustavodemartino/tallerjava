package data;

import java.util.Date;

public class AuditEvent {
	public static final int EVENT_LEVEL_INFO = 1;
	public static final int EVENT_LEVEL_WARNING = 2;
	public static final int EVENT_LEVEL_ERROR = 3;

	public static final int AUDIT_EVENT_LOGIN_OK = 1001;
	public static final String AUDIT_NAME_LOGIN_OK = "Inicio de sesión";
	public static final int AUDIT_EVENT_LOGIN_ERROR = 2001;
	public static final String AUDIT_NAME_LOGIN_ERROR = "Error al iniciar la sesión";

	public static final int AUDIT_EVENT_LOGOUT_OK = 1002;
	public static final String AUDIT_NAME_LOGOUT_OK = "Cierre de sesión";
	public static final int AUDIT_EVENT_LOGOUT_ERROR = 2002;
	public static final String AUDIT_NAME_LOGOUT_ERROR = "Error al cerrar la sesión";

	public static final int AUDIT_EVENT_SALE_OK = 1003;
	public static final String AUDIT_NAME_SALE_OK = "Venta de ticket";
	public static final int AUDIT_EVENT_SALE_ERROR = 2003;
	public static final String AUDIT_NAME_SALE_ERROR = "Error al vender ticket";

	public static final int AUDIT_EVENT_ANNULATION_OK = 1004;
	public static final String AUDIT_NAME_ANNULATION_OK = "Anulación de ticket";
	public static final int AUDIT_EVENT_ANNULATION_ERROR = 2004;
	public static final String AUDIT_NAME_ANNULATION_ERROR = "Error al anular ticket";

	public static final int AUDIT_EVENT_USER_CREATE = 1005;
	public static final int AUDIT_EVENT_USER_UPDATE = 1006;

	private Date dateTime;
	private int type;
	private int level;
	private String detail;
	private User user;
	private Location location;

	// Constructor usado para registrar eventos
	public AuditEvent(int type, String detail) {
		this.dateTime = new Date();
		this.setType(type);
		this.detail = detail;
	}

	// Constructor usado para reportes
	public AuditEvent(Date dateTime, int type) {
		this.dateTime = dateTime;
		this.setType(type);
	}

	private void setType(int type) {
		this.type = type;
		switch (type) {
		case AUDIT_EVENT_LOGIN_OK:
		case AUDIT_EVENT_LOGOUT_OK:
		case AUDIT_EVENT_SALE_OK:
		case AUDIT_EVENT_ANNULATION_OK:
			this.level = EVENT_LEVEL_INFO;
			break;
		case AUDIT_EVENT_LOGIN_ERROR:
		case AUDIT_EVENT_SALE_ERROR:
		case AUDIT_EVENT_ANNULATION_ERROR:
			this.level = EVENT_LEVEL_WARNING;
			break;
		case AUDIT_EVENT_LOGOUT_ERROR:
			this.level = EVENT_LEVEL_ERROR;
			break;
		}
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public int getType() {
		return type;
	}

	public String getSType() {
		switch (this.type) {
		case AUDIT_EVENT_LOGIN_OK:
			return AUDIT_NAME_LOGIN_OK;
		case AUDIT_EVENT_LOGIN_ERROR:
			return AUDIT_NAME_LOGIN_ERROR;
		case AUDIT_EVENT_LOGOUT_OK:
			return AUDIT_NAME_LOGOUT_OK;
		case AUDIT_EVENT_LOGOUT_ERROR:
			return AUDIT_NAME_LOGOUT_ERROR;
		case AUDIT_EVENT_SALE_OK:
			return AUDIT_NAME_SALE_OK;
		case AUDIT_EVENT_SALE_ERROR:
			return AUDIT_NAME_SALE_ERROR;
		case AUDIT_EVENT_ANNULATION_OK:
			return AUDIT_NAME_ANNULATION_OK;
		case AUDIT_EVENT_ANNULATION_ERROR:
			return AUDIT_NAME_ANNULATION_ERROR;
		}
		return "Unknow ***";
	}

	public int getLevel() {
		return level;
	}

	public String getDetail() {
		return detail;
	}

	public User getUser() {
		return user;
	}

	public Location getLocation() {
		return location;
	}

}
