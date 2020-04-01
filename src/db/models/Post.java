package db.models;

public class Post {

	private int id;
	private String title;
	private String text;
	private String link;
	private String videoSource;
	private String location;
	private boolean isEmergencyWarning;
	private boolean isPotentialDanger;
	private boolean isDeleted;
	private int userAccountId;
	
	public Post(int id, String title, String text, String link, String videoSource, String location,
			boolean isEmergencyWarning, boolean isPotentialDanger, boolean isDeleted, int userAccountId) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.link = link;
		this.videoSource = videoSource;
		this.location = location;
		this.isEmergencyWarning = isEmergencyWarning;
		this.isPotentialDanger = isPotentialDanger;
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

	public String getVideoSource() {
		return videoSource;
	}

	public void setVideoSource(String videoSource) {
		this.videoSource = videoSource;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isEmergencyWarning() {
		return isEmergencyWarning;
	}

	public void setEmergencyWarning(boolean isEmergencyWarning) {
		this.isEmergencyWarning = isEmergencyWarning;
	}

	public boolean isPotentialDanger() {
		return isPotentialDanger;
	}

	public void setPotentialDanger(boolean isPotentialDanger) {
		this.isPotentialDanger = isPotentialDanger;
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
}
