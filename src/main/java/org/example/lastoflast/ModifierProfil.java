package org.example.lastoflast;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.CustomSession;
import services.UserService;

import java.io.IOException;

public class ModifierProfil {
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
    private AnchorPane anchorPane; // Déclarez un champ anchorPane et ajoutez l'annotation @FXML

    @FXML
    private TextField id_nom;

    @FXML
    private TextField id_prenom;

    @FXML
    private ChoiceBox<String> id_adresse; // Utilisez une ChoiceBox pour l'adresse

    @FXML
    private ChoiceBox<String> id_sexe; // Utilisez une ChoiceBox pour le sexe

    @FXML
    private Button btn_modifier;

    private UserService userService;

    public ModifierProfil() {
        userService = new UserService();
    }

    @FXML
    void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        // Obtention de l'identifiant de l'utilisateur connecté
        int userId = SeConnecter.getLoggedInUserId();

        // Récupération des informations de l'utilisateur connecté
        User loggedInUser = userService.getById(userId);

        if (loggedInUser != null) {
            // Remplissage des champs de texte avec les informations de l'utilisateur
            id_nom.setText(loggedInUser.getNom());
            id_prenom.setText(loggedInUser.getPrenom());

            // Sélection de l'adresse et du sexe dans les ChoiceBox
            id_adresse.setValue(loggedInUser.getAdresse());
            id_sexe.setValue(loggedInUser.getSexe());
        } else {
            System.out.println("Utilisateur non trouvé !");
        }

        // Ajout des valeurs aux ChoiceBox
        id_adresse.getItems().addAll(
                "Ariana", "Beja", "Ben Arous", "Bizerte", "Gabes", "Gafsa", "Jendouba",
                "Kairouan", "Kasserine", "Kebili", "La Manouba", "Le Kef", "Mahdia",
                "Médenine", "Monastir", "Nabeul", "Sfax", "Sidi Bouzid", "Siliana",
                "Sousse", "Tataouine", "Tozeur", "Tunis", "Zaghouan"
        );

        id_sexe.getItems().addAll("Homme", "Femme");
    }

    @FXML
    void modifierProfil(ActionEvent event) {
        // Mise à jour du profil de l'utilisateur
        String nom = id_nom.getText();
        String prenom = id_prenom.getText();
        String adresse = id_adresse.getValue();
        String sexe = id_sexe.getValue();
        int userId = SeConnecter.getLoggedInUserId();
        User updatedUser = new User(userId, nom, prenom, adresse, sexe);
        userService.update(updatedUser, userId);

        // Redirection vers la page HomeAll
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("HomeAll.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    void retourVersHomeAll(ActionEvent event) {
        try {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("HomeAll.fxml"));
            anchorPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}


