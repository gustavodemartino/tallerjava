package data;

import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

import model.Constants;

public class TicketSaleParameters extends MessageData {
	private String plate;
	private int minutes;
	private Date startTime;

	public TicketSaleParameters(JSONObject data) throws JSONException {
		this.plate = data.getString(Constants.JSON_IDENTFIER_PLATE);
		this.minutes = data.getInt(Constants.JSON_IDENTFIER_MINUTES);
		this.startTime = new Date(data.getLong(Constants.JSON_IDENTFIER_START_TIME));
	}

	public TicketSaleParameters(String plate, int minutes, Date startTime) {
		this.plate = plate;
		this.minutes = minutes;
		this.startTime = startTime;
	}

	public String getPlate() {
		return plate;
	}

	public int getMinutes() {
		return minutes;
	}

	public Date getStartTime() {
		return startTime;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_PLATE, this.plate);
		result.put(Constants.JSON_IDENTFIER_MINUTES, this.minutes);
		result.put(Constants.JSON_IDENTFIER_START_TIME, startTime.getTime());
		return result;
	}
}
