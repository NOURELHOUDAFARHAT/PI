package org.example.lastoflast;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import securite.Credentials;
import securite.SessionFileManager;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SeConnecter  {
    @FXML
    private CheckBox rememberMeCheckbox;

    private static int loggedInUserId;

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    @FXML
    private TextField textFieldEmail;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private Button btn_connecter;

    @FXML
    private Hyperlink btn_inscrit;

    @FXML
    private Label errorEmailLabel;

    @FXML
    private Label errorMdpLabel;

    private static String loggedInUserEmail;

    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }

    @FXML
    private AnchorPane anchorPane;

    private int failedAttempts = 0;
    private boolean fieldsDisabled = false;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        retrieveSavedCredentials();
    }

    @FXML
    void seConnecter(javafx.event.ActionEvent event) {
        String email = textFieldEmail.getText();
        String password = passwordFieldPassword.getText();
        boolean rememberMe = rememberMeCheckbox.isSelected();

        UserService userService = new UserService();

        if (userService.authenticate(email, password)) {
            System.out.println("Utilisateur authentifié avec succès !");
            int userId = userService.getUserIdByEmail(email);
            loggedInUserId = userId;
            loggedInUserEmail = email;

            if (rememberMe) {
                SessionFileManager.saveCredentials(email, password, rememberMe);
            }

            if(email.equals("adminadminadmin@admin.com")) {
                redirectToHome(event);
            }
            else {
                redirectToHomeAll(event);
            }
        } else {
            System.out.println("Vérifiez les données !");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur d'authentification");
            alert.setHeaderText("Échec de l'authentification");
            alert.setContentText("Veuillez vérifier vos informations d'identification et réessayer.");
            alert.showAndWait();

            failedAttempts++;
            if (failedAttempts >= 3 && !fieldsDisabled) {
                disableFields();
            }
        }
    }

    private void disableFields() {
        textFieldEmail.setDisable(true);
        passwordFieldPassword.setDisable(true);
        fieldsDisabled = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                enableFields();
                failedAttempts = 0;
                System.out.println("Les champs sont réactivés après 3 minutes.");
                timer.cancel();
            }
        }, 3 * 60 * 1000);
    }

    private void enableFields() {
        textFieldEmail.setDisable(false);
        passwordFieldPassword.setDisable(false);
        fieldsDisabled = false;
    }

    private void redirectToHomeAll(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("HomeAll.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void redirectToHome(javafx.event.ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void retrieveSavedCredentials() {
        Credentials savedCredentials = SessionFileManager.retrieveSavedCredentials();
        if (savedCredentials != null) {
            textFieldEmail.setText(savedCredentials.getEmail());
            passwordFieldPassword.setText(savedCredentials.getPassword());
        }
    }

    @FXML
    void pageInscription(javafx.event.ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("register.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void mdpOublier(javafx.event.ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("resetPassword.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
