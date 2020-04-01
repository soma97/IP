package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.PostDAO;
import db.dao.UserAccountDAO;
import db.models.Post;
import db.models.UserAccount;

/**
 * Servlet implementation class DangerServlet
 */
@WebServlet("/DangerServlet")
public class DangerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DangerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserAccount userAccount = UserAccountDAO.selectUserById((int)request.getSession().getAttribute("id"));
		
		String location = request.getParameter("location");
		if(!location.contains(","))
		{
			location = null;
		}
		
		Post post = new Post(0, request.getParameter("title"), request.getParameter("text"), null, 
				null,location,request.getParameter("emergency") != null, true, false, userAccount.getId());
		
		PostDAO.insertPost(post);
		
		response.sendRedirect("dangerDetails.jsp?id="+post.getId());
	}

}
