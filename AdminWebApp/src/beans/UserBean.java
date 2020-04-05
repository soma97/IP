package beans;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import db.dao.UserAccountDAO;
import db.models.UserAccount;
import services.LogingManagement;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public boolean isLoggedIn = false;
	public String username, password;
	public UserAccount userAccount = new UserAccount();
	
	public UserBean()
	{
		
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	public String login()
	{
		String errorMessage = null;
		userAccount = UserAccountDAO.selectUserByUsername(username);
		if(userAccount != null)
		{
			if(userAccount.getPassword().equals(password) && userAccount.isAdmin())
			{
				FacesContext facesContext = FacesContext.getCurrentInstance();
				LogingManagement.getInstance().loginUser(userAccount, (HttpSession)facesContext.getExternalContext().getSession(true));
				isLoggedIn = true;
				return "homePage.xhtml?faces-redirect=true";
			}
			else {
				errorMessage = "Neispravna lozinka";
			}
			if(!userAccount.isAdmin())
			{
				errorMessage = "Za prijavu na ovaj servis potreban vam je administratorski nalog";
			}
		}
		else {
			errorMessage = "Neispravno korisničko ime";
		}
		FacesContext.getCurrentInstance().addMessage("form:btn", new FacesMessage(errorMessage));
		return null;
	}
	
	public String logout()
	{
		if(isLoggedIn)
		{
			FacesContext facesContext = FacesContext.getCurrentInstance();
			LogingManagement.getInstance().logoutUser((HttpSession)facesContext.getExternalContext().getSession(true));
			isLoggedIn = false;
			return "login.xhtml?faces-redirect=true";
		}
		return null;
	}
}
