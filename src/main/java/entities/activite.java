package entities;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class activite {
    private int id;
    private String nom;
    private String type;
    private java.sql.Timestamp date_heure;
    private double prix;
    private String image;
    private String description;
    private int idA_staff;
    private String code;

    public activite() {
    }

    public activite(String nom, String type, LocalDateTime date_heure, double prix, String image, String description, int idA_staff) {
        this.nom = nom;
        this.type = type;
        this.date_heure = Timestamp.valueOf(date_heure);
        this.prix = prix;
        this.image = image;
        this.description = description;
        this.idA_staff = idA_staff;
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

    public java.sql.Timestamp getDate_heure() {
        return date_heure;
    }

    public void setDate_heure(java.sql.Timestamp date_heure) {
        this.date_heure = date_heure;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public int getIdA_staff() {
        return idA_staff;
    }

    public void setIdA_staff(int idA_staff) {
        this.idA_staff = idA_staff;
    }

    @Override
    public String toString() {
        return "Activite{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", date_heure=" + date_heure +
                ", prix=" + prix +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", idA_staff='" + idA_staff + '\'' +
                '}';
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
