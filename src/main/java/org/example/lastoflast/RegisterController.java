package org.example.lastoflast;

import java.io.IOException;
import java.util.Properties;

import javafx.scene.control.*;
import org.mindrot.jbcrypt.BCrypt;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import entities.User;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.UserService;

public class RegisterController {

    @FXML
    private Button btn_ajouter;

    @FXML
    private TextField id_nom;

    @FXML
    private TextField id_prenom;

    @FXML
    private TextField id_email;

    @FXML
    private PasswordField id_motdepasse;

    @FXML
    private ChoiceBox<String> id_adresse;

    @FXML
    private ChoiceBox<String> id_sexe;

    @FXML
    private Hyperlink seConnecter;

    @FXML
    private Label error_nom;

    @FXML
    private Label error_prenom;

    @FXML
    private Label error_email;

    @FXML
    private Label error_motdepasse;

    private String codeVerification;

    @FXML
    void Ajouter(ActionEvent event) {
        // Validation du formulaire
        if (validerFormulaire()) {
            // Génération du code de vérification
            genererCodeVerification();

            // Affichage de la fenêtre contextuelle pour saisir le code de vérification
            afficherFenetreVerification(event);
        }
    }



    @FXML
    private void initialize() {
        // Get the email address from the text field
        String email = id_email.getText();

        // Check if the email address is not empty and is valid
        if (!email.isEmpty() && ValidationFormulaireController.validerEmail(email)) {
            // Call the method to send the confirmation email
            envoyerEmailConfirmation(email);
        } else {
            // Handle invalid or empty email address (display error message, disable registration button, etc.)
            System.out.println("Invalid email address or empty field. Cannot send confirmation email.");
        }
    }


    private void envoyerEmailConfirmation(String recipientEmail) {
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
            message.setText("Bonjour,\n\n Bienvenu dans notre service Votre code de confirmation est :"+codeVerification);

            // Envoi du message
            Transport.send(message);

            System.out.println("Email envoyé avec succès à : " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    // Fonction de validation du formulaire
    private boolean validerFormulaire() {
        // Récupération des valeurs des champs
        String nom = id_nom.getText();
        String prenom = id_prenom.getText();
        String email = id_email.getText();
        String password = id_motdepasse.getText(); // Mot de passe non crypté
        String adresse = id_adresse.getValue();
        String sexe = id_sexe.getValue();
        String[] roles = new String[]{};

        // Validation des champs du formulaire
        boolean isValid = true;
        if (!ValidationFormulaireController.validerNom(nom)) {
            error_nom.setText("Le nom doit contenir au moins 3 caractères.");
            isValid = false;
        } else {
            error_nom.setText("");
        }

        if (!ValidationFormulaireController.validerPrenom(prenom)) {
            error_prenom.setText("Le prénom doit contenir au moins 5 caractères.");
            isValid = false;
        } else {
            error_prenom.setText("");
        }

        if (!ValidationFormulaireController.validerEmail(email)) {
            error_email.setText("Veuillez entrer une adresse e-mail valide.");
            isValid = false;
        } else {
            error_email.setText("");
        }

        if (!ValidationFormulaireController.validerMotDePasse(password)) {
            error_motdepasse.setText("Le mot de passe doit contenir au moins un chiffre, une lettre majuscule, une lettre minuscule, un caractère spécial et avoir une longueur minimale de 8 caractères.");
            isValid = false;
        } else {
            error_motdepasse.setText("");
        }

        if (adresse == null || adresse.isEmpty()) {
            System.out.println("Veuillez sélectionner une adresse.");
            isValid = false;
        }

        return isValid;
    }

    // Génération du code de vérification
    private void genererCodeVerification() {
        codeVerification = String.format("%04d", (int)(Math.random() * 10000)); // Génération d'un nombre aléatoire de 4 chiffres
        System.out.println("Code de vérification : " + codeVerification); // Affichage du code dans la console
        String email = id_email.getText();
        envoyerEmailConfirmation(email);
    }

    // Affichage de la fenêtre contextuelle pour saisir le code de vérification
    private void afficherFenetreVerification(ActionEvent event) {
        // Création de la fenêtre contextuelle (popup) pour saisir le code de vérification
        TextInputDialog dialog = new TextInputDialog();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Vérification");
        dialog.setHeaderText("Entrez le code de vérification (4 chiffres):");
        dialog.setContentText("Code:");

        // Affichage de la fenêtre contextuelle et récupération de la valeur saisie
        dialog.showAndWait().ifPresent(codeSaisi -> {
            if (codeSaisi.equals(codeVerification)) {
                // Si le code de vérification est correct, continuez avec l'ajout de l'utilisateur
                ajouterUtilisateur();
                // Redirection vers la page de connexion
                redirigerVersConnexion(event);
                // Envoi du code de vérification par e-mail

            } else {
                // Si le code de vérification est incorrect, affichez un message d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le code de vérification est incorrect. Veuillez réessayer.");
                alert.showAndWait();
            }
        });
    }

    // Ajout de l'utilisateur
    private void ajouterUtilisateur() {
        String nom = id_nom.getText();
        String prenom = id_prenom.getText();
        String email = id_email.getText();
        String password = id_motdepasse.getText();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        String adresse = id_adresse.getValue();
        String sexe = id_sexe.getValue();
        String[] roles = new String[]{};

        UserService userService = new UserService();
        User newUser = new User(email, roles, hashedPassword, nom, prenom, adresse, sexe, false);
        userService.add(newUser);
        System.out.println("Utilisateur ajouté avec succès !");
    }

    // Redirection vers la page de connexion
    private void redirigerVersConnexion(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println("Erreur lors de la redirection vers la page de connexion : " + ex.getMessage());
        }
    }

    @FXML
    void seConnecter(ActionEvent event) {
        redirigerVersConnexion(event);
    }
}
