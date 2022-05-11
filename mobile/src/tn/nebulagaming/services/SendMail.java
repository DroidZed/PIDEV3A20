/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.pidev.service;

import java.util.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;






/**
 *
 * @author ibeno
 */
public class SendMail {
   
public static void send(String recepient,String subject,String object) throws Exception {
      

        

}

    
      private static javax.mail.Message prepareMessage(Session session, String myAccountEmail, String sendTo,String subject,String object) {
      javax.mail.Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(sendTo));
            message.setSubject(subject);
            message.setText(object);
            return message;
        } catch (Exception ex) {
           // Logger.getLogger(AddEventForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;




}

}
