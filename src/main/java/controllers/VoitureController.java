package controllers;

import entities.Sendmail;
import entities.Voiture;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import services.VoitureService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VoitureController implements Initializable {

    @FXML
    private TextField prix;

    @FXML
    private ColorPicker color;

    @FXML
    private TextField modele;

    @FXML
    private TextField matriculation;


    @FXML
    private TextField disponible;

    @FXML
    private ListView<Voiture> mylistcomm;

    private Voiture selectedVoiture;
    @FXML
    private Text msj;
    @FXML
    private TextField searchField;
    int id;

    private String selectedDisponible;
    private String selectedModel;
    private String selectedPrix;
    private String selectedColor;
    private String selectedMatriculation;

    @FXML
    void deleteaction(ActionEvent event) {
        VoitureService l = new VoitureService();
        l.supprimer(new Voiture(id));
    }
    boolean validateForm() {
        return !modele.getText().isEmpty() && !matriculation.getText().isEmpty() && !prix.getText().isEmpty();
    }
    @FXML
    void ipdateaction(ActionEvent event) {
        if (validateForm()) {
            VoitureService ts = new VoitureService();
            try {
                int selectedId = mylistcomm.getSelectionModel().getSelectedItem().getId(); // Get the selected voiture id
                System.out.println("Selected ID: " + selectedId);  // Debug: Print selected ID

                Voiture selectedVoiture = ts.getById(selectedId); // Retrieve the voiture object from the database using the selected ID
                if (selectedVoiture != null) {
                    System.out.println("Retrieved ID from DB: " + selectedVoiture.getId());  // Debug: Print retrieved ID

                    ts.modifier(new Voiture(selectedId, matriculation.getText(), modele.getText(), color.getValue().toString(), Integer.parseInt(disponible.getText()), Double.parseDouble(prix.getText())));
                    getLoad();
                } else {
                    System.out.println("No corresponding car found in the database.");  // Debug: Print message
                }
            } catch (NumberFormatException e) {
                msj.setText("Invalid number format!"); // Display error message for invalid input
            }
        } else {
            msj.setText("Please fill all required fields.");
        }
    }



    @FXML
    void save(ActionEvent event) {
        if (validateInputs()) {
            try {
                VoitureService l = new VoitureService();
                Voiture newVoiture = new Voiture(matriculation.getText(), modele.getText(), color.getValue().toString(), Integer.parseInt(disponible.getText()), Double.parseDouble(prix.getText()));
                l.ajouter(newVoiture);

                // Attempt to send email notification
                Sendmail mailer = new Sendmail();
                String toEmail = "Alaeddine.Bouabid@esprit.tn"; // The recipient's email address
                String subject = "New Car Added";
                String messageText = "A new car has been added to the system: " + newVoiture;
                mailer.envoyer(toEmail, subject, messageText);

                // Display success alert
                showAlert(AlertType.INFORMATION, "Success", "Voiture added successfully and email sent!");

            } catch (NumberFormatException e) {
                // Display error alert for invalid number format
                showAlert(AlertType.ERROR, "Error", "Invalid number format!");
            }
        }
    }

    @FXML
    void back(ActionEvent event) throws IOException {

        try {
            Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/org/example/lastoflast/Home.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
        }

    }
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private boolean validateInputs() {
        boolean isValid = true;

        if (modele.getText().isEmpty()) {
            msj.setText("Modèle field is required!");
            isValid = false;
        } else if (matriculation.getText().isEmpty()) {
            msj.setText("Matriculation field is required!");
            isValid = false;
        } else if (prix.getText().isEmpty()) {
            msj.setText("Prix field is required!");
            isValid = false;
        } else if (disponible.getText().isEmpty()) {
            msj.setText("Disponible field is required!");
            isValid = false;
        } else {
            try {
                int disp = Integer.parseInt(disponible.getText());
                if (disp < 0) {
                    msj.setText("Disponible value must be a positive number!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                msj.setText("Invalid Disponible format! Must be a number.");
                isValid = false;
            }

            try {
                double prx = Double.parseDouble(prix.getText());
                if (prx < 0) {
                    msj.setText("Prix value must be a positive number!");
                    isValid = false;
                }
            } catch (NumberFormatException e) {
                msj.setText("Invalid Prix format! Must be a number.");
                isValid = false;
            }
        }

        return isValid;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getLoad();
    }

    public void getLoad() {
        VoitureService l = new VoitureService();
        mylistcomm.getItems().clear(); // Clear existing items
        mylistcomm.getItems().addAll(l.afficher());
        mylistcomm.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                id = newValue.getId();
                selectedDisponible = String.valueOf(newValue.getDisponible());
                selectedModel = newValue.getModele();
                selectedPrix = String.valueOf(newValue.getPrix_per_day());
                selectedColor = newValue.getCouleur();
                selectedMatriculation = newValue.getNum_immatriculation();

                // Set the values of the text fields
                disponible.setText(selectedDisponible);
                modele.setText(selectedModel);
                prix.setText(selectedPrix);
                color.setValue(Color.web(selectedColor));
                matriculation.setText(selectedMatriculation);
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            List<Voiture> list = l.search(newValue);
            mylistcomm.getItems().setAll(list);
        });
    }

}
