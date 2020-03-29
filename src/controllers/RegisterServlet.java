package controllers;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import db.dao.UserAccountDAO;
import db.models.UserAccount;
import services.Constants;
import services.LogingManagement;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String result = checkForDuplicates(username, email);
		response.getWriter().print(result);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		if(checkForDuplicates(username, email).length()>3)
		{
			response.getWriter().println("Error occurred. Please enable JavaScript.");
			return;
		}
		UserAccount userAccount = new UserAccount(0, request.getParameter("name"), request.getParameter("surname"),
				username, request.getParameter("password"), email, 
				null, null, null, null, "no", 0, false, false, false, true);
		UserAccountDAO.insertUser(userAccount);
		LogingManagement.getInstance().loginUser(userAccount, request.getSession());
		request.setAttribute("user", userAccount);
		RequestDispatcher dispatcher = request.getRequestDispatcher("editProfile.jsp");
		dispatcher.forward(request, response);
	}
	
	private String checkForDuplicates(String username, String email)
	{
		ArrayList<UserAccount> users = UserAccountDAO.selectAllUsers();
		String result = "{";
		for (UserAccount userAccount : users) {
			if(userAccount.getUsername().equals(username))
			{
				result += "\"username\":true";
			}
			if(userAccount.getEmail().equals(email))
			{
				result += (result.length()>1 ? ",": "") + "\"email\":true";
			}
			if(result.length()>1)
			{
				break;
			}
		}
		return result += "}";
	}

}
