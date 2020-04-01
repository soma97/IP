package controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.dao.ImageDAO;
import db.dao.PostDAO;
import db.dao.UserAccountDAO;
import db.models.Comment;
import db.models.ImagePath;
import db.models.Post;
import db.models.UserAccount;
import services.FilesService;

@WebServlet("/PostServlet")
@MultipartConfig
public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("homePage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount userAccount = UserAccountDAO.selectUserById((int)request.getSession().getAttribute("id"));
		
		List<Part> imageParts = request.getParts().stream().filter(part -> part.getName().equals("images") && 
				part.getSize()>0).collect(Collectors.toList());
		
		Part videoPart = request.getPart("video");
		String videoPath = null;
		String videoType = request.getParameter("video-type");
		if(videoType.equals("upload"))
		{
			if(videoPart.getSize() > 0)
			{
				videoPath = FilesService.saveMedia(videoPart, userAccount.getUsername());
			}
		}
		else {
			videoPath = request.getParameter("video");
			if(!videoPath.contains("http") && videoPath.length()>1)
			{
				videoPath = "https://" + videoPath;
			}
			if(videoPath.length()<2)
			{
				videoPath = null;
			}
		}
		
		String link = request.getParameter("link");
		if(!link.contains("http") && link.length()>1)
		{
			link = "http://" + link;
		}
		if(link.length()<2)
		{
			link = null;
		}
		
		Post post = new Post(0, request.getParameter("title"), request.getParameter("text"), link, 
				videoPath,null,false, false, false, userAccount.getId());
		
		PostDAO.insertPost(post);
	
		for(Part part : imageParts)
		{
			String imagePath = FilesService.saveMedia(part, userAccount.getUsername());
			ImageDAO.insertImage(new ImagePath(0, imagePath, post.getId()));
		}
		
		response.sendRedirect("homePage.jsp");
	}

}
