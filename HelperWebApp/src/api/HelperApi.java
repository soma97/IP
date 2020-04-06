package api;

import java.util.ArrayList;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jdom2.Element;
import org.jdom2.Namespace;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import db.dao.EmergencyDAO;
import db.models.EmergencyCall;

@Path("/helper")
public class HelperApi {
	
	public static final String BASE_URL = "http://localhost:8080/HelperWebApp";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<EmergencyCall> getAll() {
		return EmergencyDAO.selectAllCalls();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getOne(@PathParam("id") int id) {
		return Response.status(200).entity(EmergencyDAO.selectCallById(id)).build();
	}
	
	@PUT
	@Path("/{id}")
	public Response denunciation(@PathParam("id") int id) {
		EmergencyCall call = EmergencyDAO.selectCallById(id);
		if (call != null) {
			call.setNumberOfDenouncements(call.getNumberOfDenouncements()+1);
			EmergencyDAO.updateCall(call);
			return Response.status(200).build();
		} else {
			return Response.status(404).entity("Call doesn't exist").build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response remove(@PathParam("id") int id) {
		EmergencyCall call = EmergencyDAO.selectCallById(id);
		if (call != null) {
			call.setDeleted(true);
			EmergencyDAO.updateCall(call);
			return Response.status(200).build();
		} else {
			return Response.status(404).entity("Call doesn't exist").build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/rss")
	public Response getRSS() {
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("Pruzanje pomoci");
		feed.setLink(BASE_URL);
		feed.setDescription("RSS feed poziva za pruzanje pomoci");
		ArrayList<EmergencyCall> emergencyCalls = EmergencyDAO.selectAllCalls();
		
		ArrayList<SyndEntry> entries = new ArrayList<SyndEntry>();
		for(EmergencyCall call : emergencyCalls)
		{
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(call.getTitle());        
			entry.setLink(BASE_URL + "/api/helper/"+call.getId());
			entry.setPublishedDate(call.getDate());
			
			Element image = new Element("image", Namespace.getNamespace("image", "http://web.resource.org/rss/1.0/modules/image/"));
			image.addContent(call.getImageSource());
			entry.getForeignMarkup().add(image);
			
			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue(call.getDescription());
			entry.setDescription(description);
			
			entries.add(entry);
		}
		feed.setEntries(entries);
		SyndFeedOutput syndFeedOutput = new SyndFeedOutput();
		String rss = "";
		try {
			rss = syndFeedOutput.outputString(feed);
		} catch (FeedException e) {
			e.printStackTrace();
			return Response.status(500).build();
		}
		return Response.status(200).entity(rss).build();
	}

}