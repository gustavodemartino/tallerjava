package data;

import org.json.JSONObject;

import model.Constants;

public class Message {
	public static final int COMMAND_LOGIN = 1;
	public static final int COMMAND_LOGOUT = 2;
	public static final int COMMAND_TICKET_SALE = 3;
	public static final int COMMAND_TICKET_CANCEL = 4;

	public static final int LOGIN_OK = 11;
	public static final int LOGOUT_OK = 12;
	public static final int TICKET_SALE_OK = 13;
	public static final int TICKET_CANCEL_OK = 14;

	public static final int LOGIN_ERROR = 21;
	public static final int TICKET_SALE_ERROR = 23;
	public static final int TICKET_CANCEL_ERROR = 24;

	private int action;
	MessageData data;

	public Message(int action, MessageData data) {
		this.action = action;
		this.data = data;
	}

	public Message(String jsonString) throws Exception {
		JSONObject jo = new JSONObject(jsonString);
		String action = jo.getString(Constants.JSON_IDENTFIER_ACTION);
		JSONObject data = jo.getJSONObject(Constants.JSON_IDENTFIER_DATA);

		if (action.equals(Constants.COMMAND_LOGIN)) {
			this.action = COMMAND_LOGIN;
			this.data = new LoginParameters(data);

		} else if (action.equals(Constants.COMMAND_LOGOUT)) {
				this.action = COMMAND_LOGOUT;
				this.data = null;

		} else if (action.equals(Constants.COMMAND_TICKET_SALE)) {
			this.action = COMMAND_TICKET_SALE;
			this.data = new TicketSaleParameters(data);

		} else if (action.equals(Constants.COMMAND_TICKET_CANCEL)) {
			this.action = COMMAND_TICKET_CANCEL;
			this.data = new TicketCancelParameters(data);
			
		} else if (action.equals(Constants.RESPONSE_LOGIN_OK)) {
			this.action = LOGIN_OK;
			this.data = new User(data);
			
		} else if (action.equals(Constants.RESPONSE_LOGOUT_OK)) {
			this.action = LOGOUT_OK;
			this.data = null;
			
		} else if (action.equals(Constants.RESPONSE_TICKET_SALE_OK)) {
			this.action = TICKET_SALE_OK;
			this.data = new Ticket(data);
			
		} else if (action.equals(Constants.RESPONSE_TICKET_CANCEL_OK)) {
			this.action = TICKET_CANCEL_OK;
			this.data = null;
			
		} else if (action.equals(Constants.RESPONSE_LOGIN_ERROR)) {
			this.action = LOGIN_ERROR;
			this.data = new ErrorMessage(data);
			
		} else if (action.equals(Constants.RESPONSE_TICKET_SALE_ERROR)) {
			this.action = TICKET_SALE_ERROR;
			this.data = new ErrorMessage(data);
			
		} else if (action.equals(Constants.RESPONSE_TICKET_CANCEL_ERROR)) {
			this.action = TICKET_CANCEL_ERROR;
			this.data = new ErrorMessage(data);
		} else {
			throw new Exception(Constants.ERROR_MSG_INVALID_ACTION);
		}
	}

	public String toString() {
		JSONObject result = new JSONObject();
		try {
			if (this.action == COMMAND_LOGIN) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.COMMAND_LOGIN);

			} else if (this.action == COMMAND_LOGOUT) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.COMMAND_LOGOUT);

			} else if (this.action == COMMAND_TICKET_SALE) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.COMMAND_TICKET_SALE);

			} else if (this.action == COMMAND_TICKET_CANCEL) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.COMMAND_TICKET_CANCEL);

			} else if (this.action == LOGIN_OK) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_LOGIN_OK);

			} else if (this.action == LOGIN_ERROR) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_LOGIN_ERROR);

			} else if (this.action == LOGOUT_OK) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_LOGOUT_OK);

			} else if (this.action == TICKET_SALE_OK) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_TICKET_SALE_OK);

			} else if (this.action == TICKET_SALE_ERROR) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_TICKET_SALE_ERROR);

			} else if (this.action == TICKET_CANCEL_OK) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_TICKET_CANCEL_OK);

			} else if (this.action == TICKET_CANCEL_ERROR) {
				result.put(Constants.JSON_IDENTFIER_ACTION, Constants.RESPONSE_TICKET_CANCEL_ERROR);
			} else {
				throw new Exception("Tipo no procesado");
			}

			if (this.data == null) {
				result.put(Constants.JSON_IDENTFIER_DATA, new JSONObject());
			} else {
				result.put(Constants.JSON_IDENTFIER_DATA, this.data.toJSON());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public int getCommand() {
		return action;
	}

	public MessageData getData() {
		return data;
	}

}
