package org.example.lastoflast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import controllers.AfficherBien;
import securite.CustomSession;

import java.io.IOException;

public class Home {
    @FXML
    private Button id_activite;

    @FXML
    private Button btn_tableau;
    @FXML
    private Button id_afficherBien;

    @FXML
    private VBox id_side_bar;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;
    private static String loggedInUserEmail;
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
    void affichertableauusers(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("Afficheradmin.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
        }
    }
    @FXML
    void afficherBien(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("AfficherBien.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
        }
    }
    @FXML
    void seDeconnecter(ActionEvent event) {
        // Mettez ici le code pour se déconnecter de l'application
        // Par exemple, réinitialisez les données de session, etc.
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Affichez une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());
        }
    }
    @FXML
    void activite(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AjoutActivite.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Affichez une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());
        }
    }

}
