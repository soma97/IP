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
			imageSource = saveImage(filePart, username);
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
	
	private String saveImage(Part filePart,String username)
	{
		String filePath = "images/";
		try
		{
			String extension = "." + filePart.getSubmittedFileName().split("\\.")[1];
			String fileName = username + (new Random().nextInt(9999999)) + extension;
			InputStream fileContent = filePart.getInputStream();
			byte[] fileBytes = fileContent.readAllBytes();
			
			//File file = new File(getServletContext().getRealPath("images") + File.separator + fileName);
			// OVAKO NA DEPLOYMENTU
			File file = new File("C:\\Users\\milos\\eclipse-workspace\\EmergencyWebApp\\WebContent\\images" + File.separator + fileName);
			// PRIVREMENO ZBOG PATHA DEV SERVERA
			
			filePath += fileName;
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file);
				fos.write(fileBytes);
			}
			catch (FileNotFoundException e) {
				System.out.println("File not found" + e);
			}
			catch (IOException ioe) {
				System.out.println("Exception while writing file " + ioe);
			}
			finally {
				try {
					if (fos != null) {
						fos.close();
					}
				}
				catch (IOException ioe) {
					System.out.println("Error while closing stream: " + ioe);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return filePath;
	}

}
