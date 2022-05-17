/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.utils;

import java.util.ArrayList;
import tn.nebulagaming.entities.CartItem;

/**
 *
 * @author ASUS
 */
public class ShoppingCartManager {

    private static ShoppingCartManager instance;

    private int idUser;
    private ArrayList<CartItem> prodsWithQuantity;
    private float sum;

    private ShoppingCartManager() {

    prodsWithQuantity = new ArrayList<>();
    idUser = 0;

    }

    public int getIdUser() {
	return idUser;
    }

    public void setIdUser(int idUser) {
	this.idUser = idUser;
    }

    public ArrayList<CartItem> getProdsWithQuantity() {
	return prodsWithQuantity;
    }

    public void setProdsWithQuantity(ArrayList<CartItem> prodsWithQuantity) {
	this.prodsWithQuantity = prodsWithQuantity;
    }

    public static ShoppingCartManager getInstance() {

	if (instance == null) {
	    instance = new ShoppingCartManager();
	}
	return instance;
    }

    public float getSum() {
	return sum;
    }

    public void setSum(float sum) {
	this.sum = sum;
    }
    
}
