package data;

public class Location {

	private long id;
	private String name;

	public Location(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object l) {
		if (l instanceof Location) {
			return this.id == ((Location) l).id;
		}
		return false;
	}
}
