package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import db.dao.EmergencyDAO;
import db.dao.UserAccountDAO;
import db.models.EmergencyCall;
import db.models.EmergencyCategory;
import db.models.UserAccount;

@ManagedBean(name="homeBean")
@RequestScoped
public class HomePageBean {
	
	public int activeUsers, registeredUsers;
	public String newEmerCategory;
	public ArrayList<UserAccount> allUsers;
	public List<UserAccount> usersToApprove;
	public ArrayList<EmergencyCall> emergencyCalls;
	
	public HomePageBean()
	{
		allUsers = UserAccountDAO.selectAllUsers();
		activeUsers = (int)allUsers.stream().filter(x -> x.isLoggedIn()).count();
		registeredUsers = allUsers.size();
		usersToApprove = allUsers.stream().filter(x -> !x.isApproved() && !x.isBlocked()).collect(Collectors.toList());
		emergencyCalls = EmergencyDAO.selectAllCalls();
		
		for(EmergencyCall call : emergencyCalls)
		{
			int id = call.getCallCategoryId();
			String callCategory = EmergencyDAO.selectCallCategoryById(id).getCategory();
			call.setCallCategoryString(callCategory);
		}
	}

	public int getActiveUsers() {
		return activeUsers;
	}

	public void setActiveUsers(int activeUsers) {
		this.activeUsers = activeUsers;
	}

	public int getRegisteredUsers() {
		return registeredUsers;
	}

	public void setRegisteredUsers(int registeredUsers) {
		this.registeredUsers = registeredUsers;
	}

	public ArrayList<UserAccount> getAllUsers() {
		return allUsers;
	}

	public void setAllUsers(ArrayList<UserAccount> allUsers) {
		this.allUsers = allUsers;
	}
	
	public List<UserAccount> getUsersToApprove() {
		return usersToApprove;
	}

	public void setUsersToApprove(List<UserAccount> usersToApprove) {
		this.usersToApprove = usersToApprove;
	}
	
	

	public String getNewEmerCategory() {
		return newEmerCategory;
	}

	public void setNewEmerCategory(String newEmerCategory) {
		this.newEmerCategory = newEmerCategory;
	}
	
	

	public ArrayList<EmergencyCall> getEmergencyCalls() {
		return emergencyCalls;
	}

	public void setEmergencyCalls(ArrayList<EmergencyCall> emergencyCalls) {
		this.emergencyCalls = emergencyCalls;
	}

	public String blokiraj()
	{
		Map<String,String> paramMap=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(paramMap.containsKey("id"))
		{
			int id=Integer.parseInt(paramMap.get("id"));
			UserAccount user = UserAccountDAO.selectUserById(id);
			allUsers.forEach(x -> {
				if(x.getId() == id)
				{
					x.setBlocked(true);
				}
			});
			user.setBlocked(true);
			UserAccountDAO.updateUser(user);
		}
		return null;
	}
	
	public String resetLozinke()
	{
		Map<String,String> paramMap=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(paramMap.containsKey("id"))
		{
			int id=Integer.parseInt(paramMap.get("id"));
			UserAccount user = UserAccountDAO.selectUserById(id);
			String newPassword = "";
			Random random = new Random();
			for(int i=0; i<16; ++i)
			{
				newPassword += random.nextInt(10);
			}
			user.setPassword(newPassword);
			UserAccountDAO.updateUser(user);
		}
		return null;
	}
	
	public String odobriNalog()
	{
		Map<String,String> paramMap=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(paramMap.containsKey("id"))
		{
			int id=Integer.parseInt(paramMap.get("id"));
			UserAccount user = UserAccountDAO.selectUserById(id);
			usersToApprove.removeIf(x -> x.getId() == id);
			user.setApproved(true);
			UserAccountDAO.updateUser(user);
		}
		return null;
	}
	
	public String dodajKategoriju()
	{
		EmergencyDAO.insertEmergencyCategory(new EmergencyCategory(0,newEmerCategory,false));
		FacesContext.getCurrentInstance().addMessage("emer-post:cat-name", new FacesMessage("Uspje�no ste dodali: " + newEmerCategory));
		newEmerCategory = null;
		return null;
	}
	
	public String izbrisiPoziv()
	{
		Map<String,String> paramMap=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if(paramMap.containsKey("id"))
		{
			int id=Integer.parseInt(paramMap.get("id"));
			EmergencyCall call = EmergencyDAO.selectCallById(id);
			call.setDeleted(true);
			EmergencyDAO.updateCall(call);
			emergencyCalls.removeIf(x -> x.getId() == id);
		}
		return null;
	}

}
