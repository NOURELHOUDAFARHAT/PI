package controllers;


import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ValidationFormulaire {

    public static boolean validerNom(TextField nomInput, Label erreurLabel) {
        String nomValue = nomInput.getText().trim();

        if (nomValue.isEmpty()) {
            erreurLabel.setText("Le champ 'Nom' est requis.");
            return false;
        }

        if (!nomValue.matches("[A-Za-z]{3,}")) {
            erreurLabel.setText("Le champ 'Nom' doit contenir uniquement des lettres et au moins 3 caractères.");
            return false;
        }

        erreurLabel.setText("");
        return true;
    }

    public static boolean validerPrix(TextField prixInput, Label erreurLabel) {
        String prixValue = prixInput.getText().trim();

        if (prixValue.isEmpty()) {
            erreurLabel.setText("Le champ 'Prix' est requis.");
            return false;
        }

        if (!prixValue.matches("\\d+")) {
            erreurLabel.setText("Le champ 'Prix' doit contenir uniquement des chiffres.");
            return false;
        }

        erreurLabel.setText("");
        return true;
    }
}
