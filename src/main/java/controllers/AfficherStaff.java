package controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import entities.Staff;
import services.ServiceStaff;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherStaff {

    @FXML
    private Button bt_Staff;

    @FXML
    private Button bt_activite;

    @FXML
    private TableView<Staff> tableView;

    @FXML
    private Button modifier;

    @FXML
    private TableColumn<Staff, Integer> idCol1;

    @FXML
    private TableColumn<Staff, String> nomCol1;

    @FXML
    private TableColumn<Staff, String> typeCol1;

    @FXML
    private TableColumn<Staff, String> num_telCol1;

    @FXML
    private TextField nomModif1;

    @FXML
    private TextField typeModif1;

    @FXML
    private TextField num_telModif1;

    private final ServiceStaff serviceStaff = new ServiceStaff();



    @FXML
    void initialize() {
        initializeTableView();
        chargerDonneesTableView();
    }

    private void initializeTableView() {
        nomCol1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeCol1.setCellValueFactory(new PropertyValueFactory<>("type"));
        num_telCol1.setCellValueFactory(new PropertyValueFactory<>("num_tel"));
    }

    private void chargerDonneesTableView() {
        try {
            ObservableList<Staff> staffList = FXCollections.observableArrayList(serviceStaff.recuperer());
            tableView.setItems(staffList); // Set the correct ObservableList to the TableView
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to retrieve staff data from the database.");
            alert.showAndWait();
        }
    }



    @FXML
    void getData(MouseEvent event) {
        Staff selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            nomModif1.setText(selectedItem.getNom());
            typeModif1.setText(selectedItem.getType());
            num_telModif1.setText(selectedItem.getNum_tel());
        }
    }

    @FXML
    void modifierstaff(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()){
        Staff selectedItem = tableView.getSelectionModel().getSelectedItem();

            selectedItem.setNom(nomModif1.getText());
            selectedItem.setType(typeModif1.getText());
            selectedItem.setNum_tel(num_telModif1.getText());

            try {
                serviceStaff.modifier(selectedItem);
                refreshTableView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshTableView() {
        tableView.getItems().clear();
        tableView.getItems().addAll(recupererStaffs());
    }

    private List<Staff> recupererStaffs() {
        try {
            return serviceStaff.recuperer();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @FXML
    void naviguezVersAjout(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AjoutActivite.fxml"));
            tableView.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void staff(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AjoutStaff.fxml"));
            tableView.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void supprimerstaff(ActionEvent event) {
        Staff selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                serviceStaff.supprimer(selectedItem.getIdA());
                tableView.getItems().remove(selectedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
