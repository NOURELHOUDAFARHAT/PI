package org.example.lastoflast;

import entities.User;
import javafx.fxml.FXMLLoader;
import services.UserService;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Afficheruser implements Initializable {

    @FXML
    private TableColumn<User, String> id_email;

    @FXML
    private TableColumn<User, String> id_nom;

    @FXML
    private TableColumn<User, String> id_prenom;

    @FXML
    private TableColumn<User, String> id_role;

    @FXML
    private TableColumn<User, Boolean> id_isActivated;

    @FXML
    private TableView<User> tab_Admin;

    @FXML
    private ChoiceBox<String> roleChoiceBox;

    @FXML
    private ChoiceBox<Boolean> activationChoiceBox;


    private ObservableList<Boolean> activationOptions = FXCollections.observableArrayList(true, false);
    private UserService userService = new UserService(); // Instance de UserService

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    void afficherCardUser(ActionEvent event) {
        User user = tab_Admin.getSelectionModel().getSelectedItem();
        if (user != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("CardUser.fxml"));
                Parent root = loader.load();
                CardUserController controller = loader.getController();
                controller.initData(user);
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un utilisateur pour afficher les détails.");
            alert.showAndWait();
        }
    }


    @FXML
    void supprimer(ActionEvent event) {
        User user = tab_Admin.getSelectionModel().getSelectedItem();
        if (user != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer l'utilisateur ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");

            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        userService.delete(user.getId());
                        tab_Admin.getItems().remove(user);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Succès");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Utilisateur supprimé avec succès !");
                        successAlert.showAndWait();
                    } catch (Exception e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Erreur");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
                        errorAlert.showAndWait();
                    }
                }
            });
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Aucune sélection");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Veuillez sélectionner un utilisateur à supprimer.");
            warningAlert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        id_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        id_email.setCellValueFactory(new PropertyValueFactory<>("email"));

        id_isActivated.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isIs_activated()));


        activationChoiceBox.setItems(activationOptions);

        tab_Admin.getItems().clear();

        try {
            List<User> users = userService.getAll();
            if (!users.isEmpty()) {
                tab_Admin.getItems().addAll(users);
            } else {
                System.out.println("Aucun utilisateur trouvé dans la base de données.");
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des utilisateurs depuis la base de données: " + e.getMessage());
        }

        tab_Admin.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double clic
                User user = tab_Admin.getSelectionModel().getSelectedItem();
                if (user != null) {
                    modifierUtilisateur(user);
                }
            }
        });
    }

    private void modifierUtilisateur(User user) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Modifier l'utilisateur");
        dialog.setHeaderText("Modifier les attributs de l'utilisateur");

        TextField nomField = new TextField(user.getNom());
        TextField prenomField = new TextField(user.getPrenom());
        TextField emailField = new TextField(user.getEmail());



        activationChoiceBox.setValue(user.isIs_activated());

        dialog.getDialogPane().setContent(new VBox(10, new Label("Nom:"), nomField, new Label("Prénom:"), prenomField, new Label("Email:"), emailField, new Label("Activated:"), activationChoiceBox));

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                user.setNom(nomField.getText());
                user.setPrenom(prenomField.getText());
                user.setEmail(emailField.getText());
                // Assurez-vous que les rôles sont stockés dans le format correct

                user.setisIs_activated(activationChoiceBox.getValue());
                try {
                    userService.update(user, user.getId());
                    tab_Admin.refresh();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Utilisateur mis à jour avec succès !");
                    alert.showAndWait();
                } catch (Exception e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Erreur lors de la mise à jour de l'utilisateur : " + e.getMessage());
                    alert.showAndWait();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }
}