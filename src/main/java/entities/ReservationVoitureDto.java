package entities;

import java.util.Date;

public class ReservationVoitureDto {
    private int id;
    private Date date_debut;
    private Date date_fin;
    private double prix;
    private int voiture_id;

    @Override
    public String toString() {
        return "ReservationVoitureDto{" +
                "id=" + id +
                ", date_debut=" + date_debut +
                ", date_fin=" + date_fin +
                ", prix=" + prix +
                ", voiture_id=" + voiture_id +
                '}';
    }

    public ReservationVoitureDto(int id) {
        this.id = id;
    }

    public ReservationVoitureDto(int id, Date date_debut, Date date_fin, double prix, int voiture_id) {
        this.id = id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.prix = prix;
        this.voiture_id = voiture_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getVoiture_id() {
        return voiture_id;
    }

    public void setVoiture_id(int voiture_id) {
        this.voiture_id = voiture_id;
    }

}
