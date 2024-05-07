package securite;
import java.util.prefs.Preferences;

public class PreferencesManager {

    private Preferences prefs;

    public PreferencesManager() {
        prefs = Preferences.userRoot().node(this.getClass().getName());
    }

    // Écrire une préférence
    public void setPreference(String key, String value) {
        prefs.put(key, value);
    }

    // Lire une préférence
    public String getPreference(String key) {
        return prefs.get(key, null);
    }

    // Lire une préférence avec une valeur par défaut
    public String getPreference(String key, String defaultValue) {
        return prefs.get(key, defaultValue);
    }

    // Supprimer une préférence
    public void removePreference(String key) {
        prefs.remove(key);
    }

    // Supprimer toutes les préférences
    public void clearPreferences() {
        try {
            prefs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PreferencesManager prefManager = new PreferencesManager();

        // Exemple d'utilisation
        prefManager.setPreference("username", "john_doe");
        String username = prefManager.getPreference("username");
        System.out.println("Username: " + username);

        // Utilisation avec valeur par défaut
        String language = prefManager.getPreference("language", "en_US");
        System.out.println("Language: " + language);

        // Supprimer une préférence
        prefManager.removePreference("username");

        // Nettoyer toutes les préférences
        prefManager.clearPreferences();
    }
}