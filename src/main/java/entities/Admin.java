package entities;

public class Admin extends User {

    // Constructeur par défaut
    public Admin() {
        super();
    }

    // Constructeur avec id
    public Admin(int id, String email, String password, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        super(id, email, new String[]{"ROLE_ADMIN"}, password, nom, prenom, adresse, sexe, is_activated);
    }

    // Constructeur sans id
    public Admin(String email, String password, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        super(email, new String[]{"ROLE_ADMIN"}, password, nom, prenom, adresse, sexe, is_activated);
    }

    // Constructeur sans mot de passe pour la modification
    public Admin(int id, String email, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        super(id, email, new String[]{"ROLE_ADMIN"}, nom, prenom, adresse, sexe, is_activated);
    }
}
