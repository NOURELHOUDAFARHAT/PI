package securite;

public class SessionData {
    private String email;
    private boolean rememberMe;
    private Credentials credentials;

    public SessionData(Credentials credentials, boolean rememberMe) {
        this.credentials = credentials;
        this.rememberMe = rememberMe;
    }

    public Credentials getCredentials() {
        return credentials;
    }



    public SessionData(String email, boolean rememberMe) {
        this.email = email;
        this.rememberMe = rememberMe;
    }

    // Ajoutez ici les getters et setters si nécessaire

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}

