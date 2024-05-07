package entities;

public class Bien {
    private int refB ;
    private  String name;
    private  String adresse;
    private  int nbrChambre;
    private  int prix ;
    private   String type;
    private   String image;

    public void setRefB(int refB) {
        this.refB = refB;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNbrChambre(int nbrChambre) {
        this.nbrChambre = nbrChambre;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public int getRefB() {
        return refB;
    }

    public String getName() {
        return name;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getNbrChambre() {
        return nbrChambre;
    }

    public int getPrix() {
        return prix;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }



    public Bien(int refB, String name, String adresse, int nbrChambre, int prix, String type, String image) {
        this.refB = refB;
        this.name = name;
        this.adresse = adresse;
        this.nbrChambre = nbrChambre;
        this.prix = prix;
        this.type = type;
        this.image = image;
    }

    public Bien() {
    }

    public Bien(String name, String adresse, int nbrChambre, int prix, String type, String image) {
        this.name = name;
        this.adresse = adresse;
        this.nbrChambre = nbrChambre;
        this.prix = prix;
        this.type = type;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Bien{" +
                "refB=" + refB +
                ", name='" + name + '\'' +
                ", adresse='" + adresse + '\'' +
                ", nbrChambre=" + nbrChambre +
                ", prix=" + prix +
                ", type='" + type + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
