package db.models;

import java.util.Date;

public class EmergencyCall {
	private int id;
	private String title;
	private String description;
	private Date date;
	private String location;
	private String imageSource;
	private boolean isDeleted;
	private int emergencyCategoryId;
	
	public EmergencyCall(int id, String title, String description, Date date, String location, String imageSource,
			boolean isDeleted, int emergencyCategoryId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.location = location;
		this.imageSource = imageSource;
		this.isDeleted = isDeleted;
		this.emergencyCategoryId = emergencyCategoryId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getEmergencyCategoryId() {
		return emergencyCategoryId;
	}

	public void setEmergencyCategoryId(int emergencyCategoryId) {
		this.emergencyCategoryId = emergencyCategoryId;
	}
	
}
