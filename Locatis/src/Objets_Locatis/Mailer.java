package Objets_Locatis;

import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Classe qui permet d'envoyer un mail.
 * @author Benjamin Mathilde
 */
public class Mailer {
    
    /**
     * Méthode qui envoie un mail à une liste de personnes
     * @param mailSender : email de celui qui envoie le mail
     * @param passwordSender : mot de passe de celui qui envoie le mail
     * @param object : objet du mail
     * @param message : message contenu dans le mail
     * @param listeMailTo : liste d'adresses mails à qui on envoie le mail
     */
    public void sendEmail(String mailSender, String passwordSender, String object, String message, ArrayList<String> listeMailTo){
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailSender, passwordSender);
            }
        });
        
        try {
            // Create a default MimeMessage object.
            MimeMessage messageSend = new MimeMessage(session);
            // Set From: header field of the header.
            messageSend.setFrom(new InternetAddress(mailSender));
            // Set To: header field of the header.
            for(int i = 0; i < listeMailTo.size(); i++){
                messageSend.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(listeMailTo.get(i)));
            }
            // Set Subject: header field
            messageSend.setSubject(object);
            // Now set the actual message
            messageSend.setText(message);
            System.out.println("sending...");
            // Send message
            Transport.send(messageSend);
            System.out.println("Sent message successfully");
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
}
