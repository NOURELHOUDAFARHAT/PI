package entities;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587"; // Utiliser le port 587 avec TLS
    private static final String EMAIL_USERNAME = "yahyaouiwael966@gmail.com";
    private static final String EMAIL_PASSWORD = "xlck tona gpok shgg"; // Utiliser le mot de passe d'application pour Gmail

    private static final Session session = createSession();

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Activer STARTTLS pour la connexion TLS
        props.put("mail.debug", "true");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_USERNAME, EMAIL_PASSWORD);
            }
        });
    }

    public static void sendWelcomeEmailWithSignature(String recipientEmail, String nom) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            message.setSubject("Confirmation Demande de Visite");

            String emailContentWithSignature = "<html><body>" +
                    "<p>Cher " + nom + ",</p>" +
                    "<p><strong>Votre demande de visite a été enregistrée avec succès.</strong></p>" +
                    "<p>Cordialement,<br></p>" +
                    "</body></html>";

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(emailContentWithSignature, "text/html");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);

            message.setContent(multipart);

            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}
