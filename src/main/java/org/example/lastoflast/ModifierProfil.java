package org.example.lastoflast;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import services.UserService;

public class ModifierProfil {

    @FXML
    private TextField id_nom;

    @FXML
    private TextField id_prenom;

    @FXML
    private TextField id_adresse;

    @FXML
    private TextField id_sexe;

    @FXML
    private Button btn_modifier;

    private UserService userService;

    public ModifierProfil() {
        userService = new UserService();
    }

    @FXML
    void initialize() {
        // Obtention de l'identifiant de l'utilisateur connecté
        int userId = SeConnecter.getLoggedInUserId();

        // Récupération des informations de l'utilisateur connecté
        User loggedInUser = userService.getById(userId);

        // Remplissage des champs de texte avec les informations de l'utilisateur
        id_nom.setText(loggedInUser.getNom());
        id_prenom.setText(loggedInUser.getPrenom());
        id_adresse.setText(loggedInUser.getAdresse());
        id_sexe.setText(loggedInUser.getSexe());
    }

    @FXML
    void modifierProfil(ActionEvent event) {
        // Récupération des nouvelles valeurs des champs de texte
        String nom = id_nom.getText();
        String prenom = id_prenom.getText();
        String adresse = id_adresse.getText();
        String sexe = id_sexe.getText();

        // Obtention de l'identifiant de l'utilisateur connecté
        int userId = SeConnecter.getLoggedInUserId();

        // Création d'un nouvel objet User avec les nouvelles valeurs
        User updatedUser = new User(userId, nom, prenom, adresse, sexe);

        // Appel de la méthode updateUser de UserService pour mettre à jour le profil de l'utilisateur
        userService.updateUserr(updatedUser);

        System.out.println("Profil utilisateur mis à jour avec succès !");
    }

}
