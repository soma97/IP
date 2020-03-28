package db.models;

public class Post {

	private int id;
	private String title;
	private String text;
	private String link;
	private String videoSource;
	private boolean isEmergencyWarning;
	private String location;
	private boolean isDeleted;
	private int userAccountId;
	
	public Post(int id, String title, String text, String link, String videoSource, boolean isEmergencyWarning,
			String location, boolean isDeleted, int userAccountId) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.link = link;
		this.videoSource = videoSource;
		this.isEmergencyWarning = isEmergencyWarning;
		this.location = location;
		this.isDeleted = isDeleted;
		this.userAccountId = userAccountId;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public int getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(int userAccountId) {
		this.userAccountId = userAccountId;
	}

	public String getVideoSource() {
		return videoSource;
	}

	public void setVideoSource(String videoSource) {
		this.videoSource = videoSource;
	}

	public boolean isEmergencyWarning() {
		return isEmergencyWarning;
	}

	public void setEmergencyWarning(boolean isEmergencyWarning) {
		this.isEmergencyWarning = isEmergencyWarning;
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
}
