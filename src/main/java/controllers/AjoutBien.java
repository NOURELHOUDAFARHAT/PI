package controllers;

import entities.Bien;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import securite.CustomSession;
import services.ServiceBien;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AjoutBien {

    private String image;
    @FXML
    private Button id_retour;


    @FXML
    private ChoiceBox<String> AdresseChoiceBox;

    private ObservableList<String> adressesGouvernorats;

    @FXML
    private TextField Nom_Input;

    @FXML
    private TextField Prix_input;

    @FXML
    private ChoiceBox<String> TypeChoiceBox;

    @FXML
    private Spinner<Integer> nbrChambreSpinner;

    @FXML
    private Label erreurNomLabel;

    @FXML
    private Label erreurPrixLabel;

    @FXML
    private ImageView image_input;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Label id_nom1;
    @FXML
    private Label userEmailLabel;
    private static String loggedInUserEmail;
    @FXML
    private BorderPane borderPane;

    @FXML
    void initialize() {
        if (!CustomSession.isUserLoggedIn()) {
            redirectToLoginPage();
        } else {
            String loggedInEmail = CustomSession.getLoggedInUserEmail();
            id_nom1.setText(loggedInUserEmail);
            userEmailLabel.setText(loggedInEmail);

        }
        adressesGouvernorats = FXCollections.observableArrayList(getAdressesGouvernorats());
        AdresseChoiceBox.setItems(adressesGouvernorats);

        TypeChoiceBox.getItems().addAll("Appartement", "Maison", "Studio", "Villa");

        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        nbrChambreSpinner.setValueFactory(spinnerValueFactory);
    }

    @FXML
    void insertImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image de bien");

        File file = fileChooser.showOpenDialog(Nom_Input.getScene().getWindow());
        if (file != null) {
            Image image1 = new Image(file.toURI().toString());
            image_input.setImage(image1);
            image = file.getAbsolutePath();
        }
    }

    @FXML
    void ajouterBien(ActionEvent event) {
        boolean nomValide = ValidationFormulaire.validerNom(Nom_Input, erreurNomLabel);
        boolean prixValide = ValidationFormulaire.validerPrix(Prix_input, erreurPrixLabel);

        if (!nomValide || !prixValide) {
            return;
        }

        int nbrChambre = nbrChambreSpinner.getValue();

        String nomValue = Nom_Input.getText().trim();
        String adresseValue = AdresseChoiceBox.getValue();
        String typeValue = TypeChoiceBox.getValue();
        int prix = Integer.parseInt(Prix_input.getText().trim());

        Bien bien = new Bien(nomValue, adresseValue, nbrChambre, prix, typeValue, image);
        try {
            ServiceBien bs = new ServiceBien();
            bs.ajouter(bien);
            chargerPageAffichage();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Succès");
            alert.setContentText("Bien ajouté");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
    private void chargerPageAffichage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/lastoflast/AfficherBien.fxml"));
            Parent root = loader.load();
            AdresseChoiceBox.getScene().setRoot(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


    public List<String> getAdressesGouvernorats() {
        List<String> adressesGouvernorats = new ArrayList<>();
        adressesGouvernorats.add("Ariana");
        adressesGouvernorats.add("Béja");
        adressesGouvernorats.add("Ben Arous");
        adressesGouvernorats.add("Bizerte");
        adressesGouvernorats.add("Gabes");
        adressesGouvernorats.add("Gafsa");
        adressesGouvernorats.add("Jendouba");
        adressesGouvernorats.add("Kairouan");
        adressesGouvernorats.add("Kasserine");
        adressesGouvernorats.add("Kébili");
        adressesGouvernorats.add("Le Kef");
        adressesGouvernorats.add("Mahdia");
        adressesGouvernorats.add("Manouba");
        adressesGouvernorats.add("Médenine");
        adressesGouvernorats.add("Monastir");
        adressesGouvernorats.add("Nabeul");
        adressesGouvernorats.add("Sfax");
        adressesGouvernorats.add("Sidi Bouzid");
        adressesGouvernorats.add("Siliana");
        adressesGouvernorats.add("Sousse");
        adressesGouvernorats.add("Tataouine");
        adressesGouvernorats.add("Tozeur");
        adressesGouvernorats.add("Tunis");
        adressesGouvernorats.add("Zaghouan");
        return adressesGouvernorats;
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/example/lastoflast/AfficherBien.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            // Affichez une erreur si le chargement de la page de connexion échoue
            System.err.println("Erreur lors du chargement de la page de connexion : " + ex.getMessage());
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
