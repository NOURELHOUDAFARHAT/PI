package org.example.lastoflast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationFormulaireController {

    public static boolean validerNom(String nom) {
        return nom.length() >= 3;
    }

    public static boolean validerPrenom(String prenom) {
        return prenom.length() >= 5;
    }

    public static boolean validerEmail(String email) {
        Pattern emailPattern = Pattern.compile("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$");
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }

    public static boolean validerMotDePasse(String motDePasse) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
        Matcher passwordMatcher = passwordPattern.matcher(motDePasse);
        return passwordMatcher.find();
    }
}
