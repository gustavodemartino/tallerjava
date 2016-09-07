package tallerjava.data;

import org.json.JSONException;
import org.json.JSONObject;

import tallerjava.model.Constants;


public class ErrorMessage extends MessageData {
	private String message;

	public String getMessage() {
		return message;
	}

	public ErrorMessage(String message) {
		this.message = message;
	}

	public ErrorMessage(JSONObject data) throws JSONException {
		this.message = data.getString(Constants.JSON_IDENTFIER_MESSAGE);
	}

	@Override
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put(Constants.JSON_IDENTFIER_MESSAGE, message);
		return result;
	}

}
