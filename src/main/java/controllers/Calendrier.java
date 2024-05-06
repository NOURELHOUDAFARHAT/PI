package controllers;

import entities.Visite;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.CustomSession;
import services.ServiceVisite;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import jfxtras.scene.control.agenda.Agenda;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Collection;
import java.util.Locale;

public class Calendrier {
    @FXML
    private BorderPane borderPane;
    @FXML
    private BorderPane rootPane;
    private static String loggedInUserEmail;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;

    @FXML
    private VBox calendrierBox;
    private ServiceVisite serviceVisite;

    public Calendrier() {
        serviceVisite = new ServiceVisite();
    }

    public void initialize() {

        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        // Create a new instance of the agenda
        Agenda agenda = new Agenda();

        // Apply CSS styles to the agenda
        agenda.getStyleClass().addAll("Agenda", "style1");

        // Create a label for the current month
        LocalDate currentDate = LocalDate.now();
        Label monthLabel = new Label(currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + currentDate.getYear());
        monthLabel.getStyleClass().add("month-label");

        // Set styles for the month label
        monthLabel.setStyle("-fx-font-family: DM Sans, sans-serif; -fx-font-size: 16px;  -fx-text-fill: #d92172;");

        // Add the month label and the agenda to the VBox
        calendrierBox.getChildren().addAll(monthLabel, agenda);
        calendrierBox.setStyle("-fx-background-color: #1e709a;"); // Set background color

        try {
            Collection<Visite> visites = serviceVisite.getAllVisites(); // Get visites using your existing method
            addVisitesToAgenda(visites, agenda);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addVisitesToAgenda(Collection<Visite> visites, Agenda agenda) {
        for (Visite visite : visites) {
            LocalDate visiteDate = visite.getDate_visite();
            LocalDateTime start = visiteDate.atTime(9, 0); // Set start time at 9 o'clock
            LocalDateTime end = visiteDate.atTime(13, 0); // Set end time at 13 o'clock


            agenda.appointments().add(new Agenda.AppointmentImplLocal()
                    .withStartLocalDateTime(start)
                    .withEndLocalDateTime(end)
                    .withSummary(visite.getName()));
        }
    }
    @FXML
    void Retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AfficherVisite.fxml"));
            calendrierBox.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void redirectToLoginPage() {
        try {
            // Chargement de la page de connexion
            Parent loginPage = FXMLLoader.load(getClass().getResource("Seconnecter.fxml"));
            Scene scene = new Scene(loginPage);
            Stage stage = (Stage) borderPane.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
