/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.util.List;

/**
 *
 * @author houba
 * @param <T>
 */
public interface IServiceU<T> {

    void ajouter(T a);

    void modifier(T a);

    T loadDataModify(String id);

    List<T> afficher();

    List<T> rechercher(String index);

    List<T> trier();

    List<T> trierMulti();
}
