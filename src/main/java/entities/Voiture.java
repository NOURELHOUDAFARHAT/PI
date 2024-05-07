package entities;

public class Voiture {

 private int id;
    private String num_immatriculation;
    private String modele;
    private String couleur;
    private int disponible;
    private double prix_per_day;



    public Voiture() {
    }


    public Voiture(int id, String num_immatriculation, String modele, String couleur, int disponible, double prix_per_day) {
        this.id = id;
        this.num_immatriculation = num_immatriculation;
        this.modele = modele;
        this.couleur = couleur;
        this.disponible = disponible;
        this.prix_per_day = prix_per_day;
    }

    public Voiture(String num_immatriculation, String modele, String couleur, int disponible, double prix_per_day) {
        this.num_immatriculation = num_immatriculation;
        this.modele = modele;
        this.couleur = couleur;
        this.disponible = disponible;
        this.prix_per_day = prix_per_day;
    }

    public Voiture(int id) {
        this.id = id;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNum_immatriculation() {
        return num_immatriculation;
    }

    public void setNum_immatriculation(String num_immatriculation) {
        this.num_immatriculation = num_immatriculation;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public int getDisponible() {
        return disponible;
    }

    public void setDisponible(int disponible) {
        this.disponible = disponible;
    }

    public double getPrix_per_day() {
        return prix_per_day;
    }

    public void setPrix_per_day(double prix_per_day) {
        this.prix_per_day = prix_per_day;
    }

    @Override
    public String toString() {
        return "Voiture{" +
                "id=" + id +
                ", num_immatriculation='" + num_immatriculation + '\'' +
                ", modele='" + modele + '\'' +
                ", couleur='" + couleur + '\'' +
                ", disponible=" + disponible +
                ", prix_per_day=" + prix_per_day +
                '}';
    }
}
