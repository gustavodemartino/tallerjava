package model;

public class Constants {

	public static final String DATASOURCE_LOOKUP = "java:jboss/datasources/AgenciaDS";
	public static final String JSON_IDENTFIER_ACTION = "action";
	public static final String JSON_IDENTFIER_DATA = "data";
	public static final String JSON_IDENTFIER_USERID = "user_id";
	public static final String JSON_IDENTFIER_USERNAME = "user_name";
	public static final String JSON_IDENTFIER_PASSWORD = "password";
	public static final String JSON_IDENTFIER_TERMINALID = "terminal_id";
	public static final String JSON_IDENTFIER_PLATE = "plate";
	public static final String JSON_IDENTFIER_END_TIME = "end_time";
	public static final String JSON_IDENTFIER_START_TIME = "start_time";
	public static final String JSON_IDENTFIER_MESSAGE = "message";
	public static final String JSON_IDENTFIER_TICKET_NUMBER = "ticket_number";
	public static final String JSON_IDENTFIER_SALE_DATETIME = "sale_timestamp";
	public static final String JSON_IDENTFIER_AMOUNT = "amount";
	public static final String JSON_IDENTFIER_OPERATOR = "operator";
	public static final String JSON_IDENTFIER_IS_ADMIN = "admin";

	public static final String COMMAND_LOGIN = "login";
	public static final String COMMAND_LOGOUT = "logout";
	public static final String COMMAND_TICKET_SALE = "ticket_sale";
	public static final String COMMAND_TICKET_CANCEL = "ticket_cancel";

	public static final String RESPONSE_LOGIN_OK = "login_ok";
	public static final String RESPONSE_LOGIN_ERROR = "login_error";
	public static final String RESPONSE_LOGOUT_OK = "logout_ok";
	public static final String RESPONSE_TICKET_SALE_OK = "ticket_sale_ok";
	public static final String RESPONSE_TICKET_SALE_ERROR = "ticket_sale_error";
	public static final String RESPONSE_TICKET_CANCEL_OK = "ticket_cancel_ok";
	public static final String RESPONSE_TICKET_CANCEL_ERROR = "ticket_cancel_error";

	public static final String ERROR_MSG_INVALID_LOGIN = "Nombre de usuario o contraseña incorrecta";
	public static final String ERROR_MSG_INVALID_NICKNAME = "Identificado de usuario inválido";
	public static final String ERROR_MSG_REPEATED_LOGIN = "Intento repetido de login";
	public static final String ERROR_MSG_LOGIN_REQUIRED = "Usuario no logueado";
	public static final String ERROR_MSG_INVALID_ACTION = "Identificador de acción inválido";
	public static final String ERROR_MSG_INVALID_USERNAME = "Nombre de usuario inválido";
	public static final String ERROR_MSG_INVALID_PASSWORD = "Password inválido";	
	public static final String ERROR_MSG_INVALID_LOCATION = "Ubicación desconocida";
	public static final String ERROR_MSG_LOCATION_NOT_ALLOWED = "No tiene permisos para acceder desde esta ubicación";
	public static final String ERROR_MSG_NOT_USER_PRIVILEGES = "No tiene permisos para utilizar este recurso";
	public static final String ERROR_MSG_INVALID_USERMOD = "No tiene permisos para modificar otro usuario";
	public static final String ERROR_MSG_INVALID_USER_UPGRADE = "No tiene permisos para promover el usuario";
}
