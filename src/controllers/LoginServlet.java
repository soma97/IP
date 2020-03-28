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


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public LoginServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Page does not exist.").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String errorMessage = null;
		String password = request.getParameter("password");
		UserAccount userAccount = UserAccountDAO.selectUserByUsername(request.getParameter("username"));
		if(userAccount != null)
		{
			if(userAccount.getPassword().equals(password))
			{
				LogingManagement.getInstance().loginUser(userAccount, request.getSession());
				RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");
				dispatcher.forward(request, response);
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
