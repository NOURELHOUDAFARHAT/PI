package securite;

import java.io.*;

public class SessionFileManager {
    private static final String CREDENTIALS_FILE_PATH = "credentials.txt";

    public static void saveCredentials(String email, String password, boolean rememberMe) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CREDENTIALS_FILE_PATH))) {
            writer.write(email);
            writer.newLine();
            writer.write(password);
            writer.newLine();
            writer.write(String.valueOf(rememberMe));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Credentials retrieveSavedCredentials() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CREDENTIALS_FILE_PATH))) {
            String email = reader.readLine();
            String password = reader.readLine();
            String rememberMeStr = reader.readLine();
            if (email != null && password != null && rememberMeStr != null) {
                boolean rememberMe = Boolean.parseBoolean(rememberMeStr);
                return new Credentials(email, password, rememberMe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
