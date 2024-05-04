package entities;

public class Client extends User {
    //Constrecteur par defaut
    public Client() {super();}
    //Constrecteur avec Id
    public Client(int id, String email, String[] roles, String password, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        super(id, email, roles, password, nom, prenom, adresse, sexe, is_activated);
    }
    //Constrecteur sans Id

    public Client(String email, String[] roles, String password, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        super(email, roles, password, nom, prenom, adresse, sexe, is_activated);
    }
    //Constrecteur sans mot de passe pour modification

    public Client(int id, String email, String[] roles, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        super(id, email, roles, nom, prenom, adresse, sexe, is_activated);
    }


}
