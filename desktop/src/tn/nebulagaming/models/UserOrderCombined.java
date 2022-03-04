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
 * @author ASUS
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderCombined  {
    
    private int orderNumber;
    private Date createdDTM;
    private Date payDTM;
    private String statusOrder;
    private String payType;
    private String orderAddress;
    private String fullName;
    private Float tot;

    public UserOrderCombined(int orderNumber, Date createdDTM, Date payDTM, String fullName, String statusOrder, Float tot) {
	
	this.orderNumber = orderNumber;
	this.createdDTM = createdDTM;
	this.payDTM = payDTM;
	this.fullName = fullName;
	this.statusOrder = statusOrder;
	this.tot = tot;
    }

    
}
