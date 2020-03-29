package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.servlet.http.Part;

public class FilesService {
	
	public static String saveMedia(Part filePart,String username)
	{
		String filePath = "media/";
		try
		{
			String extension = "." + filePart.getSubmittedFileName().split("\\.")[1];
			String fileName = username + (new Random().nextInt(9999999)) + extension;
			InputStream fileContent = filePart.getInputStream();
			byte[] fileBytes = fileContent.readAllBytes();
			
			//File file = new File(getServletContext().getRealPath("images") + File.separator + fileName);
			// OVAKO NA DEPLOYMENTU
			File file = new File("C:\\Users\\milos\\eclipse-workspace\\EmergencyWebApp\\WebContent\\media" + File.separator + fileName);
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
