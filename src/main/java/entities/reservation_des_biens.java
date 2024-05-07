package entities;

import java.util.Date;

public class reservation_des_biens {
    private int id;
    private int prix;
    private String adresse;
    private Date date_debut;
    private Date date_fin;
    private int nombre_de_membre;


    public reservation_des_biens() {
    }

    public reservation_des_biens(int id) {
        this.id = id;
    }

    public reservation_des_biens(int id, int prix, String adresse, Date date_debut, Date date_fin, int nombre_de_membre) {
        this.id = id;
        this.prix = prix;
        this.adresse = adresse;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.nombre_de_membre = nombre_de_membre;
    }

    public reservation_des_biens(int prix, String adresse, Date date_debut, Date date_fin, int nombre_de_membre) {
        this.prix = prix;
        this.adresse = adresse;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.nombre_de_membre = nombre_de_membre;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public int getNombre_de_membre() {
        return nombre_de_membre;
    }

    public void setNombre_de_membre(int nombre_de_membre) {
        this.nombre_de_membre = nombre_de_membre;
    }

    @Override
    public String toString() {
        return "reservation_des_biens{" +
                "id=" + id +
                ", prix=" + prix +
                ", adresse='" + adresse + '\'' +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", nombre_de_membre=" + nombre_de_membre +
                '}';
    }
}
