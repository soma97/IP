package services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import db.dao.UserAccountDAO;
import db.models.Login;

@Path("/activity")
public class LoginStatus {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public HashMap<Integer, Integer> getAll() {
		HashMap<Integer, Integer> activityPerHours = new HashMap<Integer, Integer>();
		ArrayList<Login> logins = UserAccountDAO.selectAllLogins();
		Date nowDate = new Date(System.currentTimeMillis());
		Date yesterdayDate = new Date(System.currentTimeMillis() - (1000*60*60*24));
		
		List<Login> back24Hours = logins.stream().filter(x -> x.getDateLogin().before(nowDate) && 
				x.getDateLogin().after(yesterdayDate)).collect(Collectors.toList());
		
		for(int i=0;i<24;++i)
		{
			activityPerHours.put(i, 0);
		}

		for(Login login : back24Hours)
		{
			int hour = new Date(login.getDateLogin().getTime()).getHours();
			activityPerHours.put(hour, activityPerHours.get(hour)+1);
		}
		return activityPerHours;
	}

}
