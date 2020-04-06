package services;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import db.dao.UserAccountDAO;
import db.models.UserAccount;

public class MailService {

	public static void sendEmailToSubscribedUsers(String title, String content)
	{
		ArrayList<UserAccount> allUserAccounts = UserAccountDAO.selectAllUsers();
		ArrayList<String> recipients = new ArrayList<String>();
		for(UserAccount user : allUserAccounts)
		{
			if(user.getReceiveEmergencyNotifications().equals("email"))
			{
				recipients.add(user.getEmail());
			}
		}
		if(recipients.size() > 0)
		{
			sendEmail(recipients, title, content);
		}
	}
	
	static void sendEmail(ArrayList<String> recipients, String title, String content)
	{
		final String username = "milos.ip.test@gmail.com";
		final String password = "glicerin1";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		String recipientsString = "";
		for(String recipient : recipients)
		{
			recipientsString += recipient +",";
		}
		
		recipientsString = recipientsString.substring(0, recipientsString.length() - 1);

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username, "Emergency service"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientsString));
			message.setSubject(title);
			message.setText(content);

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
