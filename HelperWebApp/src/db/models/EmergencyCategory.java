package db.models;

public class EmergencyCategory {

	private int id;
	private String name;
	private boolean isDeleted;
	
	public EmergencyCategory(int id, String name, boolean isDeleted) {
		this.id = id;
		this.name = name;
		this.isDeleted = isDeleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
