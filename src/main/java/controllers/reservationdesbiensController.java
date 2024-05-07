package controllers;

import entities.reservation_des_biens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.reservationdesbiensService;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class reservationdesbiensController implements Initializable {

    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private TextField prix;
    @FXML
    private TextField adresse;
    @FXML
    private TextField nombredemembre;
    @FXML
    private Text msj;
    @FXML
    private ListView<reservation_des_biens> mylist; // Changed Voiture to reservation_des_biens

    int id;
    private String selectedDatedebut;
    private String selectedDatefin;
    private String selectedPrix;
    private String selectedadresse;
    private String selectednombredemembre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getLoad(); // Load data after FXML components are initialized
    }

    @FXML
    void deleteaction(ActionEvent event) {
        reservationdesbiensService l = new reservationdesbiensService();
        l.supprimer(new reservation_des_biens(id));
        getLoad(); // Reload data after deletion
    }

    @FXML
    void ipdateaction(ActionEvent event) throws SQLException {
        if (validateFields()) {
            reservationdesbiensService ts = new reservationdesbiensService();
            ts.modifier(new reservation_des_biens(
                    id, Integer.parseInt(prix.getText()), adresse.getText(),
                    java.sql.Date.valueOf(date_debut.getValue()),
                    java.sql.Date.valueOf(date_fin.getValue()),
                    Integer.parseInt(nombredemembre.getText())));
            getLoad(); // Reload data after update
        } else {
            msj.setText("Please fill all required fields.");
        }
    }

    @FXML
    void save(ActionEvent event) throws SQLException {
        if (validateFields()) {
            System.err.println(id);
            reservationdesbiensService ts = new reservationdesbiensService();
            ts.ajouter(new reservation_des_biens(
                    Integer.parseInt(prix.getText()),
                    adresse.getText(),
                    java.sql.Date.valueOf(date_debut.getValue()),
                    java.sql.Date.valueOf(date_fin.getValue()),
                    Integer.parseInt(nombredemembre.getText())));
            getLoad(); // Reload data after save
        } else {
            msj.setText("Please fill all required fields.");
        }
    }

    public void getLoad() {
        reservationdesbiensService l = new reservationdesbiensService();
        mylist.getItems().clear(); // Clear existing items
        mylist.getItems().addAll(l.afficher());
        mylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                id = newValue.getId();
                selectedadresse = newValue.getAdresse();
                selectedDatedebut = String.valueOf(newValue.getDate_debut().toString());
                selectedPrix = String.valueOf(newValue.getPrix());
                selectednombredemembre = String.valueOf(newValue.getNombre_de_membre());
                selectedDatefin = newValue.getDate_fin().toString();
                // Set the values of the text fields
                adresse.setText(selectedadresse);
                date_fin.setValue(java.sql.Date.valueOf(selectedDatefin).toLocalDate());
                date_debut.setValue(LocalDate.parse(selectedDatedebut));

                prix.setText(selectedPrix);
                nombredemembre.setText(selectednombredemembre);
            }
        });
    }



    boolean validateFields() {
        return date_debut.getValue() != null && date_fin.getValue() != null && !prix.getText().isEmpty() && !adresse.getText().isEmpty() && !nombredemembre.getText().isEmpty();
    }
}
