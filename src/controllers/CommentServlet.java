package controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.dao.PostDAO;
import db.models.Comment;
import services.FilesService;

/**
 * Servlet implementation class CommentServlet
 */
@WebServlet("/CommentServlet")
@MultipartConfig
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.sendRedirect(request.getHeader("referer"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("user-id"));
		int postId = Integer.parseInt(request.getParameter("post-id"));
		String comment = request.getParameter("comment");
		String imageSource = null;
		Part part = request.getPart("comment-image");
		
		if(part != null && part.getSize()>0)
		{
			imageSource = FilesService.saveMedia(part, comment.substring(0,5));
		}
		
		PostDAO.insertComment(new Comment(0, comment, imageSource, new Date(System.currentTimeMillis()), false, userId, postId));
		response.sendRedirect(request.getHeader("referer"));
	}

}
