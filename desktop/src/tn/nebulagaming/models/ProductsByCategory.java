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
 * @author anony
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsByCategory {

    private int idProd;
    private String nom;
    private Float prix;
    private Integer quantity;
    private String imageProduct;
    private String category;
}
