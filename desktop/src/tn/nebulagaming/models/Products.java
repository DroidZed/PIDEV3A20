/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

/**
 *
 * @author rayen
 */
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Products {
    private int idProduct; 
    private String nameProduct;
    private float priceProduct;
    private int qtyProduct;
    private String imageProduct;
    private int idCategory ; 
    private int idUser;
    private Date createdDTM;


public Products (String nameProduct ,  float priceProduct , int qtyProduct , String imageProduct  , int idUser , int idCategory  ){
    this.nameProduct=nameProduct;
    this.priceProduct = priceProduct ; 
    this.qtyProduct = qtyProduct ;
    this.imageProduct=imageProduct ;
    this.idCategory= idCategory;
    this.idUser= idUser;
}

}
