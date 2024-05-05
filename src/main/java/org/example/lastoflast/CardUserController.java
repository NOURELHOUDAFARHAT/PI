package org.example.lastoflast;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Collections;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CardUserController {
    @FXML
    private Label nomLabel;

    @FXML
    private Label prenomLabel;

    @FXML
    private Label emailLabel;

    // Référence à l'utilisateur
    private User user;

    // Méthode pour initialiser les données de l'utilisateur
    public void initData(User user) {
        this.user = user;
        nomLabel.setText(user.getNom());
        prenomLabel.setText(user.getPrenom());
        emailLabel.setText(user.getEmail());
    }

 @FXML
    void Imprimé(ActionEvent event) {
        if (user != null) {
            try {
                // Génération du fichier PDF
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialFileName("user_details.pdf");
                File selectedFile = fileChooser.showSaveDialog(null);

                if (selectedFile != null) {
                    String filePath = selectedFile.getAbsolutePath();
                    ExporterPdf.exportToPDF("Détails de l'utilisateur", Collections.singletonList(user), filePath);
                    System.out.println("PDF généré avec succès !");

                    // Copie du fichier PDF vers l'emplacement sélectionné
                    Path source = new File("user_details.pdf").toPath();
                    Path destination = selectedFile.toPath();
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("PDF téléchargé avec succès !");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
