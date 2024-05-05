package org.example.lastoflast;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;import java.util.Properties;

public class EmailController {

    @FXML
    private TextField recipientField;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField messageField;

    @FXML
    void sendEmail() {
        String recipient = recipientField.getText();
        String subject = subjectField.getText();
        String messageText = messageField.getText();

        // Paramètres de connexion SMTP
        String host = "smtp.example.com";
        String port = "587";
        String username = "your_email@example.com";
        String password = "your_email_password";

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

    }
}