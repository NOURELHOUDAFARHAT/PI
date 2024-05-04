package test;

import entities.User;
import services.UserService;

public class Main {
    public static void main(String[] args) {
        // Créer un tableau de rôles vide
        String[] roles = new String[0];

        // Créer un nouvel utilisateur avec le tableau de rôles vide
        User u3 = new User("yahydaoui@gmaiil.com", roles, "12345JaJKG?.", "wael", "yahyaoui", "jendouba", "homme", true);
        User u2 = new User("wadfa@gmaiil.com", roles, "12345JaJKG?.", "wael", "yahyaoui", "jendouba", "homme", true);

        // Créer une instance de UserService
        UserService us = new UserService();

        // Appeler la méthode add de UserService pour ajouter l'utilisateur
        //us.add(u3);
        //us.add(u2);
        us.getAll().forEach(System.out::println);
    }
}