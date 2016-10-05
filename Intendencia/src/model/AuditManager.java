package model;

public class AuditManager {

	public enum Event{
		LOGIN_ERROR
	}
	
	private static AuditManager instance = null;

	private AuditManager() {
	}

	public static AuditManager getInstance() {
		if (instance == null) {
			instance = new AuditManager();
		}
		return instance;
	}

	public void register(Event event, String user) {
		// TODO Auto-generated method stub
		
	}

}
