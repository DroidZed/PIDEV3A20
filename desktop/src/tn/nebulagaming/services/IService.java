/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.util.List;
//import projet.esprit.pidev.entities.Role.RoleEnum;

/**
 *
 * @author houba
 */
public interface IService<T> {


void ajouter(T a);
    
     void modifier(T a);
   //  void supprimerUtilisateur(String mail);
     //void modifierCv(String mail, String photo);
    
     T loadDataModify(String id);
    
     List<T> afficher();
 List<T> rechercher(String index);
    List<T> trier();
List<T> trierMulti();
   //  boolean verif(String mail);
  // public T getRoleByName(RoleEnum a);
//public int verifierData(String id, String password);
    
    //public boolean verifEtatCompte(String mail);
    
   // public boolean verifierEmailBd(String email);
    
    //public boolean verifierPwBd(String password);
    
    //public void modifierPassword(String mail, String password);
    
   // public void modifierEtatCompte(String mail, Boolean etatCompte);
    
  //  public void modifierPhoto(String mail , String photo);
    
}
