/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author anony
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

 private Integer idproduct;
 private String nameproduct;
 private Float priceproduct;
 private Integer qtyproduct;
 Category idCategory;
 User idUser;

    public Product(String nameproduct, Float priceproduct, Integer qtyproduct, Category idCategory, User idUser) {
	this.nameproduct = nameproduct;
	this.priceproduct = priceproduct;
	this.qtyproduct = qtyproduct;
	this.idCategory = idCategory;
	this.idUser = idUser;
    }

    public Product(String nameproduct, Float priceproduct, Integer qtyproduct) {
	this.nameproduct = nameproduct;
	this.priceproduct = priceproduct;
	this.qtyproduct = qtyproduct;
    }

    
}
