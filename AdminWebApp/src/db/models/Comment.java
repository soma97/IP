package db.models;

import java.util.Date;

public class Comment {
	private int id;
	private String text;
	private String imageSource;
	private Date dateTime;
	private boolean isDeleted;
	private int userAccountId;
	private int postId;
	
	public Comment(int id, String text, String imageSource, Date dateTime, boolean isDeleted, int userAccountId,
			int postId) {
		this.id = id;
		this.text = text;
		this.imageSource = imageSource;
		this.dateTime = dateTime;
		this.isDeleted = isDeleted;
		this.userAccountId = userAccountId;
		this.postId = postId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(int userAccountId) {
		this.userAccountId = userAccountId;
	}

	public int getPostId() {
		return postId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
}
