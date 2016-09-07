package tallerjava.data;

import org.json.JSONException;
import org.json.JSONObject;

import tallerjava.model.Constants;

public class TicketCancelParameters extends MessageData {
	private long ticketNumber;
	
	public TicketCancelParameters(JSONObject data) throws JSONException {
		this.ticketNumber = data.getLong(Constants.JSON_IDENTFIER_TICKET_NUMBER);
	}

	public TicketCancelParameters(long ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public long getTicketNumber() {
		return ticketNumber;
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_TICKET_NUMBER , this.ticketNumber);
		return result;
	}

}
