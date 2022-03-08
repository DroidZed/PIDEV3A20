/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tn.nebulagaming.services;

import java.util.List;

/**
 *
 * @author SuperNova
 * @param <T>
 */
public interface IServiceNewsfeed<T> {
   public void add (T t) ;
   public void delete (int id) ;
   public void update (T t) ; 
   public List<T> display () ; 
}
