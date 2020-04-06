package beans;

import java.util.ArrayList;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import db.dao.EmergencyDAO;
import db.models.*;

@ManagedBean(name="homeBean")
@RequestScoped
public class HomeBean {
	
	public String title;
	public String description;
	public String imageSource;
	public String location;
	public int callCategoryId;
	public ArrayList<CallCategory> callCategories;
	
	public String status;
	
	public HomeBean()
	{
		callCategories = EmergencyDAO.selectCallCategories();
		status="";
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

	public int getCallCategoryId() {
		return callCategoryId;
	}

	public void setCallCategoryId(int callCategoryId) {
		this.callCategoryId = callCategoryId;
	}

	public ArrayList<CallCategory> getCallCategories() {
		return callCategories;
	}

	public void setCallCategories(ArrayList<CallCategory> callCategories) {
		this.callCategories = callCategories;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String dodajPoziv()
	{
		if(location != null && !location.contains(","))
		{
			location = null;
		}
		status = "Poziv je podijeljen";
		EmergencyDAO.insertEmergencyCall(new EmergencyCall(0, title, description, new Date(System.currentTimeMillis()), location, imageSource, 0, false, callCategoryId));
		return "homePage.xhtml?faces-redirect=true&status="+status;
	}
	
}
