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
public class OrderLine {
    
    private int idOrderLine;
    private int quantOrdLine;
    private int idProduct;
    private int numberOrder;

    public OrderLine(int quantOrdLine, int idProduct, int numberOrder) {
	this.quantOrdLine = quantOrdLine;
	this.idProduct = idProduct;
	this.numberOrder = numberOrder;
    }

    
}
