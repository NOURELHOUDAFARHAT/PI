package controllers;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
