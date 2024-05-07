package controllers.ahmed;


import entities.Sendmail;
import entities.ahmed.Service;
import entities.ahmed.Staff;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.ahmed.ServiceSERV;
import services.ahmed.StaffSERV;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class StaffController implements Initializable {

    @FXML
    public javafx.scene.control.TextField nC;
    @FXML
    public javafx.scene.control.TextField pC;
    @FXML
    public javafx.scene.control.TextField jC;
    @FXML
    public javafx.scene.control.TextField sC;
    @FXML
    public javafx.scene.control.Button createS;
    @FXML
    public javafx.scene.control.Button deleteS;
    @FXML
    public ChoiceBox<Boolean> statusS;
    @FXML
    public javafx.scene.control.Button modifyS;
    @FXML
    public TableView<Staff> StaffTableView;
    @FXML
    private ChoiceBox<Service>  service;

    @FXML
    private TableColumn<Staff, Integer> B;
    @FXML
    private TableColumn<Staff, String> N;
    @FXML
    private TableColumn<Staff, Integer> P;
    @FXML
    private TableColumn<Staff, String> J;
    @FXML
    private TableColumn<Staff, Integer> S;
    @FXML
    private TableColumn<Staff, Boolean> St;
    @FXML
    private TableColumn<Staff, Boolean> se;

    private StaffSERV staffService;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        staffService = new StaffSERV();
        loadStaffData();

        //Initialize choicebox;
        statusS.getItems().addAll(Boolean.TRUE, Boolean.FALSE );
        B.setCellValueFactory(new PropertyValueFactory<>("numStaff"));
        N.setCellValueFactory(new PropertyValueFactory<>("nameStaff"));
        P.setCellValueFactory(new PropertyValueFactory<>("phoneStaff"));
        J.setCellValueFactory(new PropertyValueFactory<>("jobStaff"));
        S.setCellValueFactory(new PropertyValueFactory<>("scoreStaff"));
        St.setCellValueFactory(new PropertyValueFactory<>("status"));
        se.setCellValueFactory(new PropertyValueFactory<>("service"));


    }
int idServ;
    private void loadStaffData() {
        try {
Service sr = new Service();
            List<Staff> staffs = staffService.show();
            ObservableList<Staff> observableList = FXCollections.observableArrayList(staffs);
            ServiceSERV cm = new ServiceSERV();
            // Clear the items before adding new ones*

            System.out.println(cm.show());
            List<Service> voitures = cm.show();
            System.out.println("Voitures: " + voitures); // Debug print
            service.getItems().addAll(voitures);

        StaffTableView.setItems(observableList);
            service.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    idServ = newValue.getId(); // Assuming id is the identifier for Voiture

                    System.out.println(idServ);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            // Handle error loading staff data
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to load staff data");
            alert.setContentText("An error occurred while loading staff data. Please try again later.");
            alert.showAndWait();
        }
    }

@FXML
public void goToService(MouseEvent event){

}
    @FXML
    public void CreateStaff() {
        // Get data from text fields and choice box
        String name = nC.getText();
        String job = jC.getText();
        int phone = Integer.parseInt(pC.getText());
        int score = Integer.parseInt(sC.getText());
        boolean status = Boolean.parseBoolean(String.valueOf(statusS.getValue()));

        // Create a new Staff object
        Staff newStaff = new Staff(0, name, phone, job,status,  score);
      //  initialize(null, null);

        // Call the create method in StaffSERV
        try {
            staffService.create(newStaff);

            // Attempt to send email notification
            Sendmail sn = new Sendmail();
            sn.envoyer("hmd18079@gmail.com","new Staff","Staff added");
            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Staff created successfully");
            alert.showAndWait();
            initialize(null, null);
            ClearData();
        } catch (SQLException e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to create staff");
            alert.setContentText("An error occurred while creating staff. Please try again later.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    void goToService(ActionEvent event){

        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/org/example/lastoflast/ServiceApply.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
        }
    }

    @FXML
    void DeleteStaff(MouseEvent event) {
        Staff selectedItem = StaffTableView.getSelectionModel().getSelectedItem();


        if (selectedItem != null) {

            try {
                staffService.delete(selectedItem.getNumStaff());
                StaffTableView.getItems().remove(selectedItem);


                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setTitle("Suppression réussie");
                alert2.setHeaderText(null);
                alert2.setContentText("Le bien a été supprimé avec succès.");
                alert2.showAndWait();
                initialize(null, null);

            } catch (SQLException e) {

                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Erreur lors de la suppression");
                alert1.setHeaderText(null);
                alert1.setContentText("Une erreur s'est produite lors de la suppression : " + e.getMessage());
                alert1.showAndWait();
            }
        } else {

            Alert alert3 = new Alert(Alert.AlertType.WARNING);
            alert3.setTitle("Aucun staff sélectionné");
            alert3.setHeaderText(null);
            alert3.setContentText("Veuillez sélectionner un staff.");
            alert3.showAndWait();
        }
    }

    @FXML
    void LoadDataInform(MouseEvent event){
        System.out.println("clicked");
        Staff selectedStaff = StaffTableView.getSelectionModel().getSelectedItem();

        // Populate the modifying form fields with the selected staff information
        nC.setText(String.valueOf(selectedStaff.getNameStaff()));
        pC.setText(String.valueOf(selectedStaff.getPhoneStaff()));
        jC.setText(selectedStaff.getJobStaff());
        sC.setText(String.valueOf(selectedStaff.getScoreStaff()));
        statusS.setValue(Boolean.valueOf(String.valueOf(selectedStaff.isStatus())));
    }

    @FXML
    void ModifyStaff(MouseEvent event) {
        if (!StaffTableView.getSelectionModel().isEmpty()) {
         /*   Staff selectedItem = StaffTableView.getSelectionModel().getSelectedItem();

            selectedItem.setNameStaff(nC.getText());
            selectedItem.setPhoneStaff(Integer.parseInt(pC.getText()));
            selectedItem.setJobStaff(jC.getText());
            selectedItem.setScoreStaff(Integer.parseInt(sC.getText()));
            selectedItem.setStatus(Boolean.parseBoolean(String.valueOf(statusS.getValue())));
*/
            Staff selectedStaff = StaffTableView.getSelectionModel().getSelectedItem();

// Update the selected staff with the values from the input fields
            selectedStaff.setNameStaff(nC.getText());
            selectedStaff.setPhoneStaff(Integer.parseInt(pC.getText()));
            selectedStaff.setJobStaff(jC.getText());
            selectedStaff.setScoreStaff(Integer.parseInt(sC.getText()));
            selectedStaff.setStatus(Boolean.parseBoolean(String.valueOf(statusS.getValue())));

  selectedStaff.setService(idServ);
            try {
                staffService.modify(selectedStaff);

                initialize(null, null);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Succès");
                alert.setContentText("Staff modifie avec succés");
                alert.showAndWait();
                ClearData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
 void ClearData(){
     nC.setText("");
     pC.setText("");
     jC.setText("");
     sC.setText("");
 }

    @FXML
    public void goToStaff(MouseEvent event) throws IOException {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("StaffView.fxml"));
            Scene scene =new Scene(root);
            Stage stage =new Stage();
            stage.setScene(scene);
            stage.show();



        }catch(NullPointerException e) {
            System.out.println(e);
        }

    }

    }
