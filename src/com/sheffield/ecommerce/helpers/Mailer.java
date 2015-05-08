package com.sheffield.ecommerce.helpers;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import com.sheffield.ecommerce.models.User;

/**
 * Class is responsible for sending emails
 */
public class Mailer {
	
	/**
	 * Create an email by passing in a user (containing an email), an email subject and the email body
	 * @param user
	 * @param subject
	 * @param content
	 */
	public static void sendEmail(User user,String subject, String content) {
		
		  // Recipient's email
	      String to = user.getEmail();

	      // Settings for the mailing server
	      Properties properties = System.getProperties();
	      properties.put("mail.smtp.starttls.enable", true);
	      properties.put("mail.smtp.host", "smtp.gmail.com");
	      properties.put("mail.smtp.user", "noreply.journalsystem@gmail.com");
	      properties.put("mail.smtp.password", "team10system");
	      properties.put("mail.smtp.port", "587");
	      properties.put("mail.smtp.auth", true);

	      // Get the default Session object with the GmailAuthenticator
	      Session session = Session.getDefaultInstance(properties,new GMailAuthenticator("noreply.journalsystem@gmail.com","team10system"));

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Set email content
	         message.setText(content);

	         // Send email
	         Transport.send(message);
	      } catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

}
