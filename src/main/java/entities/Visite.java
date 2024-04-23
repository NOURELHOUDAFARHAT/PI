package entities;
import java.time.LocalDate;
import java.util.Objects;

public class Visite {
    private int id ;
    private  int ref_b ;
    private  int numero;
    private LocalDate date_visite;
    private   String email;
    private  String name;

    public Visite() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRef_B() {
        return ref_b;
    }

    public void setRef_B(int ref_b) {
        this.ref_b = ref_b;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDate_visite() {
        return date_visite;
    }

    public void setDate_visite(LocalDate date_visite) {
        this.date_visite = date_visite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Visite(int id, int ref_b, int numero, LocalDate date_visite, String email, String name) {
        this.id = id;
        this.ref_b = ref_b;
        this.numero = numero;
        this.date_visite = date_visite;
        this.email = email;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visite visite = (Visite) o;
        return id == visite.id && ref_b == visite.ref_b && numero == visite.numero && Objects.equals(date_visite, visite.date_visite) && Objects.equals(email, visite.email) && Objects.equals(name, visite.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ref_b, numero, date_visite, email, name);
    }

    @Override
    public String toString() {
        return "Visite{" +
                "id=" + id +
                ", ref_b=" + ref_b +
                ", numero=" + numero +
                ", date_visite=" + date_visite +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
