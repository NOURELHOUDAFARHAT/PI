package org.example.lastoflast;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    @FXML
    private Button btn_tableau;

    @FXML
    private VBox id_side_bar;

    @FXML
    void affichertableauusers(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("afficheradmin.fxml"));
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

}
