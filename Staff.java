package tn.esprit.entites;



public class Staff {
    private int idA;
    private String nom;
    private String type;
    private String num_tel;

    public Staff() {
    }

    public Staff(String nom, String type, String num_tel) {
        this.idA=idA;
        this.nom = nom;
        this.type = type;
        this.num_tel = num_tel;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
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

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "idA=" + idA +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", num_tel='" + num_tel + '\'' +
                '}';
    }
}

