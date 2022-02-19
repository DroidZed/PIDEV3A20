/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.util.List;

/**
 *
 * @author dell
 * @param <T>
 */
public interface Services<T> {

    public void ajouter(T t);
    public void supprimer(T t);
    public void modifier(T t);
    public List<T> afficher();
    public List<T> rechercher(T t);
    
}
