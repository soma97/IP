package db.models;

public class CallCategory {
	
	public int id;
	public String category;
	
	public CallCategory(int id, String category)
	{
		this.id = id;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	

}
