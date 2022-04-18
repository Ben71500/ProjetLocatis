/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Session;
import javax.mail.internet.*;

/**
 *
 * @author benja
 */
public class testMail {
    public static void main(String[] args){
        try{
            /*Session session = Session.getInstance(props,authenticator);
            // creation d'une session partagee
            Session defaultSession = Session.getDefaultInstance(props,authenticator);*/
            InternetAddress vInternetAddresses = new InternetAddress("benjaminrandazzo2@hotmail.fr");
        }catch(AddressException ex){
            System.out.println(ex.getMessage());
        }
    }
    /*public void envoyerMail(){
        Authenticator auth = new Authenticator();
        Properties p = new Properties();
        p.put("mail.smtp.host", "smtp.gmail.com");
        p.put("mail.smtp.socketFactory.port", "465");
        p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.port", "465");
        Session session = Session.getInstance(p,authenticator);
    }*/
}
