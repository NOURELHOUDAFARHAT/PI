package org.example.lastoflast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import services.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ResetPassword {

    @FXML
    private TextField id_email;

    @FXML
    private TextField verificationCodeField;

    @FXML
    private Button verifyCodeButton;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField confirmNewPasswordField;

    @FXML
    private Button changePasswordButton;

    private String verificationCode;

    private UserService userService = new UserService();

    @FXML
    void resetPassword() {
        String email = id_email.getText();

        generateVerificationCode();
        sendVerificationCodeByEmail(email);

        // Afficher une boîte de dialogue pour saisir le code de vérification
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Code de vérification");
        dialog.setHeaderText(null);
        dialog.setContentText("Veuillez entrer le code de vérification envoyé à votre adresse e-mail:");

        // Attendre la saisie de l'utilisateur et vérifier le code
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::verifyCodeAndChangePassword);
    }

    private void generateVerificationCode() {
        Random random = new Random();
        int code = 1000 + random.nextInt(9000);
        verificationCode = String.valueOf(code);
        System.out.println(verificationCode);
    }

    private void sendVerificationCodeByEmail(String recipientEmail) {
        // Paramètres SMTP pour Gmail
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "yahyaouiwael966@gmail.com"; // Remplacez par votre adresse email
        String password = "xlck tona gpok shgg"; // Remplacez par votre mot de passe

        // Propriétés pour la session SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Création de la session SMTP avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Confirmation d'inscription");
            message.setText("Bonjour,\n\n Bienvenu dans notre service Votre code de confirmation est :" + verificationCode);

            // Envoi du message
            Transport.send(message);

            System.out.println("Email envoyé avec succès à : " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void verifyCodeAndChangePassword(String enteredCode) {
        if (enteredCode.equals(verificationCode)) {
            verificationCodeField.setVisible(false);
            verificationCodeField.setManaged(false);
            verifyCodeButton.setVisible(false);
            verifyCodeButton.setManaged(false);

            // Afficher un nouveau champ pour saisir le nouveau mot de passe
            // Vous pouvez implémenter cette logique ici ou appeler une méthode distincte pour le faire
            // Par exemple :
            showChangePasswordFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Code de vérification incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Le code de vérification que vous avez saisi est incorrect. Veuillez réessayer.");
            alert.show();
        }
    }

    private void showChangePasswordFields() {
        // Implémentez ici la logique pour afficher les champs de saisie du nouveau mot de passe
        // Par exemple :
        newPasswordField.setVisible(true);
        confirmNewPasswordField.setVisible(true);
        changePasswordButton.setVisible(true);
    }

    @FXML
    void changePassword() {
        // Implémentez ici la logique pour changer le mot de passe
        String newPassword = newPasswordField.getText();
        String confirmedPassword = confirmNewPasswordField.getText();

        if (newPassword.equals(confirmedPassword)) {
            // Appelez la méthode de service pour changer le mot de passe
            // Par exemple :
            int userId = userService.getUserIdByEmail(id_email.getText());
            userService.changePassword(userId, newPassword);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Mot de passe changé");
            alert.setHeaderText(null);
            alert.setContentText("Votre mot de passe a été changé avec succès.");
            alert.show();

            // Fermer la fenêtre de réinitialisation du mot de passe
            Stage stage = (Stage) id_email.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nouveau mot de passe incorrect");
            alert.setHeaderText(null);
            alert.setContentText("Les mots de passe ne correspondent pas. Veuillez réessayer.");
            alert.show();
        }
    }

    @FXML
    void seConnecter(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
