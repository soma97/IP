package controllers;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import db.dao.UserAccountDAO;
import db.models.UserAccount;
import services.LogingManagement;


@WebServlet("/AuthServlet")
public class AuthServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AuthServlet() {
        super();
    }

    //LOGOUT
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogingManagement.getInstance().logoutUser(request.getSession());
		response.sendRedirect("login.jsp");
	}

	//LOGIN
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String errorMessage = null;
		String password = request.getParameter("password");
		UserAccount userAccount = UserAccountDAO.selectUserByUsername(request.getParameter("username"));
		if(userAccount != null)
		{
			if(userAccount.isApproved() == false)
			{
				errorMessage = "Nalog nije odobren";
			}
			else if(userAccount.isBlocked())
			{
				errorMessage = "Nalog je blokiran :(";
			}
			else if(userAccount.getPassword().equals(password))
			{
				LogingManagement.getInstance().loginUser(userAccount, request.getSession());
				response.sendRedirect("homePage.jsp");
				return;
			}
			else {
				errorMessage = "Neispravna lozinka";
			}
		}
		else {
			errorMessage = "Neispravno korisničko ime";
		}
		request.setAttribute("error", errorMessage);
		RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
		dispatcher.forward(request, response);
	}

}
