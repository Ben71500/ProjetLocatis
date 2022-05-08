import javax.mail.internet.*;
import javax.mail.*;
import java.util.*;

/**
 * Classe permettant d'envoyer un mail.
 */
public class useMailer {
  private final static String MAILER_VERSION = "Java";
  public static boolean envoyerMailSMTP(String serveur, boolean debug) {
    boolean result = false;
    try {
      Properties prop = System.getProperties();
      prop.put("smtp.gmail.com", serveur);
      Session session = Session.getDefaultInstance(prop,null);
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress("theluffydu30@gmail.com"));
      InternetAddress[] internetAddresses = new InternetAddress[1];
      internetAddresses[0] = new InternetAddress("benjaminrandazzo2@hotmail.fr");
      message.setRecipients(Message.RecipientType.TO,internetAddresses);
      message.setSubject("Test");
      message.setText("coucou");
      message.setHeader("X-Mailer", MAILER_VERSION);
      message.setSentDate(new Date());
      session.setDebug(debug);
      Transport.send(message);
      result = true;
    } catch (AddressException e) {
      //e.printStackTrace();
    } catch (MessagingException e) {
      //e.printStackTrace();
        System.out.println(e.getMessage());
    }
    return result;
  }
   
  public static void main(String[] args) {
    useMailer.envoyerMailSMTP("108.177.15.109",true);
  }
}