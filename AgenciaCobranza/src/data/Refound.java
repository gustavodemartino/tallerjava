package data;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class Refound extends MessageData {
	private Date dateTime;
	private long ticket;
	private long amount;
	private long authorization;

	public Refound(JSONObject data) throws JSONException {
		this.dateTime = new Date(data.getLong(Constants.JSON_IDENTFIER_CREDIT_DATETIME));
		this.ticket = data.getLong(Constants.JSON_IDENTFIER_TICKET_NUMBER);
		this.amount = data.getLong(Constants.JSON_IDENTFIER_AMOUNT);
		this.authorization = data.getLong(Constants.JSON_IDENTFIER_AUTHORIZATION);
	}

	public Refound() {
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_CREDIT_DATETIME, this.dateTime.getTime());
		result.put(Constants.JSON_IDENTFIER_TICKET_NUMBER, this.ticket);
		result.put(Constants.JSON_IDENTFIER_AMOUNT, this.amount);
		result.put(Constants.JSON_IDENTFIER_AUTHORIZATION, this.authorization);
		return result;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public long getTicket() {
		return ticket;
	}

	public void setTicket(long ticket) {
		this.ticket = ticket;
	}

	public long getAmount() {
		return amount;
	}

	public float getFloatAmount() {
		return ((float) amount) / 100;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getAuthorization() {
		return authorization;
	}

	public void setAuthorization(long authorization) {
		this.authorization = authorization;
	}

}
