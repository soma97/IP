package services;

import java.util.HashMap;
import java.util.Date;
import javax.servlet.http.HttpSession;
import db.dao.UserAccountDAO;
import db.models.Login;
import db.models.UserAccount;

public class LogingManagement {
	private static LogingManagement instance = new LogingManagement();

	public HashMap<String,Login> usersSessions = new HashMap<String, Login>();
	
	public static LogingManagement getInstance()
	{
		if(instance == null)
		{
			instance = new LogingManagement();
		}
		return instance;
	}
	
	private LogingManagement()
	{
		
	}
	
	public void loginUser(UserAccount userAccount, HttpSession session)
	{
		session.setAttribute("id", userAccount.getId());
		Login login = new Login(0, new Date(System.currentTimeMillis()), null, userAccount.getId());
		userAccount.setNumberOfLogins(userAccount.getNumberOfLogins()+1);
		userAccount.setLoggedIn(true);
		UserAccountDAO.insertLogin(login);
		UserAccountDAO.updateUser(userAccount);
		usersSessions.put(session.getId(), login);
	}
	
	public void logoutUser(HttpSession session)
	{
		UserAccount userAccount = UserAccountDAO.selectUserById((int)session.getAttribute("id"));
		Login login = usersSessions.get(session.getId());
		login.setDateLogout(new Date(System.currentTimeMillis()));
		userAccount.setLoggedIn(false);
		UserAccountDAO.updateLogoutTime(login);
		UserAccountDAO.updateUser(userAccount);
		usersSessions.remove(session.getId());
		session.invalidate();
	}
	
	public boolean checkIfLoggedIn(String sessionId)
	{
		Login login = usersSessions.get(sessionId);
		if(login == null)
		{
			return false;
		}
		return true;
	}
}
