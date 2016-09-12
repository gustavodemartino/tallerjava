package util;

import org.json.JSONException;
import org.json.JSONObject;

public class FixedPoint {

	private int power;
	private long value = 0;

	public FixedPoint(double value, int decimalPlaces) {
		this.power = (int) Math.pow(10.0, decimalPlaces);
		this.value = (long) (value * this.power);
	}

	public FixedPoint(JSONObject data) throws JSONException {
		this.power = data.getInt("power");
		this.value = data.getLong("value");
	}

	public float getFloat() {
		return ((float) value) / power;
	}

	public double getDouble() {
		return ((double) value) / power;
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("value", value);
		result.put("power", power);
		return result;
	}

	public String toString() {
		return ((Long) (value / power)).toString() + "," + ((Long) (value % power)).toString();

	}
}
