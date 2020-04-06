package db.models;

import java.util.Date;

import db.dao.EmergencyDAO;

public class EmergencyCall {
	private int id;
	private String title;
	private String description;
	private Date date;
	private String location;
	private String imageSource;
	private int numberOfDenouncements;
	private boolean isDeleted;
	private int callCategoryId;
	private String callCategoryString;
	
	public EmergencyCall(int id, String title, String description, Date date, String location, String imageSource, int numberOfDenouncements,
			boolean isDeleted, int callCategoryId) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.date = date;
		this.location = location;
		this.imageSource = imageSource;
		this.isDeleted = isDeleted;
		this.numberOfDenouncements = numberOfDenouncements;
		this.callCategoryId = callCategoryId;
		this.callCategoryString = EmergencyDAO.selectCallCategoryById(callCategoryId).getCategory();
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

	public int getNumberOfDenouncements() {
		return numberOfDenouncements;
	}

	public void setNumberOfDenouncements(int numberOfDenouncements) {
		this.numberOfDenouncements = numberOfDenouncements;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getCallCategoryId() {
		return callCategoryId;
	}

	public void setCallCategoryId(int callCategoryId) {
		this.callCategoryId = callCategoryId;
	}

	public String getCallCategoryString() {
		return callCategoryString;
	}

	public void setCallCategoryString(String callCategoryString) {
		this.callCategoryString = callCategoryString;
	}
	
}
