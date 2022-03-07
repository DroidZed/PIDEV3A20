/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author dell
 * @param <T>
 */
public interface Services<T> {

    public void ajouter(T t)throws SQLException;
    public void supprimer(T t)throws SQLException;
    public void modifier(T t)throws SQLException;
    //public void modifier2(T t)throws SQLException;
    public List<T> afficher()throws SQLException;
    //public void TriDate (publication p);

    
}
