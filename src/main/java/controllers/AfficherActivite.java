package controllers;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import entities.Staff;
import entities.activite;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import services.ServiceActivite;


public class AfficherActivite {
    @FXML
    private Button id_retour;

    @FXML
    private Button Inserer;

    @FXML
    private TextField date_heureModif;

    @FXML
    private TableColumn<activite, Timestamp> date_heurecol;

    @FXML
    private TextField descriptionModif;

    @FXML
    private TableColumn<activite, String> descriptioncol;

    @FXML
    private ComboBox<Staff> idAmodif;
    @FXML
    private Button showStatsButton;

    @FXML
    private ComboBox<String> priceIntervalComboBox;


    @FXML
    private TableColumn<activite, Integer> idAstaffcol;
    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<activite, Integer> idcol;

    @FXML
    private ImageView image_input;

    @FXML
    private TableColumn<activite, String> imagecol;

    @FXML
    private Button modifier;

    @FXML
    private TextField nomModif;

    @FXML
    private TableColumn<activite, String> nomcol;
    @FXML
    private Button btnGenererPDF;

    @FXML
    private TextField prixModif;

    @FXML
    private TableColumn<activite, Double> prixcol;

    @FXML
    private Button supprimer;

    @FXML
    private TableView<activite> tableView;

    @FXML
    private TextField typeModif;

    @FXML
    private TableColumn<activite, String> typecol;
    private boolean triAscendant = true;


    @FXML
    void initialize() {
        initializeTableView();
        chargerDonneesTableView();
    }

    private void initializeTableView() {
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomcol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typecol.setCellValueFactory(new PropertyValueFactory<>("type"));
        date_heurecol.setCellValueFactory(new PropertyValueFactory<>("date_heure"));
        prixcol.setCellValueFactory(new PropertyValueFactory<>("prix"));
        descriptioncol.setCellValueFactory(new PropertyValueFactory<>("description"));
        imagecol.setCellValueFactory(new PropertyValueFactory<>("image"));
        idAstaffcol.setCellValueFactory(new PropertyValueFactory<>("idA_staff"));
    }

    private void chargerDonneesTableView() {
        ServiceActivite serviceActivite = new ServiceActivite();
        try {
            ObservableList<activite> activiteList = FXCollections.observableArrayList(serviceActivite.recuperer());
            tableView.setItems(activiteList);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Database Error");
            alert.setContentText("Failed to retrieve activity data from the database.");
            alert.showAndWait();
        }
    }


    @FXML
    void getData(MouseEvent event) {
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof activite) {
            activite activiteSelectionnee = (activite) selectedItem;
            nomModif.setText(activiteSelectionnee.getNom());
            typeModif.setText(activiteSelectionnee.getType());
            date_heureModif.setText(activiteSelectionnee.getDate_heure().toString());
            prixModif.setText(String.valueOf(activiteSelectionnee.getPrix()));
            descriptionModif.setText(activiteSelectionnee.getDescription());


            Staff selectedStaff = idAmodif.getSelectionModel().getSelectedItem();
            if (selectedStaff != null) {
                // Assigner l'idA_staff de l'activité sélectionnée avec l'idA du Staff sélectionné
                activiteSelectionnee.setIdA_staff(selectedStaff.getIdA());
            }

            Image image = new Image(activiteSelectionnee.getImage());
            image_input.setImage(image);
        }
    }


    @FXML
    void insertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de maison");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers image (.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Inserer.getScene().getWindow());
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            image_input.setImage(image1);
        }
    }

    @FXML
    void modifierActivite(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()) {
            activite selectedItem = tableView.getSelectionModel().getSelectedItem();
            selectedItem.setNom(nomModif.getText());
            selectedItem.setType(typeModif.getText());
            selectedItem.setDate_heure(Timestamp.valueOf(date_heureModif.getText()));
            selectedItem.setPrix(Double.parseDouble(prixModif.getText()));
            selectedItem.setDescription(descriptionModif.getText());
            selectedItem.setImage(image_input.getImage().getUrl());

            Staff selectedStaff = idAmodif.getValue(); // Récupérer l'élément sélectionné dans le ComboBox
            if (selectedStaff != null) {
                selectedItem.setIdA_staff(selectedStaff.getIdA());
            }

            ServiceActivite serviceActivite = new ServiceActivite();
            try {
                serviceActivite.modifier(selectedItem);
                refreshTableView();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void refreshTableView() {
        tableView.getItems().clear();
        tableView.getItems().addAll(recupererActivites());
    }

    private List<activite> recupererActivites() {
        ServiceActivite serviceActivite = new ServiceActivite();
        try {
            return serviceActivite.recuperer();
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
    void supprimerActivite(ActionEvent event) {
        if (!tableView.getSelectionModel().isEmpty()) {
            activite selectedItem = tableView.getSelectionModel().getSelectedItem();
            ServiceActivite serviceActivite = new ServiceActivite();
            try {
                serviceActivite.supprimer(selectedItem.getId());
                tableView.getItems().remove(selectedItem);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Méthode pour trier ascendant/descendant
    @FXML
    void trierParPrix(ActionEvent event) {
        ObservableList<activite> activiteList = tableView.getItems();

        if (triAscendant) {
            activiteList.sort(Comparator.comparingDouble(activite::getPrix));
            triAscendant = false;
        } else {
            activiteList.sort(Comparator.comparingDouble(activite::getPrix).reversed());
            triAscendant = true;
        }
    }

    @FXML
    void search(ActionEvent event) {
        String searchTerm = searchField.getText().trim().toLowerCase();
        ObservableList<activite> filteredList = FXCollections.observableArrayList();

        if (!searchTerm.isEmpty()) {
            for (activite activite : tableView.getItems()) {
                if (activite.getNom().toLowerCase().contains(searchTerm)) {  // Modifier ici pour rechercher par nom
                    filteredList.add(activite);
                }
            }
            tableView.setItems(filteredList);
        } else {
            chargerDonneesTableView(); // Charger à nouveau toutes les données si la barre de recherche est vide
        }
    }

    public void genererPDF() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.newLineAtOffset(50, 700);

            ObservableList<activite> activiteList = tableView.getItems();

            // Mise en page tabulaire
            float margin = 50;
            float yStart = 700;
            float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20;
            float cellMargin = 5;

            // Headers
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.setLeading(15);
            contentStream.showText("Nom");
            contentStream.newLine();
            contentStream.showText("Type");
            contentStream.newLine();
            contentStream.newLine();
            contentStream.showText("Date_heure");
            contentStream.newLine();
            contentStream.newLine();

            // Contenu de la table
            contentStream.setFont(PDType1Font.HELVETICA, 12);
            for (activite activite : activiteList) {
                if (yPosition - rowHeight < margin) {
                    // Nouvelle page si la ligne actuelle dépasse la marge
                    contentStream.endText();
                    contentStream.close();
                    PDPage newPage = new PDPage();
                    document.addPage(newPage);
                    contentStream = new PDPageContentStream(document, newPage);
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    yPosition = yStart;
                }
                contentStream.showText(activite.getNom());
                contentStream.newLine();
                contentStream.showText(activite.getType());
                contentStream.newLine();
                contentStream.newLine();

                yPosition -= rowHeight;
            }

            contentStream.endText();
            contentStream.close();

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(Inserer.getScene().getWindow());

            if (file != null) {
                document.save(file);
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showPriceStats(ActionEvent event) {
        String interval = priceIntervalComboBox.getValue();
        if (interval != null && !interval.isEmpty()) {
            try {
                double minPrice = Double.parseDouble(interval.split("-")[0].trim());
                double maxPrice = Double.parseDouble(interval.split("-")[1].trim());

                ServiceActivite serviceActivite = new ServiceActivite();
                ObservableList<activite> activiteList = FXCollections.observableArrayList(serviceActivite.recupererParIntervalPrix(minPrice, maxPrice));
                tableView.setItems(activiteList);
            } catch (NumberFormatException | SQLException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de récupération des données", "Une erreur s'est produite lors de la récupération des activités par intervalle de prix.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Attention", "Interval de prix non sélectionné", "Veuillez sélectionner un intervalle de prix pour afficher les statistiques.");
        }
    }

// Autres méthodes...

    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);


    }
    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AjoutActivite.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Affichez une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());
        }

    }
}













