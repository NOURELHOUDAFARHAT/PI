package services;


import entities.reservation_des_voitures;

import java.util.List;

public interface IResarvationVoiture<T>{
    public void ajouter(T t);
    public void supprimer(T t);
    public void modifier(T t);
   List<reservation_des_voitures> afficher();
}
