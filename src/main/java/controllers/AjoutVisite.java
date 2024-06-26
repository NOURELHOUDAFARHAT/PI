package controllers;

import entities.EmailSender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import entities.Visite;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.CustomSession;

public class AjoutVisite {
    private static String loggedInUserEmail;

    @FXML
    private BorderPane borderPane;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;

    @FXML
    private Button Bt_visites;

    @FXML
    private Button button_Submit;

    @FXML
    private ComboBox<String> comboV;

    @FXML
    private DatePicker dateV;

    @FXML
    private TextField emailV;

    @FXML
    private TextField nomV;

    @FXML
    private TextField numV;
    @FXML
    private Label errorLabelNom;

    @FXML
    private Label errorLabelEmail;

    @FXML
    private Label errorLabelNum;


    public ComboBox<String> getComboV() {
        return comboV;
    }

    private void populateComboBox() {
        ObservableList<String> nomMaisonList = FXCollections.observableArrayList();

        try {
            String url = "jdbc:mysql://localhost:3306/pi";
            String username = "root";
            String password = "";
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT ref_b, name FROM bien";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nomMaison = resultSet.getString("name");
                nomMaisonList.add(nomMaison);
            }
            comboV.setItems(nomMaisonList);
            resultSet.close();

        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    @FXML
    void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        populateComboBox();

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

    private List<LocalDate> verifierDisponibilite(int refB) throws SQLException {
        List<LocalDate> datesVisite = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT date_visite FROM visite WHERE ref_b = ?")) {
            preparedStatement.setInt(1, refB);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate dateVisite = resultSet.getDate("date_visite").toLocalDate();
                    datesVisite.add(dateVisite);
                }
            }
        }
        return datesVisite;
    }
    @FXML
    public void ajouterVisit(ActionEvent event) throws SQLException {
        LocalDate dateVisit = dateV.getValue();
        TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
            if (change.getText().matches("\\d{0,8}")) {
                return change;
            } else {
                return null;
            }
        });

        numV.setTextFormatter(textFormatter);
        try {
            if (nomV.getText().isEmpty() || emailV.getText().isEmpty() || dateVisit == null) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez remplir tous les champs.");
            } else if (!isValidEmail(emailV.getText())) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez entrer une adresse email valide.");
            } else if (dateVisit.isBefore(LocalDate.now())) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez sélectionner une date future.");
            } else if (!isValidName(nomV.getText())) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez entrer un nom valide (lettres uniquement).");
            } else if (!isValidPhoneNumber(numV.getText())) {
                showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez entrer un numéro de téléphone valide (8 chiffres).");
            } else {
                int numero = parseInteger(numV.getText());
                String selectedNomMaison = comboV.getSelectionModel().getSelectedItem();
                int refB = getNomMaison(selectedNomMaison);

                List<LocalDate> datesVisite = verifierDisponibilite(refB);

                long nombreVisites = datesVisite.stream()
                        .filter(date -> date.equals(dateVisit))
                        .count();

                if (nombreVisites >= 4) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", null, "Cette date a déjà 4 visites planifiées.");
                } else {
                    dateV.setDayCellFactory(picker -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate date, boolean empty) {
                            super.updateItem(date, empty);

                            if (date.isBefore(LocalDate.now())) {
                                setDisable(true);
                                setStyle("-fx-background-color: #FF0000;"); // Rouge pour les dates passées
                            }

                            long nombreVisites = datesVisite.stream()
                                    .filter(d -> d.equals(date))
                                    .count();

                            if (nombreVisites >= 4) {
                                setDisable(true);
                                setStyle("-fx-background-color: #FF0000;"); // Rouge pour les dates avec 4 visites planifiées
                            }
                        }
                    });

                    Visite visit = new Visite();
                    visit.setRef_B(refB);
                    visit.setNumero(numero);
                    visit.setDate_visite(dateVisit);
                    visit.setEmail(emailV.getText());
                    visit.setName(nomV.getText());

                    String req = "INSERT INTO visite (ref_b, numero, date_visite, email, name) VALUES (?, ?, ?, ?, ?)";
                    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
                         PreparedStatement preparedStatement = connection.prepareStatement(req)) {
                        preparedStatement.setInt(1, visit.getRef_B());
                        preparedStatement.setInt(2, visit.getNumero());
                        preparedStatement.setDate(3, java.sql.Date.valueOf(visit.getDate_visite()));
                        preparedStatement.setString(4, visit.getEmail());
                        preparedStatement.setString(5, visit.getName());
                        int rowsAffected = preparedStatement.executeUpdate();
                        if (rowsAffected > 0) {
                            EmailSender.sendWelcomeEmailWithSignature(visit.getEmail(), visit.getName());
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", null, "Veuillez entrer des valeurs numériques valides.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private int parseInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\d{8}$");
    }

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private boolean isValidName(String name) {
        String nameRegex = "^[A-Za-z]+$";
        return Pattern.matches(nameRegex, name);
    }

    @FXML
    void naviguerVersAffichage(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AfficherVisite.fxml"));
            nomV.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private int getNomMaison(String nom) throws SQLException {
        int ref_b = -1;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT ref_b FROM bien WHERE name = ?")) {
            preparedStatement.setString(1, nom);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    ref_b = resultSet.getInt("ref_b");
                }
            }
        }
        return ref_b;
    }

    private ObservableList<LocalDate> verifierDisponibilite(LocalDate dateVisite, int ref_b) {
        ObservableList<LocalDate> datesIndisponibles = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pi", "root", "");
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT date_visite FROM visite WHERE date_visite = ? AND ref_b = ?")) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(dateVisite));
            preparedStatement.setInt(2, ref_b);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                int nombreVisites = 0;
                while (resultSet.next()) {
                    nombreVisites++;
                }
                if (nombreVisites >= 5) {
                    datesIndisponibles.add(dateVisite);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
        return datesIndisponibles;
    }
}