package services;


import entities.reservation_des_biens;

import java.util.List;

public interface IReservationdesBiens <T> {

    void ajouter(reservation_des_biens reservation_des_biens);




    void supprimer(reservation_des_biens reservation_des_biens);



    void modifier(reservation_des_biens reservation_des_biens);

    public List<reservation_des_biens> afficher()   ;
}
