/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tn.nebulagaming.services;

import java.util.List;
import tn.nebulagaming.models.Products;

/**
 *
 * @author anony
 * @param <T>
 */
public interface IProduct <T> {
    public void ajouter(T t);
    public void supprimer(T t);
    public void modifier(T t);
    public List<T> afficher();
    public List<Products> sortByName();
    public Products findByname_Product(String name_Product);
    public Products findByID_Product(int id);
    public List<Products> paginateProducts(int pageNum, int pageSize);
    
}
