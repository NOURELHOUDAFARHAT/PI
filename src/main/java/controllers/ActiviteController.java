package controllers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.CustomSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ActiviteController {
    @FXML
    private Button id_retour;

    @FXML
    private ImageView activite1;

    @FXML
    private ImageView activite2;

    @FXML
    private Label statusLabel;

    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;
    private static String loggedInUserEmail;

    static final String QR_CODE_IMAGE_PATH = "./QRCode.png";

    @FXML
    public void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        // Initialisation des éléments FXML
        statusLabel.setText("");
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


    // Méthode pour générer le code QR à partir des données formatées
    private BufferedImage bitMatrixToImage(BitMatrix bitMatrix) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return bufferedImage;
    }

    private void generateQRCodeFromData(String prenom, String type, double prix, String dateHeure) {
        String data = String.format("Nom: %s\nType: %s\nPrix: %.2f\nDate et Heure: %s", prenom, type, prix, dateHeure);
        int width = 300;
        int height = 300;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bitMatrixToImage(bitMatrix), "png", outputStream);
            byte[] byteArray = outputStream.toByteArray();

            Image image = new Image(new ByteArrayInputStream(byteArray));
            qrCodeImageView.setImage(image);


        } catch (Exception e) {
            statusLabel.setText("Erreur lors de la génération du QR Code : " + e.getMessage());
        }
    }

    @FXML
    void afficherDetails1() {
        String prenom = "SPA";
        String type = "Luxe";
        double prix = 200.0;
        String dateHeure = "Mardi 25 Juin 2024";
        generateQRCodeFromData(prenom, type, prix, dateHeure);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'Activité");
        alert.setHeaderText(null);
        alert.setGraphic(new ImageView(qrCodeImageView.getImage()));
        alert.showAndWait();
    }

    @FXML
    void afficherDetails2() {
        String prenom = "marine";
        String type = "aquatique";
        double prix = 1000.0;
        String dateHeure = "06 aout 2024";
        generateQRCodeFromData(prenom, type, prix, dateHeure);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'Activité");
        alert.setHeaderText(null);
        alert.setGraphic(new ImageView(qrCodeImageView.getImage()));
        alert.showAndWait();
    }

    @FXML
    void afficherDetails3() {
        String prenom = "parachute";
        String type = "aquatique";
        double prix = 300.0;
        String dateHeure = "25 aout 2024";
        generateQRCodeFromData(prenom, type, prix, dateHeure);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Détails de l'Activité");
        alert.setHeaderText(null);
        alert.setGraphic(new ImageView(qrCodeImageView.getImage()));
        alert.showAndWait();
    }

    @FXML
    void afficherDetails4() {
        String prenom = "quad";
        String type = "dunes de sable";
        double prix = 100.0;
        String dateHeure = "06 aout 2024";
        generateQRCodeFromData(prenom, type, prix, dateHeure);
    }

    @FXML
    void afficherDetails5() {
        String prenom = "camping";
        String type = "excursion";
        double prix = 200.0;
        String dateHeure = "03 décembre 2024";
        generateQRCodeFromData(prenom, type, prix, dateHeure);
    }

    @FXML
    void afficherDetails6() {
        String prenom = "parc aquatique";
        String type = "aquatique";
        double prix = 350.0;
        String dateHeure = "06 juillet 2024";
        generateQRCodeFromData(prenom, type, prix, dateHeure);
    }

    @FXML
    public void initialize(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/meteo.fxml"));
            qrCodeImageView.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/HomeAll.fxml"));
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


