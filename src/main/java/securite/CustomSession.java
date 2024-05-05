package securite;

public class CustomSession {
    private static final String SESSION_FILE_PATH = "session.txt";

    public static void setLoggedInUserEmail(String email, String password) {
        SessionFileManager.saveCredentials(email, password, true); // Sauvegarde de la session avec l'email et le mot de passe
    }

    public static boolean isUserLoggedIn() {
        return SessionFileManager.retrieveSavedCredentials() != null; // Vérifie si des informations de session sont disponibles
    }

    public static String getLoggedInUserEmail() {
        Credentials credentials = SessionFileManager.retrieveSavedCredentials();
        return credentials != null ? credentials.getEmail() : null; // Récupère l'email de l'utilisateur connecté
    }

    public static boolean isUserLoggedIn(String userEmail) {
        Credentials credentials = SessionFileManager.retrieveSavedCredentials();
        return credentials != null && credentials.getEmail().equals(userEmail); // Vérifie si l'utilisateur spécifié est connecté
    }

    public static void endSession() {
        SessionFileManager.saveCredentials(null, null, false); // Supprime les informations de session
    }
}
