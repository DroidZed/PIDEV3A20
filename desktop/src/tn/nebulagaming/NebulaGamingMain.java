/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tn.nebulagaming;

import java.util.Calendar;
import tn.nebulagaming.models.Badge;
import tn.nebulagaming.models.Post;
import tn.nebulagaming.services.ServiceBadge;
import tn.nebulagaming.services.ServiceEvent;
import tn.nebulagaming.services.ServicePost;


/**
 *
 * @author Aymen Dhahri
 */
public class NebulaGamingMain {

    public static void main(String args[]) {
	
        ServiceBadge sb = new ServiceBadge () ; 
	Badge b = new Badge (9,"new Badge","new Badge desc","photoBadgeUpdated") ;
        //sb.add(b) ;
        //sb.update(b);
        //sb.delete(b);
        
        ServicePost sp = new ServicePost () ; 
        //Post p = new Post (9,"test title","test desc",0,"Post","test photoUpdated",1) ;
        //sp.add (p) ;
        //sp.update(p);
        //sp.delete(p);
        
        
        java.sql.Date postedDTM = new java.sql.Date (Calendar.getInstance().getTime().getTime());
        System.out.println (postedDTM) ;
        
        
    }
    
}
