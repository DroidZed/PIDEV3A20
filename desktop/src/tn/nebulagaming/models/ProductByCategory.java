/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;


/**
 *
 * @author Aymen Dhahri
 */


public class ProductByCategory {

    private int idProd;
    private String nameProduct;
    private float priceProduct;
    private String nameCategory;

    public ProductByCategory() {
    }

    public ProductByCategory(int idProd, String nameProduct, float priceProduct, String nameCategory) {
	this.idProd = idProd;
	this.nameProduct = nameProduct;
	this.priceProduct = priceProduct;
	this.nameCategory = nameCategory;
    }

    public int getIdProd() {
	return idProd;
    }

    public void setIdProd(int idProd) {
	this.idProd = idProd;
    }

    public String getNameProduct() {
	return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
	this.nameProduct = nameProduct;
    }

    public float getPriceProduct() {
	return priceProduct;
    }

    public void setPriceProduct(float priceProduct) {
	this.priceProduct = priceProduct;
    }

    public String getNameCategory() {
	return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
	this.nameCategory = nameCategory;
    }

    @Override
    public String toString() {
	return nameProduct;
    }

    
}
