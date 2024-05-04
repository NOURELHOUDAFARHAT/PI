module org.example.lastoflast {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    requires java.mail;
    requires org.apache.pdfbox;
    requires java.prefs;

    opens org.example.lastoflast to javafx.fxml;
    opens entities; // Ajoutez cette ligne pour ouvrir le paquetage entities

    exports org.example.lastoflast;
    exports securite;
    opens securite to javafx.fxml;
}