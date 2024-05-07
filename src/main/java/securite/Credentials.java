package securite;

import java.io.FileWriter;
import java.io.Writer;

public class Credentials {
    private String email;
    private String password;
    private boolean rememberMe;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Credentials(String email, boolean rememberMe) {
        this.email = email;
        this.rememberMe = rememberMe;
    }
    public Credentials(String email, String password, boolean rememberMe) {
        this.email = email;
        this.password = password;
        this.rememberMe = rememberMe;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


}
