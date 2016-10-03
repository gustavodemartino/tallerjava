package model;

public class Constants {

	public static final String DATASOURCE_LOOKUP = "java:jboss/datasources/AgenciaDS";
	public static final String DB_IDENTFIER_WEB_LOCATION_NAME = "web";
	public static final String SESSION_IDENTFIER_LOGIN_INFO = "loginInfo";
	public static final String SESSION_IDENTFIER_USER_INFO = "userInfo";
	
	public static final String JSON_IDENTFIER_ACTION = "action";
	public static final String JSON_IDENTFIER_DATA = "data";
	public static final String JSON_IDENTFIER_USERID = "user_id";
	public static final String JSON_IDENTFIER_USERNAME = "user_name";
	public static final String JSON_IDENTFIER_PASSWORD = "password";
	public static final String JSON_IDENTFIER_LOCATION_NAME = "location_name";
	public static final String JSON_IDENTFIER_PLATE = "plate";
	public static final String JSON_IDENTFIER_END_TIME = "end_time";
	public static final String JSON_IDENTFIER_START_TIME = "start_time";
	public static final String JSON_IDENTFIER_TICKET_NUMBER = "ticket_number";
	public static final String JSON_IDENTFIER_SALE_DATETIME = "sale_timestamp";
	public static final String JSON_IDENTFIER_CREDIT_DATETIME = "credit_timestamp";
	public static final String JSON_IDENTFIER_MESSAGE = "message";
	public static final String JSON_IDENTFIER_AMOUNT = "amount";
	public static final String JSON_IDENTFIER_OPERATOR = "operator";
	public static final String JSON_IDENTFIER_IS_ADMIN = "admin";
	public static final String JSON_IDENTFIER_AUTHORIZATION = "authorization";

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

	public static final String ERROR_MSG_INVALID_LOGIN = "Nombre de usuario o contrase�a incorrecta";
	public static final String ERROR_MSG_INVALID_NICKNAME = "Identificado de usuario inv�lido";
	public static final String ERROR_MSG_REPEATED_LOGIN = "Intento repetido de login";
	public static final String ERROR_MSG_LOGIN_REQUIRED = "Usuario no logueado";
	public static final String ERROR_MSG_INVALID_ACTION = "Identificador de acci�n inv�lido";
	public static final String ERROR_MSG_INVALID_USERNAME = "Nombre de usuario inv�lido";
	public static final String ERROR_MSG_INVALID_PASSWORD = "Password inv�lido";	
	public static final String ERROR_MSG_INVALID_LOCATION = "Ubicaci�n desconocida";
	public static final String ERROR_MSG_LOCATION_NOT_ALLOWED = "No tiene permisos para acceder desde esta ubicaci�n";
	public static final String ERROR_MSG_INVALID_USERADD = "No tiene permisos para crear usuarios";
	public static final String ERROR_MSG_INVALID_USERMOD = "No tiene permisos para modificar a otro usuario";
	public static final String ERROR_MSG_INVALID_USERDEL = "No tiene permisos para eliminar usuarios";
	public static final String ERROR_MSG_INVALID_SELF_ADMIN = "No puede cambiar sus privilegios administrativos";
	public static final String ERROR_MSG_UNSUPORTED_METHOD = "M�todo no soportado";
	public static final String ERROR_MSG_INVALID_USERID = "No existe usuario con el identificador requerido";
	public static final String ERROR_MSG_CANNOT_DELETE_USER = "No fue posible eliminar el usuario porque tiene registros asociados";
}
