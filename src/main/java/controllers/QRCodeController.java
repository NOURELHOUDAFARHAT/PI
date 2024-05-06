package controllers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import securite.CustomSession;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QRCodeController {
    @FXML
    private Label statusLabel;

    @FXML
    private ImageView qrCodeImageView;
    @FXML
    private TextField textField;
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
    public void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

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



    static final String QR_CODE_IMAGE_PATH = "./QRCode.png";

    @FXML
    private void generateQRCode() {
        String data = textField.getText();
        int width = 300;
        int height = 300;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            File qrCodeImage = new File(QR_CODE_IMAGE_PATH);
            ImageIO.write(bufferedImage, "png", qrCodeImage);
            Image image = new Image(new File(QR_CODE_IMAGE_PATH).toURI().toString());
            qrCodeImageView.setImage(image);


        } catch (Exception e) {
            statusLabel.setText("Erreur lors de la génération du QR Code : " + e.getMessage());
        }
    }
}
