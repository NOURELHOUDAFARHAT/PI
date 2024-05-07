package services;


import entities.reservationsdesactiviter;

import java.util.List;

public interface IReservationActiviter  <T> {

        void addReservationActiviter(reservationsdesactiviter reservationsdesactiviter);


        void supprimer(reservationsdesactiviter reservationsdesactiviter);


        List<reservationsdesactiviter> afficher();

        void modifier(reservationsdesactiviter reservationsdesactiviter);
}
