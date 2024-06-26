package entities.ahmed;
import java.util.Date;

public class Service {

    public  int id;
    public String name;
    public  String adress;
    public int phone;

    public String type;

    public  Date rdv;

    public Service() {}

    public Service(int id) {
        this.id = id;
    }

    public Service(int id, String name, String adress, int phone, String type, Date rdv){
        this.id = id;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.type = type;
        this.rdv = rdv;
    }



    public int getId () {
        return id;
    }

    public void setId ( int id){
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public String getAdress () {
        return adress;
    }

    public void setAdress (String adress){
        this.adress = adress;
    }

    public int getPhone () {
        return phone;
    }

    public void setPhone ( int phone){
        this.phone = phone;
    }

    public String getType () {
        return type;
    }

    public void setType (String type){
        this.type = type;
    }

    public Date getRdv () {
        return rdv ;
    }

    public void setRdv (Date rdv){
        this.rdv = rdv;
    }

    @Override
    public String toString() {
        return "Service{" +

                ", name='" + name + '\'' +
                ", adress='" + adress + '\'' +
                ", phone=" + phone +
                ", type='" + type + '\'' +
                ", rdv=" + rdv +
                '}';
    }
}

