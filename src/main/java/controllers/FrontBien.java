package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import entities.Bien;
import javafx.stage.Stage;
import services.ServiceBien;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontBien implements Initializable {

    @FXML
    private TilePane biensContainer;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField minPriceField;

    @FXML
    private TextField maxPriceField;

    @FXML
    private AjoutVisite ajoutVisiteController; // Injectez AjoutVisite ici

    private ServiceBien serviceBien;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serviceBien = new ServiceBien();
        afficherTousBiens();

        // Écouteurs pour les ComboBox et les champs de prix
        typeComboBox.setItems(FXCollections.observableArrayList("Appartement", "Studio", "Maison", "Villa"));
        typeComboBox.setOnAction(event -> filterBiens());
        minPriceField.textProperty().addListener((observable, oldValue, newValue) -> filterBiens());
        maxPriceField.textProperty().addListener((observable, oldValue, newValue) -> filterBiens());
    }

    private void afficherTousBiens() {
        try {
            List<Bien> biens = serviceBien.afficher();
            afficherBiens(biens);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void afficherBiens(List<Bien> biens) {
        biensContainer.getChildren().clear(); // Effacer les biens existants

        for (Bien bien : biens) {
            // Filtrer par type sélectionné (ou afficher tous les biens si aucun type n'est sélectionné)
            if (typeComboBox.getValue() == null || bien.getType().equalsIgnoreCase(typeComboBox.getValue())) {
                // Filtrer par prix
                if (isInRange(bien.getPrix())) {
                    // Créer un élément visuel pour chaque bien
                    StackPane bienCard = createBienCard(bien);
                    biensContainer.getChildren().add(bienCard);

                    // Ajouter le bouton pour demander une visite
                    addButtonToBienCard(bienCard, bien);
                }
            }
        }
    }

    private boolean isInRange(double price) {
        try {
            double minPrice = Double.parseDouble(minPriceField.getText());
            double maxPrice = Double.parseDouble(maxPriceField.getText());
            return price >= minPrice && price <= maxPrice;
        } catch (NumberFormatException e) {
            return true; // Si les champs de prix sont vides ou contiennent des valeurs non valides, afficher tous les biens
        }
    }

    private StackPane createBienCard(Bien bien) {
        StackPane bienCard = new StackPane();
        bienCard.getStyleClass().add("bien-card");

        VBox bienBox = new VBox();
        bienBox.setSpacing(5);

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setPreserveRatio(true);

        try {
            String imagePath = bien.getImage();
            if (imagePath != null && !imagePath.isEmpty()) {
                Image image = new Image("file:" + imagePath);
                imageView.setImage(image);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors du chargement de l'image : " + e.getMessage());
        }

        Label nameLabel = new Label(bien.getName());
        Label prixLabel = new Label("Prix : " + bien.getPrix());
        Label typeLabel = new Label("Type : " + bien.getType());
        Label adresseLabel = new Label("Adresse : " + bien.getAdresse());

        bienBox.getChildren().addAll(imageView, nameLabel, prixLabel, typeLabel, adresseLabel);
        bienBox.getStyleClass().add("bien-details");

        bienCard.getChildren().add(bienBox);
        return bienCard;
    }

    private void addButtonToBienCard(StackPane bienCard, Bien bien) {
        Button addButton = new Button("Demander une visite");
        addButton.getStyleClass().add("addbuttonevaluation");
        addButton.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutVisite.fxml"));
                Parent root = loader.load();

                AjoutVisite controller = loader.getController();

                ComboBox<String> comboV = controller.getComboV();
                if (comboV != null) {
                    comboV.getSelectionModel().select(bien.getName());
                }

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox bienBox = (VBox) bienCard.getChildren().get(0);
        bienBox.getChildren().add(addButton);
    }

    @FXML
    private void filterBiens() {
        afficherTousBiens(); // Réafficher tous les biens en fonction des critères de filtrage
    }

    @FXML
    private void resetFilters() {
        minPriceField.clear();
        maxPriceField.clear();
        typeComboBox.getSelectionModel().clearSelection();
        afficherTousBiens(); // Réinitialiser et afficher tous les biens
    }

    @FXML
    private void openChatBotDialog() {
        ChatBotDialog chatBotDialog = new ChatBotDialog();
        chatBotDialog.setVisible(true);
    }
}
