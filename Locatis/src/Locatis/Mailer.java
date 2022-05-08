 package hermes;


import java.io.File;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author HM
 */
public class Mailer {

    private final String mailTransportProtocol;
    private final String mailHost;
    private final String mailUser;
    private final String mailPassword;
    private final String mailFrom;
    private final String rfc2231;

    public String getMailTransportProtocol() {
        return (mailTransportProtocol);
    }

    public String getMailHost() {
        return (mailHost);
    }

    public String getMailUser() {
        return (mailUser);
    }

    public String getMailPassword() {
        return (mailPassword);
    }

    public String getMailFrom() {
        return (mailFrom);
    }

    public Mailer(String mailTransportProtocol, String mailHost, String mailUser, String mailPassword, String mailFrom, String rfc2231) {
        this.mailTransportProtocol = mailTransportProtocol;
        this.mailHost = mailHost;
        this.mailUser = mailUser;
        this.mailPassword = mailPassword;
        this.mailFrom = mailFrom;
        this.rfc2231 = rfc2231;
    }

   
    /**
     * Envoi d'un message avec 1 seul pièce jointe et sans destinataire
     * invisible
     *
     *
     * @param subject : sujet du message
     * @param text : texte du message en HTML
     * @param destinataire : destinataires du message séparés par , ; ou espace
     * @param copyDest : destinataires en copie
     * @param PJ : String contenant le chemin de la PJ
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void sendMessageAvecPJ(String subject, String text, String destinataire, String copyDest, String PJ) throws NoSuchProviderException, MessagingException {

        sendMessageAvecPJ(subject, text, destinataire, copyDest, "", PJ);
    }

    /**
     * Envoi d'un message avec 1 seul pièce jointe et des destinataires
     * invisibles
     *
     * @param subject : sujet du message
     * @param text : texte du message en HTML
     * @param destinataire : destinataires du message séparés par , ; ou espace
     * @param copyDest : destinataires en copie
     * @param copyDestInvisible
     * @param PJ : String contenant le chemin de la PJ
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void sendMessageAvecPJ(String subject, String text, String destinataire, String copyDest, String copyDestInvisible, String PJ) throws NoSuchProviderException, MessagingException {

        ArrayList<String> laPJ = new ArrayList<>();
        laPJ.add(PJ);
        sendMessageAvecPJ(subject, text, destinataire, copyDest, copyDestInvisible, laPJ);
    }

    /**
     * Envoi d'un message avec plusieurs pièces jointes sans destinataire
     * invisible
     *
     * @param subject : sujet du message
     * @param text : texte du message en HTML
     * @param destinataire : destinataires du message séparés par , ; ou espace
     * @param copyDest : destinataires en copie
     * @param listePJ : ArrayList contenant le chemin des PJ
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void sendMessageAvecPJ(String subject, String text, String destinataire, String copyDest, ArrayList<String> listePJ) throws NoSuchProviderException, MessagingException {

        sendMessageAvecPJ(subject, text, destinataire, copyDest, "", listePJ);
    }

    /**
     * Envoi d'un message avec plusieurs pièces jointes avec destinataires
     * invisibles
     *
     * @param subject : sujet du message
     * @param text : texte du message en HTML
     * @param destinataire : destinataires du message séparés par , ; ou espace
     * @param copyDest : destinataires en copie
     * @param copyDestInvisible
     * @param listePJ : ArrayList contenant le chemin des PJ
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void sendMessageAvecPJ(String subject, String text, String destinataire, String copyDest, String copyDestInvisible, ArrayList<String> listePJ) throws NoSuchProviderException, MessagingException {

        // 1 -> Création de la session 
        Properties properties = new Properties();
        System.setProperty("mail.mime.encodeparameters", rfc2231);
        //Pour forcer l'encodage selon la RFC 2047 et non RFC 2231 (problème avec outlook et fichier .dat)
        //A valider ??????
        properties.setProperty("mail.mime.encodeparameters", rfc2231);
        properties.setProperty("mail.transport.protocol", mailTransportProtocol);
        properties.setProperty("mail.smtp.host", mailHost);
        properties.setProperty("mail.smtp.user", mailUser);
        properties.setProperty("mail.smtp.password", mailPassword);
        properties.setProperty("mail.from", mailFrom);

        Session session = Session.getInstance(properties);

        // 2 -> Création des la PJ dans une ArrayList
        ArrayList<MimeBodyPart> lesPJ = new ArrayList<>();
        for (String PJ : listePJ) {            
            File file = new File(PJ);
            if (file.exists()) {
                FileDataSource datasource1 = new FileDataSource(file);
                DataHandler handler1 = new DataHandler(datasource1);

                MimeBodyPart unePj = new MimeBodyPart();
                try {
                    unePj.setDataHandler(handler1);
                    unePj.setFileName(datasource1.getName());
                    lesPJ.add(unePj);
                } catch (MessagingException e) {
                }
            }
        }

        // 3 --> corps du message  
        MimeBodyPart content = new MimeBodyPart();
        try {
            content.setContent(text, "text/html; charset=utf-8");
        } catch (MessagingException e) {
        }

        // 4 --> Composition du message multi-parties
        MimeMultipart mimeMultipart = new MimeMultipart();

        try {
            mimeMultipart.addBodyPart(content); //Le corps du message
            for (MimeBodyPart unePJ : lesPJ) { //Les pièces jointes
                mimeMultipart.addBodyPart(unePJ);
            }
        } catch (MessagingException e) {
        }

        // 5 --> Création du message 
        MimeMessage message = new MimeMessage(session);
        try {
            message.setContent(mimeMultipart);
            message.setSubject(subject);
            message.addRecipients(Message.RecipientType.TO, transformeAdresse(destinataire));
            if (!copyDest.isEmpty()) {
                message.addRecipients(Message.RecipientType.CC, transformeAdresse(copyDest));
            }
            if (!copyDestInvisible.isEmpty()) {
                message.addRecipients(Message.RecipientType.BCC, transformeAdresse(copyDestInvisible));
            }
        } catch (MessagingException e) {
        }

        // 6 --> Envoi du message 
        Transport transport = session.getTransport("smtp");
        //transport.connect(serveur.getMailHost(), serveur.getMailUser(), serveur.getMailPassword());
        transport.connect(mailHost, mailUser, mailPassword);
        //transport.sendMessage(message, new Address[]{new InternetAddress(destinataire)});
        transport.sendMessage(message, transformeAdresse(destinataire));

    }

    private Address[] transformeAdresse(String destinataires) {

        ArrayList<Address> liste = new ArrayList<>();

        String cleanDestinataires = destinataires.replace(" ", ";").replace(",", ";").replace(":", ";").replace("/", ";");
        StringTokenizer separateur = new StringTokenizer(cleanDestinataires, ";");
        while (separateur.hasMoreTokens()) {
            try {
                liste.add(new InternetAddress(separateur.nextToken().toLowerCase()));

            } catch (AddressException ex) {
            }
        }
        return liste.toArray(new Address[liste.size()]);
    }

    /**
     * Envoi d'un message sans pièces jointes sans destinataire invisible
     *
     * @param subject : sujet du message
     * @param text : texte du message en HTML
     * @param destinataire : destinataires du message séparés par , ; ou espace
     * @param copyDest : destinataires en copie
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void sendMessage(String subject, String text, String destinataire, String copyDest) throws NoSuchProviderException, MessagingException {
        
        sendMessage(subject, text, destinataire, copyDest, "");
    }

    /**
     * Envoi d'un message sans pièces jointes
     *
     * @param subject : sujet du message
     * @param text : texte du message en HTML
     * @param destinataire : destinataires du message séparés par , ; ou espace
     * @param copyDest : destinataires en copie
     * @param copyDestInvisible
     * @throws NoSuchProviderException
     * @throws MessagingException
     */
    public void sendMessage(String subject, String text, String destinataire, String copyDest, String copyDestInvisible) throws NoSuchProviderException, MessagingException {

        // 1 -> Création de la session 
        Properties properties = new Properties();
        System.setProperty("mail.mime.encodeparameters", rfc2231);
        //Pour forcer l'encodage selon la RFC 2047 et non RFC 2231 (problème avec outlook et fichier .dat)
        //A valider ??????
        properties.setProperty("mail.mime.encodeparameters", rfc2231);
        properties.setProperty("mail.transport.protocol", mailTransportProtocol);
        properties.setProperty("mail.smtp.host", mailHost);
        properties.setProperty("mail.smtp.user", mailUser);
        properties.setProperty("mail.smtp.password", mailPassword);
        properties.setProperty("mail.from", mailFrom);
        Session session = Session.getInstance(properties);

        // 2 -> Création du message 
        MimeMessage message = new MimeMessage(session);
        try {
            message.setText(text, "utf-8", "html");
            message.setSubject(subject);
            message.addRecipients(Message.RecipientType.TO, transformeAdresse(destinataire));
            if (!copyDest.isEmpty()) {
                message.addRecipients(Message.RecipientType.CC, transformeAdresse(copyDest));
            }
            if (!copyDestInvisible.isEmpty()) {
                message.addRecipients(Message.RecipientType.BCC, transformeAdresse(copyDestInvisible));
            }
        } catch (MessagingException e) {
        }

        // 3 -> Envoi du message 
        Transport transport = session.getTransport("smtp");
        transport.connect(mailHost, mailUser, mailPassword);
        //transport.sendMessage(message, new Address[]{new InternetAddress(destinataire)});
        transport.sendMessage(message, transformeAdresse(destinataire));

    }

    public class ServeurInconnu extends Exception {

        /**
         * Constructeur
         *
         * @param s message retourné par la méthode qui a levé l'exception
         */
        public ServeurInconnu(String s) {
            super(s);
        }
    }
}
