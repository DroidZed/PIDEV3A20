/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Aymen Dhahri
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WishList {

    private int idWishList;
    private int idUser;
    private int idProduct;

    public WishList(int idUser, int products) {
	this.idUser = idUser;
	this.idProduct = products;
    }

    
}
