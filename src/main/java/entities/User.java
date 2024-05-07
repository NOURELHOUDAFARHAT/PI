package entities;

import java.util.Arrays;

public class User {
    private int userId;
    private int id;
    private String email;
    private String[] roles;
    private String password;
    private String nom;
    private String prenom;
    private String adresse;
    private String sexe;
    private boolean is_activated;

    //constructeur par défaut
    public User() {
    }

    //constructeur avec id
    public User(int id, String email, String[] roles, String password, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.sexe = sexe;
        this.is_activated = is_activated;
    }


    //constructeur sans id
    public User(String email, String[] roles, String password, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.sexe = sexe;
        this.is_activated = is_activated;
    }

    public User(String email) {
        this.email=email;
    }

    public User(int userId, String nom, String prenom, String adresse, String sexe) {
        this.userId = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.sexe = sexe;

    }

    public User(int id, String nom, String prenom, String[] roles, String email, String password, String adresse, String sexe) {

        this.userId = id;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
        this.email=email;
        this.password=password;
        this.adresse = adresse;
        this.sexe = sexe;

    }

    public User(int idUser, String nom, String prenom, String email, String password, String adresse, String sexe) {
        this.userId = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email=email;
        this.password=password;
        this.adresse = adresse;
        this.sexe = sexe;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    //constructeur sans mot de passe pour la modification
    public User(int id, String email, String[] roles, String nom, String prenom, String adresse, String sexe, boolean is_activated) {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.sexe = sexe;
        this.is_activated = is_activated;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public boolean isIs_activated() {
        return is_activated;
    }


    public void setisIs_activated(boolean is_activated) {
        this.is_activated = is_activated;
    }


    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", password='" + password + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", adresse='" + adresse + '\'' +
                ", sexe='" + sexe + '\'' +
                ", is_activated=" + is_activated +
                '}';
    }
    public void setMotDePasse(String motDePasse) {
        this.password = motDePasse;
    }

}
