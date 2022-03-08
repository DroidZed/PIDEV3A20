/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.nebulagaming.services;

public interface IServiceUser<T> {

    public String verifierData(String id, String password);

    public int verifEtatCompte(String mail);

    public boolean verifierEmailBd(String email);

    public boolean verifierPwBd(String password);

    public void modifierPassword(String mail, String password);

}
