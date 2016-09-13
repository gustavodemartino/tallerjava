package data;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import model.Constants;

public class Ticket extends MessageData {
	private String agency;
	private Date saleDateTime;
	private long ticketNumber;
	private String plate;
	private Date startDateTime;
	private int minutes;
	private Long amount;

	public Ticket() {
	}

	public Ticket(JSONObject data) throws JSONException {
		this.agency = data.getString(Constants.JSON_IDENTFIER_OPERATOR);
		this.saleDateTime = new Date(data.getLong(Constants.JSON_IDENTFIER_SALE_DATETIME));
		this.ticketNumber = data.getLong(Constants.JSON_IDENTFIER_TICKET_NUMBER);
		this.plate = data.getString(Constants.JSON_IDENTFIER_PLATE);
		this.startDateTime = new Date(data.getLong(Constants.JSON_IDENTFIER_START_TIME));
		this.minutes = data.getInt(Constants.JSON_IDENTFIER_MINUTES);
		this.amount = data.getLong(Constants.JSON_IDENTFIER_AMOUNT);
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_OPERATOR, this.agency);
		result.put(Constants.JSON_IDENTFIER_SALE_DATETIME, this.saleDateTime.getTime());
		result.put(Constants.JSON_IDENTFIER_TICKET_NUMBER, this.ticketNumber);
		result.put(Constants.JSON_IDENTFIER_PLATE, this.plate);
		result.put(Constants.JSON_IDENTFIER_START_TIME, this.startDateTime.getTime());
		result.put(Constants.JSON_IDENTFIER_MINUTES, this.minutes);
		result.put(Constants.JSON_IDENTFIER_AMOUNT, this.amount);
		return result;
	}

	public String getAgency() {
		return agency;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public Date getSaleDateTime() {
		return saleDateTime;
	}

	public void setSaleDateTime(Date saleDateTime) {
		this.saleDateTime = saleDateTime;
	}

	public long getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public Date getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(Date startDateTime) {
		this.startDateTime = startDateTime;
	}

	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public float getFloatAmount() {
		return ((float) this.amount) / 100;
	}
}
