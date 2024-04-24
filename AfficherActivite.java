package tn.esprit.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import tn.esprit.entites.Staff;
import tn.esprit.entites.activite;
import tn.esprit.services.ServiceActivite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AfficherActivite {

    @FXML
    private Button Inserer;

    @FXML
    private TextField date_heureModif;

    @FXML
    private TableColumn<activite, Timestamp> date_heurecol;

    @FXML
    private TextField descriptionModif;

    @FXML
    private TableColumn<activite,String> descriptioncol;

    @FXML
    private ComboBox<Staff> idAmodif;

    @FXML
    private TableColumn<activite, Integer> idAstaffcol;

    @FXML
    private TableColumn<activite, Integer> idcol;

    @FXML
    private ImageView image_input;

    @FXML
    private TableColumn<activite, String> imagecol;

    @FXML
    private Button modifier;

    @FXML
    private TextField nomModif;

    @FXML
    private TableColumn<activite, String> nomcol;

    @FXML
    private TextField prixModif;

    @FXML
    private TableColumn<activite, Double> prixcol;

    @FXML
    private Button supprimer;

    @FXML
    private TableView<activite> tableView;

    @FXML
    private TextField typeModif;

    @FXML
    private TableColumn<activite, String> typecol;


    @FXML
    void initialize() {
        initializeTableView();
        chargerDonneesTableView();
    }

    private void initializeTableView() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        date_heurecol.setCellValueFactory(new PropertyValueFactory<>("date_heure"));
        prixcol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        imagecol.setCellValueFactory(new PropertyValueFactory<>("image"));
        idAstaffcol.setCellValueFactory(new PropertyValueFactory<>("idA_staff"));
    }

    private void chargerDonneesTableView() {
        ServiceActivite serviceActivite = new ServiceActivite();
        try {
            ObservableList<activite> activiteList = FXCollections.observableArrayList(serviceActivite.recuperer());
            tableView.setItems(activiteList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to retrieve activity data from the database.");
            alert.showAndWait();
        }
    }



    @FXML
    void getData(MouseEvent event) {
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof activite) {
            activite activiteSelectionnee = (activite) selectedItem;
            nomModif.setText(activiteSelectionnee.getNom());
            typeModif.setText(activiteSelectionnee.getType());
            date_heureModif.setText(activiteSelectionnee.getDate_heure().toString());
            prixModif.setText(String.valueOf(activiteSelectionnee.getPrix()));
            descriptionModif.setText(activiteSelectionnee.getDescription());


            Staff selectedStaff = idAmodif.getSelectionModel().getSelectedItem();
            if (selectedStaff != null) {
                // Assigner l'idA_staff de l'activité sélectionnée avec l'idA du Staff sélectionné
                activiteSelectionnee.setIdA_staff(selectedStaff.getIdA());
            }

            Image image = new Image(activiteSelectionnee.getImage());
            image_input.setImage(image);
        }
    }


    @FXML
    void insertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de maison");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers image (.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Inserer.getScene().getWindow());
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            image_input.setImage(image1);
        }
    }

    @FXML
    void modifierActivite(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()) {
            activite selectedItem = tableView.getSelectionModel().getSelectedItem();
            selectedItem.setNom(nomModif.getText());
            selectedItem.setType(typeModif.getText());
            selectedItem.setDate_heure(Timestamp.valueOf(date_heureModif.getText()));
            selectedItem.setPrix(Double.parseDouble(prixModif.getText()));
            selectedItem.setDescription(descriptionModif.getText());
            selectedItem.setImage(image_input.getImage().getUrl());

            Staff selectedStaff = idAmodif.getValue(); // Récupérer l'élément sélectionné dans le ComboBox
            if (selectedStaff != null) {
                selectedItem.setIdA_staff(selectedStaff.getIdA());
            }

            ServiceActivite serviceActivite = new ServiceActivite();
            try {
                serviceActivite.modifier(selectedItem);
                refreshTableView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void refreshTableView() {
        tableView.getItems().clear();
        tableView.getItems().addAll(recupererActivites());
    }

    private List<activite> recupererActivites() {
        ServiceActivite serviceActivite = new ServiceActivite();
        try {
            return serviceActivite.recuperer();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @FXML
    void naviguezVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjoutActivite.fxml"));
            tableView.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void staff(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Ajoutstaff.fxml"));
            tableView.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerActivite(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()) {
            activite selectedItem = tableView.getSelectionModel().getSelectedItem();
            ServiceActivite serviceActivite = new ServiceActivite();
            try {
                serviceActivite.supprimer(selectedItem.getId());
                tableView.getItems().remove(selectedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}





