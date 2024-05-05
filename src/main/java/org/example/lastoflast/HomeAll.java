package org.example.lastoflast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import securite.CustomSession;
import  controllers.FrontBien;

import java.io.IOException;

public class HomeAll {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Déclaration de la variable loggedInUserEmail
    private static String loggedInUserEmail;

    // Méthode pour définir loggedInUserEmail
    public static void setLoggedInUserEmail(String email) {
        loggedInUserEmail = email;
    }



    @FXML
    private Button btn_formation;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;
    @FXML
    private Button btn_bien;


    @FXML
    private Button btn_parameter;

    @FXML
    private Button btn_projetApp;

    @FXML
    private Button btn_refresh;

    @FXML
    private StackPane centerPane;

    @FXML
    private Button forfaits;

    @FXML
    private Button id_evaluation;

    @FXML
    private Button id_home;


    @FXML
    private Button id_panier;

    @FXML
    private VBox id_side_bar;

    @FXML
    private BorderPane rootPane;
    //////////////
    @FXML
    private Button loginButton;

    @FXML
    private BorderPane borderPane;


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
    void EvaluationFormateur(ActionEvent event) {

    }

    @FXML
    void Forfaits(ActionEvent event) {
        // Redirection vers la page Modifierprofil
        try {
            Parent root = FXMLLoader.load(getClass().getResource("modifierprofil.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Gérer les erreurs de chargement de la page
            ex.printStackTrace();
        }
    }


    @FXML
    void frontBien(ActionEvent event) {
        try {
            // Utilisez getClass().getResource() pour obtenir le chemin relatif au package
            // Assurez-vous que le chemin est correctement spécifié en fonction de l'emplacement de FrontBien.fxml
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/FrontBien.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
        }
    }
    @FXML
    void formationApprenants(ActionEvent event) {

    }

    @FXML
    void home(ActionEvent event) {

    }

    @FXML
    void modification(ActionEvent event) {

    }

    @FXML
    void refreshData(ActionEvent event) {

    }
    public static String getLoggedInUserEmail() {
        return loggedInUserEmail;
    }
    @FXML
    void seDeconnecter(ActionEvent event) {
        loggedInUserEmail = null; // Réinitialise la session utilisateur
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Afficher une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());
        }
    }


}
