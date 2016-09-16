package data;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class MessageData {
	public abstract JSONObject toJSON() throws JSONException;
}
