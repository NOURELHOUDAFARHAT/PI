package controllers;

import com.itextpdf.text.Font;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import entities.ReservationVoitureDto;
import entities.Voiture;
import entities.reservation_des_voitures;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.lastoflast.HelloApplication;
import services.ReservationService;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ReservationController implements Initializable {
    @FXML
    private DatePicker date_debut;
    @FXML
    private DatePicker date_fin;
    @FXML
    private TextField prix;
    @FXML
    private Text msj;
    @FXML
    private ChoiceBox<Voiture> idvoiture;
    @FXML
    private ListView<ReservationVoitureDto> mylist;
    private String selectedDatedebut;
    private String selectedDatefin;
    private double selectedprix;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getLoad();
            populateChoiceBox();

            // Listener for the ChoiceBox selection
            idvoiture.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    idv = newValue.getId(); // Assuming id is the identifier for Voiture
                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
@FXML
void resbien(ActionEvent event){
    try {
        Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("/org/example/lastoflast/reservationdesbiens.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException ex) {
        ex.printStackTrace(); // Afficher l'erreur complète pour le débogage
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
    @FXML
    void makePdf(ActionEvent event) {
        ReservationVoitureDto selectedReservation = mylist.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            Document document = new Document();
            try {
                String filePath = "ReservationDetails.pdf";
                PdfWriter.getInstance(document, new FileOutputStream(filePath));
                document.open();

                Font titleFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD, BaseColor.BLUE);
                Paragraph title = new Paragraph("Reservation Details", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("\n")); // Adding a line break

                Font textFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);

                // Adding reservation details
                document.add(new Paragraph("Reservation ID: " + selectedReservation.getId(), textFont));
                document.add(new Paragraph("Start Date: " + selectedReservation.getDate_debut(), textFont));
                document.add(new Paragraph("End Date: " + selectedReservation.getDate_fin(), textFont));
                document.add(new Paragraph("Price: " + selectedReservation.getPrix(), textFont));
                document.add(new Paragraph("Vehicle ID: " + selectedReservation.getVoiture_id(), textFont));

                document.close();

                // Open the PDF document
                File file = new File(filePath);
                Desktop.getDesktop().open(file);

                msj.setText("PDF generated successfully!");
            } catch (DocumentException | IOException e) {
                msj.setText("Error generating PDF: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            msj.setText("No reservation selected.");
        }
    }

    void populateChoiceBox() throws SQLException {
        ReservationService cm = new ReservationService();
        idvoiture.getItems().clear(); // Clear the items before adding new ones
        List<Voiture> voitures = cm.affichage().stream().map(dto -> new Voiture(dto.getVoiture_id())).collect(Collectors.toList());
        System.out.println("Voitures: " + voitures); // Debug print
        idvoiture.getItems().addAll(voitures);
    }
    void getLoad() throws SQLException {
        ReservationService cm = new ReservationService();
        mylist.getItems().clear(); // Clear the list before adding new items
        mylist.getItems().addAll(cm.affichage());
        mylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                idr = newValue.getId(); // Assuming id is the identifier for Commande

                selectedDatefin = newValue.getDate_fin().toString();
                selectedprix = newValue.getPrix(); // Assuming mntntot is of type Double
                selectedDatedebut = newValue.getDate_debut().toString(); // Assuming datecommande is of type LocalDate

                // Set the values of the text fields

                prix.setText(String.valueOf(selectedprix));
                date_fin.setValue(Date.valueOf(selectedDatefin).toLocalDate());
                date_debut.setValue(Date.valueOf(selectedDatedebut).toLocalDate());
            }
        });
    }
 int idr;
    int idv;
    @FXML
    void deleteaction(ActionEvent event) throws SQLException {
        ReservationService ts = new ReservationService();
        ts.supprimer(new reservation_des_voitures(idr));
        getLoad();
    }

    @FXML
    void ipdateaction(ActionEvent event) throws SQLException {
        if (validateFields()) {
            ReservationService ts = new ReservationService();
            ts.modifier(new reservation_des_voitures(
                    idr,  Date.valueOf(date_debut.getValue()), Date.valueOf(date_fin.getValue()), Double.parseDouble(prix.getText()), idv
            ));
            getLoad();
        } else {
            msj.setText("Please fill all required fields.");
        }
    }
    @FXML
    void save(ActionEvent event) throws SQLException {
        if (validateFields()) {
            System.err.println(idv);
            ReservationService ts = new ReservationService();
            ts.ajouter(new reservation_des_voitures( Date.valueOf(date_debut.getValue()), Date.valueOf(date_fin.getValue()), Double.parseDouble(prix.getText()),idv));
            getLoad();
        } else {
            msj.setText("Please fill all required fields.");
        }
    }

    // Method to validate form fields
    boolean validateFields() {
        return date_debut.getValue() != null && date_fin.getValue() != null && !prix.getText().isEmpty();
    }


}
