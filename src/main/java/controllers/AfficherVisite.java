package controllers;

import java.io.IOException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import entities.Visite;
import javafx.scene.input.MouseEvent;
import services.ServiceVisite;

import java.sql.SQLException;
import java.util.List;

public class AfficherVisite {

    @FXML
    private Button Bt_Biens;

    @FXML
    private Button Bt_visites;

    @FXML
    private TableColumn<Visite, Integer> idCol;

    @FXML
    private TableColumn<Visite, String> nameCol;

    @FXML
    private TableColumn<Visite, String> emailCol;

    @FXML
    private TableColumn<Visite, String> dateCol;

    @FXML
    private TableColumn<Visite, Integer> numCol;

    @FXML
    private TableColumn<Visite, String> refCol;

    @FXML
    private TableView<Visite> tableView;

    @FXML
    private TextField rechercheField;

    @FXML
    private Button supprimerVisite;

    @FXML
    private TextField nomModif;

    @FXML
    private TextField emailModif;

    @FXML
    private DatePicker dateModif;

    @FXML
    private TextField numModif;

    @FXML
    private TextField refModif;

    @FXML
    void initialize() {
        rechercherVisites("");
        // ...
        ServiceVisite visiteService = new ServiceVisite();
        try {
            List<Visite> visites = visiteService.afficher();
            ObservableList<Visite> observableList = FXCollections.observableList(visites);
            tableView.setItems(observableList);

            idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            refCol.setCellValueFactory(new PropertyValueFactory<>("ref_B"));
            numCol.setCellValueFactory(new PropertyValueFactory<>("numero"));
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date_visite"));
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void modifierVisite(ActionEvent event) {
        Visite visite = tableView.getSelectionModel().getSelectedItem();
        if (visite != null) {

            String ref_bText = refModif.getText().toString();
            String numeroText = numModif.getText();
            LocalDate dateVisite = dateModif.getValue();
            String email = emailModif.getText();
            String name = nomModif.getText();

            int ref_b = 0;
            int numero = 0;

            if (!ref_bText.isEmpty() && !ref_bText.equals("[]")) {
                ref_b = Integer.parseInt(ref_bText.replaceAll("\\D+", ""));
            }
            if (!numeroText.isEmpty() && !numeroText.equals("[]")) {
                numero = Integer.parseInt(numeroText);
            }

            visite.setRef_B(ref_b);
            visite.setNumero(numero);
            visite.setDate_visite(dateVisite);
            visite.setEmail(email);
            visite.setName(name);

            ServiceVisite serviceVisite = new ServiceVisite();
            try {
                serviceVisite.modifier(visite);

                initialize();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Visite modifiée");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune visite sélectionnée");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une visite à modifier.");
            alert.showAndWait();
        }
    }

    @FXML
    void getData(MouseEvent event) {
        Visite visite = tableView.getSelectionModel().getSelectedItem();

        refModif.setText(String.valueOf(visite.getRef_B()));
        numModif.setText(String.valueOf(visite.getNumero()));
        dateModif.setValue(visite.getDate_visite());
        emailModif.setText(visite.getEmail());
        nomModif.setText(visite.getName());

    }

    @FXML
    void supprimerVisite(ActionEvent event) {
        Visite visite = tableView.getSelectionModel().getSelectedItem();
        if (visite != null) {
            ServiceVisite serviceVisite = new ServiceVisite();
            try {
                serviceVisite.supprimer(visite);
                tableView.getItems().remove(visite);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Suppression réussie");
                alert.setHeaderText(null);
                alert.setContentText("La visite a été supprimée avec succès.");
                alert.showAndWait();
            } catch (SQLException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur lors de la suppression");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la suppression de la visite : " + e.getMessage());
                alert.showAndWait();
            }
        } else {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune visite sélectionné");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un visite a supprimer");
            alert.showAndWait();
        }
    }

    @FXML
    void onRechercheFieldTextChanged(KeyEvent event) {
        String recherche = rechercheField.getText();
        rechercherVisites(recherche);
    }

    private void rechercherVisites(String recherche) {
        ServiceVisite serviceVisite = new ServiceVisite();
        try {
            List<Visite> visites = serviceVisite.rechercher(recherche);
            ObservableList<Visite> observableList = FXCollections.observableList(visites);
            tableView.setItems(observableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void naviguerVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjoutVisite.fxml"));
            nomModif.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    @FXML
    void naviguerVersBien(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjoutBien.fxml"));
            refModif.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    }
