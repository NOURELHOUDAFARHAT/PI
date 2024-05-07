package controllers;


import entities.reservationsdesactiviter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import services.ReservationActiviterService;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationActiviterController implements Initializable {
    @FXML
    private DatePicker date;
    @FXML
    private TextField prix;
    @FXML
    private TextField type;
    @FXML
    private TextField nom;
    @FXML
    private Text msj;
    @FXML
    private ListView<reservationsdesactiviter> mylist;
    private String selectedDate;
    private String selectednom;
    private String selectedtype;
    private int selectedprix;
    @FXML
    private ComboBox<String> sortOptions;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getLoad();
        setupSortOptions();
    }
    private void setupSortOptions() {
        sortOptions.setOnAction(event -> {
            String selectedOption = sortOptions.getValue();
            if ("Sort by Name".equals(selectedOption)) {
                sortByName();
            } else if ("Sort by Type".equals(selectedOption)) {
                sortByType();
            }
        });
    }
    private void sortByName() {
        ReservationActiviterService service = new ReservationActiviterService();
        List<reservationsdesactiviter> sortedList = service.triNom();
        mylist.getItems().setAll(sortedList);
        msj.setText("Sorted by Name");
    }

    private void sortByType() {
        ReservationActiviterService service = new ReservationActiviterService();
        List<reservationsdesactiviter> sortedList = service.triType();
        mylist.getItems().setAll(sortedList);
        msj.setText("Sorted by Type");
    }
    int id;
    @FXML
    void deleteaction(ActionEvent event) {
        ReservationActiviterService l = new ReservationActiviterService();
        l.supprimer(new reservationsdesactiviter(id));
        getLoad(); // Reload data after deletion
    }
    @FXML
    void ipdateaction(ActionEvent event) throws SQLException {
        if (validateFields()) {
            ReservationActiviterService ts = new ReservationActiviterService();
            ts.modifier(new reservationsdesactiviter(
                    id,
                    nom.getText(),
                    type.getText(),
                    Integer.parseInt(prix.getText()),
                    java.sql.Date.valueOf(date.getValue().toString())
            )); // Update data

            getLoad(); // Reload data after update
        } else {
            msj.setText("Please fill all required fields.");
        }
    }
    @FXML
    void save(ActionEvent event) throws SQLException {
        if (validateFields()) {
            System.err.println(id);
            ReservationActiviterService ts = new ReservationActiviterService();
            ts.addReservationActiviter(new reservationsdesactiviter(
                    nom.getText(),
                    type.getText(),
                    Integer.parseInt(prix.getText()),
                    java.sql.Date.valueOf(date.getValue().toString())));
            getLoad(); // Reload data after save
        } else {
            msj.setText("Please fill all required fields.");
        }
    }
    public void getLoad() {
        ReservationActiviterService l = new ReservationActiviterService();
        mylist.getItems().clear(); // Clear existing items
        mylist.getItems().addAll(l.afficher());
        mylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                id = newValue.getId();
                selectednom = newValue.getNom();
                selectedDate = String.valueOf(newValue.getDate().toString());
                selectedprix = Integer.parseInt(String.valueOf(newValue.getPrix()));
                selectedtype = String.valueOf(newValue.getType());

                // Set the values of the text fields
                 nom.setText(selectednom);
                date.setValue(java.sql.Date.valueOf(selectedDate).toLocalDate());
                prix.setText(String.valueOf(selectedprix));
                type.setText(selectedtype);

            }
        });
    }

    @FXML
    void sortByName(ActionEvent event) {
        ReservationActiviterService l = new ReservationActiviterService();
        List<reservationsdesactiviter> sortedList = l.triNom();
        mylist.getItems().setAll((Collection<? extends reservationsdesactiviter>) sortedList); // Update the ListView with the sorted list
        msj.setText("Sorted by Name");
    }

    @FXML
    void sortByType(ActionEvent event) {
        ReservationActiviterService l = new ReservationActiviterService();
        List<reservationsdesactiviter> sortedList = l.triType();
        mylist.getItems().setAll((Collection<? extends reservationsdesactiviter>) sortedList); // Update the ListView with the sorted list
        msj.setText("Sorted by Type");
    }


    boolean validateFields() {
        return date.getValue() != null  && !prix.getText().isEmpty() && !type.getText().isEmpty() ;
    }
}
