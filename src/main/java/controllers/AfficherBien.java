package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import entities.Bien;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import services.ServiceBien;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;




public class AfficherBien {
    private String image;
    @FXML
    private Button Inserer;
    @FXML
    private TableColumn<Bien, String> TypeCol;

    @FXML
    private TableColumn<Bien, String> adressCol;

    @FXML
    private TableColumn<Bien, String> imageCol;

    @FXML
    private TableColumn<Bien, String> nameCol;

    @FXML
    private TableColumn<Bien, Integer> nbrChambreCol;
    @FXML
    private Button convertirTNDenEUR;
    @FXML
    private TextField montantEURField;
    private double tauxChangeTND_EUR = 0.30;

    @FXML
    private TextField montantTNDField;

    @FXML
    private TableColumn<Bien, Integer> prixCol;

    @FXML
    private TableColumn<Bien, Integer> refCol;

    @FXML
    private TextField adresseModif;


    @FXML
    private ImageView image_input;

    @FXML
    private Button modifierBien;

    @FXML
    private TextField nomModif;

    @FXML
    private TextField nombreChambreModif;



    @FXML
    private TextField prixModif;



    @FXML
    private Button supprimerBien;

    @FXML
    private TableView<Bien> tableView;

    @FXML
    private TextField typeModif;
    @FXML
    private TextField rechercheField;

    @FXML
    void initialize() {
        rechercherBiens("");

         ServiceBien bienService = new ServiceBien();
        try {
            List<Bien> biens = bienService.afficher();
            ObservableList<Bien> observableList = FXCollections.observableList(biens);
            tableView.setItems(observableList);

            refCol.setCellValueFactory(new PropertyValueFactory<>("refB"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            adressCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            prixCol.setCellValueFactory(new PropertyValueFactory<>("prix"));
            nbrChambreCol.setCellValueFactory(new PropertyValueFactory<>("nbrChambre"));
            imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void modifierBien(ActionEvent event) {
        Bien bien = tableView.getSelectionModel().getSelectedItem();
        if (bien != null) {

            String nom = nomModif.getText();
            String adresse = adresseModif.getText();
            int nbrChambre = Integer.parseInt(nombreChambreModif.getText());
            int prix = Integer.parseInt(prixModif.getText());
            String type = typeModif.getText();


            bien.setName(nom);
            bien.setAdresse(adresse);
            bien.setNbrChambre(nbrChambre);
            bien.setPrix(prix);
            bien.setType(type);
            if (image != null && !image.isEmpty()) {
                bien.setImage(image);
            }

            ServiceBien serviceBien = new ServiceBien();
            try {
                serviceBien.modifier(bien);


                initialize();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Bien modifié");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun bien sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un bien à modifier.");
            alert.showAndWait();
        }
    }
    @FXML
    void getData(MouseEvent event) {
        Bien bien = tableView.getSelectionModel().getSelectedItem();

        if (bien != null) {
            nomModif.setText(bien.getName());
            adresseModif.setText(bien.getAdresse());
            nombreChambreModif.setText(String.valueOf(bien.getNbrChambre()));
            prixModif.setText(String.valueOf(bien.getPrix()));
            typeModif.setText(bien.getType());
            image = bien.getImage();

            double prixTND = bien.getPrix();
            double prixEUR = convertirTNDenEUR(prixTND, tauxChangeTND_EUR);

            // Ensure that montantEURField is not null before setting text
            if (montantEURField != null) {
                montantEURField.setText(String.valueOf(prixEUR));
            } else {
                System.err.println("montantEURField is null");
            }

            if (bien.getImage() != null && !bien.getImage().isEmpty()) {
                File file = new File(bien.getImage());
                if (file.exists()) {
                    image_input.setImage(new Image("file:" + bien.getImage()));
                }
            }
        }
    }

    @FXML
    void insertImage (ActionEvent event){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de bien");


        File file = fileChooser.showOpenDialog(modifierBien.getScene().getWindow());
        if (file != null) {

            Image image1 = new Image(file.toURI().toString());
            image_input.setImage(image1);

            image = file.getAbsolutePath();
        }
    }
    @FXML
    void supprimerBien(ActionEvent event) {
        Bien bien = tableView.getSelectionModel().getSelectedItem();
        if (bien != null) {
            ServiceBien serviceBien = new ServiceBien();
            try {
                serviceBien.supprimer(bien);
                tableView.getItems().remove(bien);

                serviceBien.afficher();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le bien a été supprimé avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la suppression du bien : " + e.getMessage());
                alert.showAndWait();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucun bien sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un bien.");
            alert.showAndWait();
        }
    }
    @FXML
    void onRechercheFieldTextChanged(KeyEvent event) {
        String rechercheText = rechercheField.getText().trim();
        rechercherBiens(rechercheText);
    }
    private void rechercherBiens(String rechercheText) {
        ServiceBien bienService = new ServiceBien();
        try {
            List<Bien> biens = bienService.rechercher(rechercheText);

            ObservableList<Bien> observableList = FXCollections.observableList(biens);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    void naviguezVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ajoutBien.fxml"));
            nomModif.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void naviguezVersVisit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjoutVisite.fxml"));
            prixModif.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    private double convertirTNDenEUR(double montantTND, double tauxChange) {
        return montantTND / tauxChange;
    }
    @FXML
    void convertirMontant(ActionEvent event) {

            String montantTNDText = montantTNDField.getText();
            if (!montantTNDText.isEmpty()) {
                double montantTND = Double.parseDouble(montantTNDText);
                double montantEUR = convertirTNDenEUR(montantTND, tauxChangeTND_EUR);
                montantEURField.setText(String.valueOf(montantEUR));
            } else {
                // Gérer le cas où le champ montantTNDField est vide
            }

    }

}