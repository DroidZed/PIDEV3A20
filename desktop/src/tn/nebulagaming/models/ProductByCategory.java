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
@AllArgsConstructor
@NoArgsConstructor
public class ProductByCategory {

    private int idProd;
    private String nameProduct;
    private float priceProduct;
    private String nameCategory;
}
