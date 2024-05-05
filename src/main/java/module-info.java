module org.example.lastoflast {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    requires java.mail;
    requires java.prefs;
    requires itextpdf;
    requires activation;
    requires com.gluonhq.maps;
    requires jfxtras.agenda;
    requires org.apache.poi.poi;
    requires org.apache.pdfbox;
    requires org.json;
    requires com.google.zxing;


    opens org.example.lastoflast to javafx.fxml;
    opens controllers;
    opens entities to javafx.base;
    opens securite to javafx.fxml;


    exports controllers;
    exports test;
    exports org.example.lastoflast;
    exports securite;

        }
