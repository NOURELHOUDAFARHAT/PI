package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import entities.Bien;
import java.net.URL;
import javafx.scene.control.ScrollPane;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import services.ServiceBien;

public class FrontBien implements Initializable {
    @FXML
    private TilePane biensContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            List<Bien> biens = getBiens();
            afficherBiens(biens);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Bien> getBiens() throws SQLException {
        ServiceBien serviceBien = new ServiceBien();
        return serviceBien.afficher();
    }

    private void afficherBiens(List<Bien> biens) {
        for (Bien bien : biens) {
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
            StackPane.setMargin(bienBox, new Insets(10));

            bienCard.getChildren().add(bienBox);
            biensContainer.getChildren().add(bienCard);
        }
    }
}