package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import db.dao.UserAccountDAO;
import db.models.UserAccount;
import services.FilesService;

/**
 * Servlet implementation class EditProfileServlet
 */
@WebServlet("/EditProfileServlet")
@MultipartConfig
public class EditProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Page does not exist.").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		Part filePart = request.getPart("image");
		String imageSource = null;
		if(filePart.getSize() == 0)
		{
			imageSource = request.getParameter("countryFlag");
		}
		else {
			imageSource = FilesService.saveMedia(filePart, username);
		}
		
		UserAccount userAccount = UserAccountDAO.selectUserById(Integer.parseInt(request.getParameter("id")));
		
		userAccount.setName(request.getParameter("name"));
		userAccount.setSurname(request.getParameter("surname"));
		userAccount.setUsername(username);
		userAccount.setEmail(request.getParameter("email"));
		userAccount.setCountry(request.getParameter("country"));
		userAccount.setRegion(request.getParameter("region"));
		userAccount.setCity(request.getParameter("city"));
		userAccount.setReceiveEmergencyNotifications(request.getParameter("notifications"));
		userAccount.setImageSource(imageSource);
		UserAccountDAO.updateUser(userAccount);
		RequestDispatcher dispatcher = request.getRequestDispatcher("homePage.jsp");
		dispatcher.forward(request, response);
		
	}

}
