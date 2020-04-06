package db.models;

public class UserAccount {

	private int id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	private String country;
	private String region;
	private String city;
	private String imageSource;
	private String receiveEmergencyNotifications;
	private int numberOfLogins;
	private boolean isAdmin;
	private boolean isApproved;
	private boolean isBlocked;
	private boolean isLoggedIn;
	
	public UserAccount(int id, String name, String surname, String username, String password, String email,
			String country, String region, String city, String imageSource, String receiveEmergencyNotifications,
			int numberOfLogins, boolean isAdmin, boolean isApproved, boolean isBlocked, boolean isLoggedIn) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.country = country;
		this.region = region;
		this.city = city;
		this.imageSource = imageSource;
		this.receiveEmergencyNotifications = receiveEmergencyNotifications;
		this.numberOfLogins = numberOfLogins;
		this.isAdmin = isAdmin;
		this.isApproved = isApproved;
		this.isBlocked = isBlocked;
		this.isLoggedIn = isLoggedIn;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getImageSource() {
		return imageSource;
	}

	public void setImageSource(String imageSource) {
		this.imageSource = imageSource;
	}

	public String getReceiveEmergencyNotifications() {
		return receiveEmergencyNotifications;
	}

	public void setReceiveEmergencyNotifications(String receiveEmergencyNotifications) {
		this.receiveEmergencyNotifications = receiveEmergencyNotifications;
	}

	public int getNumberOfLogins() {
		return numberOfLogins;
	}

	public void setNumberOfLogins(int numberOfLogins) {
		this.numberOfLogins = numberOfLogins;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	
}
