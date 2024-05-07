package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import entities.Staff;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.CustomSession;
import services.ServiceStaff;

import java.io.IOException;
import java.sql.SQLException;

public class AjoutStaff {


    @FXML
    private Button button_Submit;

    @FXML
    private Button button_Submit1;

    @FXML
    private ImageView image_input;

    @FXML
    private TextField nomStaff;

    @FXML
    private TextField numtelStaff;

    @FXML
    private TextField typeStaff;

    private final ServiceStaff serviceStaff = new ServiceStaff();
    @FXML
    private BorderPane borderPane;
    @FXML
    private BorderPane rootPane;
    private static String loggedInUserEmail;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;


    @FXML
    public void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
    }




    private void redirectToLoginPage() {
        try {
            // Chargement de la page de connexion
            Parent loginPage = FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(loginPage);
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterStaff(ActionEvent event) {
        String nom = nomStaff.getText();
        String type = typeStaff.getText();
        String numTel = numtelStaff.getText();

        if (isValidInput(nom, type, numTel)) {
            try {
                Staff nouveauStaff = new Staff(nom, type, numTel);
                serviceStaff.ajouter(nouveauStaff);
                showAlert("Le staff a été ajouté avec succès.");
            } catch (SQLException e) {
                showAlert("Erreur lors de l'ajout du staff : " + e.getMessage());
            }
        } else {
            showAlert("Veuillez vérifier vos informations.");
        }
    }

    private boolean isValidInput(String nom, String type, String numTel) {
        if (nom == null || nom.isEmpty()) {
            showAlert("Le champ Nom est obligatoire.");
            return false;
        }

        if (type == null || type.isEmpty()) {
            showAlert("Le champ Type est obligatoire.");
            return false;
        }

        if (numTel == null || numTel.isEmpty()) {
            showAlert("Le champ Numéro de téléphone est obligatoire.");
            return false;
        }

        if (!numTel.matches("\\d{8}")) {
            showAlert("Le numéro de téléphone doit contenir exactement 8 chiffres.");
            return false;
        }

        return true;
    }



    @FXML
    void naviguerversaffichagestaff(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AfficherStaff.fxml"));
            nomStaff.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la vue : " + e.getMessage());
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
