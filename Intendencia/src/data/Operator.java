package data;

public class Operator {
	private long dbId;
	private String signature;
	private String name;

	public Operator(long dbId, String signature, String name) {
		this.dbId = dbId;
		this.signature = signature;
		this.name = name;
	}

	public long getDbId() {
		return dbId;
	}

	public void setDbId(long id) {
		this.dbId = id;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature() {
		return signature;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
