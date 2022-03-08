/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming.services;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author houba
 */
public class ServiceChat extends Thread {

    ServiceMembre se = new ServiceMembre();

    public void run(Label lab) {
	lab.setText(se.afficherM());
	System.out.println(se.afficherM());

	try {
	    Thread.sleep(100);

	} catch (InterruptedException ex) {
	    Logger.getLogger(ServiceChat.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
