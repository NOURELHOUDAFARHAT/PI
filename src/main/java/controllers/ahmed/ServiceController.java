package controllers.ahmed;

import entities.ahmed.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ahmed.ServiceSERV;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class ServiceController {
    @FXML
    public TableView<Service> serviceView;
    @FXML
    public TableColumn<Service, String> N;
    @FXML
    public TableColumn<Service, Integer> P;
    @FXML
    public TableColumn<Service, String> A;
    @FXML
    public TableColumn<Service, String> T;
    @FXML
    public TableColumn<Service, Date> R;

    @FXML
    public TextField ad;
    @FXML
    public TextField na;
    @FXML
    public TextField ph;
    @FXML
    private ChoiceBox<String> typeS;

    @FXML
    private DatePicker rdv;
    @FXML
    private TextField searchF;

    private ServiceSERV serviceSVR;
int idStaff;
    @FXML
    void initialize() {
        initializeTable();
        LoadTableView();
        typeS.getItems().addAll("Cleaning", "Gardening", "Plumb/Elec");
        serviceSVR = new ServiceSERV();


    }

    @FXML
    public void initializeTable() {
        N.setCellValueFactory(new PropertyValueFactory<>("name"));
        P.setCellValueFactory(new PropertyValueFactory<>("phone"));
        A.setCellValueFactory(new PropertyValueFactory<>("adress"));
        T.setCellValueFactory(new PropertyValueFactory<>("type"));
        R.setCellValueFactory(new PropertyValueFactory<>("rdv"));

        // Add listener to the TableView selection model
        serviceView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                na.setText(newSelection.getName());
                ad.setText(newSelection.getAdress());
                ph.setText(String.valueOf(newSelection.getPhone()));
                typeS.setValue(newSelection.getType());
                rdv.setValue(convertToLocalDate(newSelection.getRdv()));
            }
        });
    }

    private void LoadTableView() {
        ServiceSERV serviceSVR = new ServiceSERV();
        try {
            ObservableList<Service> svr = FXCollections.observableArrayList(serviceSVR.show());
            serviceView.setItems(svr);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to retrieve activity data from the database.");
            alert.showAndWait();
        }
    }

    @FXML
    void goToStaff(ActionEvent event){


        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/org/example/lastoflast/StaffView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
        }
    }

    @FXML
    void Back(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/Home.fxml")); // Adjust the path
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the full error for debugging
        }
    }
    private LocalDate convertToLocalDate(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @FXML
    void ApplyService() {
        // Get data from text fields and choice box
        String name = na.getText();
        String adress = ad.getText();
        int phone = Integer.parseInt(ph.getText());
        String type = typeS.getValue();
        LocalDate localDate = rdv.getValue();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date rendez = Date.from(instant);

        // Create a new Service object
        Service newService = new Service(0, name, adress, phone, type, rendez);

        // Call the create method in ServiceSERV
        try {
            serviceSVR.create(newService);
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Applied successfully");
            alert.showAndWait();

            LoadTableView();
            Clear();
        } catch (SQLException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to create service");
            alert.setContentText("An error occurred while creating service. Please try again later.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void Clear() {
        na.setText("");
        ad.setText("");
        ph.setText("");
        typeS.setValue(null);
        rdv.setValue(null);
    }

    @FXML
    void onSearchField(KeyEvent event) {
        String searchText = searchF.getText().trim();
        SearchService(searchText);
    }

    private void SearchService(String searchText) {
        ServiceSERV svr = new ServiceSERV();
        try {
            List<Service> services = svr.search(searchText);
            ObservableList<Service> observableList = FXCollections.observableList(services);
            serviceView.setItems(observableList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    void ModifyService(MouseEvent event) {
        if (!serviceView.getSelectionModel().isEmpty()) {
            Service selectedItem = serviceView.getSelectionModel().getSelectedItem();

            // Update the selected service with the values from the input fields
            selectedItem.setName(na.getText());
            selectedItem.setAdress(ad.getText());
            selectedItem.setPhone(Integer.parseInt(ph.getText()));
            selectedItem.setType(typeS.getValue());
            LocalDate localDate = rdv.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date rendez = Date.from(instant);
            selectedItem.setRdv(rendez);

            try {
                serviceSVR.modify(selectedItem);

                LoadTableView();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Service modifié avec succès");
                alert.showAndWait();
                Clear();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void LoadDataInform() {
        System.out.println("clicked");
    }

    @FXML
    public void goToService(MouseEvent event) throws IOException {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("ServiceApply.fxml"));
            Scene scene =new Scene(root);
            Stage stage =new Stage();
            stage.setScene(scene);
            stage.show();



        }catch(NullPointerException e) {
            System.out.println(e);
        }

    }
}
