/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.models;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author rayen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Promos {

    private int idPromo;
    private String codePromo;
    private Date createdDTM;
    private int discountPromo;
    private int idProduct;

    public Promos(String codePromo, int discountPromo, int idProduct) {
        this.codePromo = codePromo;
        this.discountPromo = discountPromo;
        this.idProduct = idProduct;
    }
}
