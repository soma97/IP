package db.models;

import java.util.Date;

public class EmergencyCall {
	private int id;
	private String title;
	private String description;
	private Date date;
	private String imageSource;
	private String location;
	private boolean isDeleted;
	private int emergencyCategoryId;
	
	public EmergencyCall(int id, String title, String description, Date date, String imageSource, String location,
			boolean isDeleted, int emergencyCategoryId) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.imageSource = imageSource;
		this.location = location;
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

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
