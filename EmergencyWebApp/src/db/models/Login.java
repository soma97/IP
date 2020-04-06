package db.models;

import java.util.Date;

public class Login {

	private int id;
	private Date dateLogin;
	private Date dateLogout;
	private int userAccountId;
	
	public Login(int id, Date dateLogin, Date dateLogout, int userAccountId) {
		this.id = id;
		this.dateLogin = dateLogin;
		this.dateLogout = dateLogout;
		this.userAccountId = userAccountId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateLogin() {
		return dateLogin;
	}

	public void setDateLogin(Date dateLogin) {
		this.dateLogin = dateLogin;
	}

	public Date getDateLogout() {
		return dateLogout;
	}

	public void setDateLogout(Date dateLogout) {
		this.dateLogout = dateLogout;
	}

	public int getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(int userAccountId) {
		this.userAccountId = userAccountId;
	}
	
}
