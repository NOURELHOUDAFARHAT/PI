

package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import entities.activite;
import javafx.stage.Stage;
import securite.CustomSession;
import services.ServiceActivite;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AjoutActivite {

    @FXML
    private Button Inserer;

    @FXML
    private Button button_Submit;

    @FXML
    private Button button_Submit1;
    @FXML
    private Button id_retour;

    @FXML
    private TextField date_heure;

    @FXML
    private TextField description;

    @FXML
    private ImageView image_input;

    @FXML
    private ComboBox<String> combov;

    @FXML
    private TextField nom;

    @FXML
    private TextField type;

    @FXML
    private TextField prix;
    @FXML
    private BorderPane rootPane;
    private static String loggedInUserEmail;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;
    @FXML
    private BorderPane borderPane;

    private final ServiceActivite serviceActivite = new ServiceActivite(); // Créer une instance finale de ServiceActivite

    @FXML
    void initialize()
    {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        populateComboBox();
    }

    @FXML
    void ajouterActivite(ActionEvent event) {
        try {
            if (validerChamps()) {
                String nomActivite = nom.getText();
                String typeActivite = type.getText();
                LocalDateTime dateHeureActivite = LocalDateTime.parse(date_heure.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); // Utilisation du bon format pour la date et l'heure
                double prixActivite = Double.parseDouble(prix.getText());
                String imageActivite = image_input.getImage().getUrl(); // Utilisation de l'URL de l'image dans ImageView
                String descriptionActivite = description.getText();
                String selectedNomstaff = combov.getSelectionModel().getSelectedItem();
                int idA_staff = getnomstaff(selectedNomstaff);// Récupération du nom du staff depuis ComboBox

                // Création de l'objet activite avec l'ID du staff obtenu
                activite nouvelleActivite = new activite(nomActivite, typeActivite, dateHeureActivite, prixActivite, imageActivite, descriptionActivite, idA_staff);
                serviceActivite.ajouter(nouvelleActivite); // Utilisation de l'instance de ServiceActivite pour appeler la méthode ajouter
            }
        } catch (SQLException | NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Erreur de saisie. Veuillez vérifier vos données.");
            alert.showAndWait();
        }
    }

    private boolean validerChamps() {
        if (nom.getText().isEmpty() || type.getText().isEmpty() || date_heure.getText().isEmpty() || prix.getText().isEmpty() || description.getText().isEmpty() || combov.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Tous les champs sont obligatoires.");
            alert.showAndWait();
            return false;
        }

        if (nom.getText().contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Le nom ne doit pas contenir d'espaces.");
            alert.showAndWait();
            return false;
        }

        try {
            LocalDateTime dateHeureActivite = LocalDateTime.parse(date_heure.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime now = LocalDateTime.now();
            if (dateHeureActivite.isBefore(now)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setContentText("La date et l'heure doivent être supérieures à la date et l'heure actuelles.");
                alert.showAndWait();
                return false;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("Format de date et heure incorrect. Utilisez le format YYYY-MM-DD HH:MM:SS.");
            alert.showAndWait();
            return false;
        }

        if (description.getText().length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setContentText("La description doit contenir au moins 10 caractères.");
            alert.showAndWait();
            return false;
        }

        return true;
    }
    @FXML
    void insertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image d'activite");
        // Filtre pour afficher uniquement les fichiers d'image
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers image (.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        // Afficher la boîte de dialogue de sélection de fichier
        File file = fileChooser.showOpenDialog(Inserer.getScene().getWindow());
        if (file != null) {
            // Charger l'image sélectionnée dans l'ImageView
            Image image1 = new Image(file.toURI().toString());
            image_input.setImage(image1); // Afficher l'image dans l'ImageView
        }
    }



    private void populateComboBox() {
        ObservableList<String> nomstaffList = FXCollections.observableArrayList();

        try {
            String url = "jdbc:mysql://localhost:3306/pi";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT nom FROM staff"; // Sélectionnez simplement le nom du staff

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomStaff = resultSet.getString("nom");
                nomstaffList.add(nomStaff);
            }
            combov.setItems(nomstaffList);
            resultSet.close();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    public int getnomstaff(String nom) throws SQLException {
        int idA = -1;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT idA FROM staff WHERE nom = ?")) {
            preparedStatement.setString(1, nom);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    idA = resultSet.getInt("idA");
                }
            }
        }
        return idA;
    }
    @FXML
    void naviguezversaffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AfficherActivite.fxml"));
            nom.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/Home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Affichez une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());
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

    }



