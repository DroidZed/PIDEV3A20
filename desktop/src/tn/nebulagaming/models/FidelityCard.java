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
 * @author Aymen
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FidelityCard {

    private int idFidelityCard;
    private int nbPointsFid;
    private Date createdDTM;
    private int idCardType;
    private int idUser;

    public FidelityCard(int nbPointsFid, 
			Date 
			createdDTM, 
			int idCardType, 
			int idUser) 
    {
	this.nbPointsFid = nbPointsFid;
	this.createdDTM = createdDTM;
	this.idCardType = idCardType;
	this.idUser = idUser;
    }
}
