import java.util.Properties;
import javax.mail.internet.*;
import javax.mail.*;

public class useMailer {
    public static boolean sendEmail(String from,String password,String to,boolean highPriority,String title,String content) {
        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });
        // Used to debug SMTP issues
        session.setDebug(true);
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Create a default MimeMessage object.
            if (highPriority) {
                message.setHeader("X-Priority", "1");
                message.setHeader("x-msmail-priority", "high");
            }
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: header field
            message.setSubject(title);
            // Now set the actual message
            message.setText(content);
            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully");
            return true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(sendEmail("theluffydu30@gmail.com","bvogzfkcpurcwnnc","benjaminrandazzo2@hotmail.fr",true,"Message de test","Ceci est un message de test"));
        
    }
}