package entities;

import java.util.Date;

public class reservationsdesactiviter {
     private  int id;
     private String nom;
        private String type;
        private int prix;

        private Date date;

    public reservationsdesactiviter() {
    }

    public reservationsdesactiviter(int id) {
        this.id = id;
    }

    public reservationsdesactiviter(int id, String nom, String type, int prix, Date date) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.date = date;
    }

    public reservationsdesactiviter(String nom, String type, int prix, Date date) {
        this.nom = nom;
        this.type = type;
        this.prix = prix;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "reservations_des_activiter{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", prix=" + prix +
                ", date=" + date +
                '}';
    }
}
