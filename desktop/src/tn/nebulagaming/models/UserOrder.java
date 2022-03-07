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
 * @author Aymen Dhahri
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrder {

    private int numberOrder;
    private Date createdDTM;
    private Date payDTM;
    private int idStatusOrder;
    private int idPayType;
    private String orderAddress;
    private int idUser;

    public UserOrder(Date createdDTM, String orderAddress, int idUser) {
	this.createdDTM = createdDTM;
	this.orderAddress = orderAddress;
	this.idUser = idUser;
    }

    public UserOrder(String orderAddress, int idUser) {
	this.orderAddress = orderAddress;
	this.idUser = idUser;
    }

    public UserOrder(Date payDTM, int idPayType, String orderAddress, int idUser) {
	this.payDTM = payDTM;
	this.idPayType = idPayType;
	this.orderAddress = orderAddress;
	this.idUser = idUser;
    }

}
