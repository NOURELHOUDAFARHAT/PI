module org.example.lastoflast {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    requires java.mail;
   // requires org.apache.pdfbox;
    requires java.prefs;
    requires itextpdf;
    opens org.example.lastoflast to javafx.fxml;
    //opens entities; // Ajoutez cette ligne pour ouvrir le paquetage entities
    requires activation;
   // requires org.apache.poi;
    requires com.gluonhq.maps;
    requires jfxtras.agenda;
    requires org.apache.poi.poi;
    requires org.apache.pdfbox;
    opens controllers;
    opens entities to javafx.base;
    exports controllers;
    exports test;
    exports org.example.lastoflast;
    exports securite;
    opens securite to javafx.fxml;
}