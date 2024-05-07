package entities;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Sendmail {

	final String username = "bouabid.ala1920@outlook.com"; // Your Outlook username
	final String password = "Alaesprit123."; // Your Outlook password

	public void envoyer(String Toemail, String Subject, String MessageText) {
		// Set properties for the session
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.office365.com");
		props.put("mail.smtp.port", "587");

		// Create the session with authentication
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Create and set the attributes of the message
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(Toemail));
			message.setSubject(Subject);
			message.setText(MessageText);
			message.setSentDate(new Date());

			// Send the message
			Transport.send(message);
			System.out.println("Message sent successfully.");
		} catch (MessagingException e) {
			System.err.println("Failed to send the email, cause: " + e.getMessage());
		}
	}
}
