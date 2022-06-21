package Objets_Locatis;
import java.util.List;

/**
 * Classe décrivant une campagne.
 * @author Benjamin Mathilde
 */
public class Campagne {
    
    private int id;
    private String titre;
    private MyDate dateDebut;
    private MyDate dateFin;
    private MyTime heure;
    private String frequence;
    private MyDate dateProchainMail;
    private MyTime tempsProchainMail;
    private int terminee;
    private String objetMail;
    private String messageMail;
    private List<ListeDeDiffusion> listes;
    private Utilisateur utilisateur;
    
    /**
     * Constructeur d'une campagne lors de la création de celle-ci par un utilisateur
     * @param id : id de la campagne (entier)
     * @param titre : c'est le nom donné à la campagne (chaine de caractères)
     * @param dateDebut : date de début de la campagne (date)
     * @param dateFin : date de fin de la campagne (date)
     * @param heure : heure d'envoi des messages (heure)
     * @param frequence : frequence à laquelle sont envoyés les messages (chaîne de caractères)
     * @param unObjet : objet des mails de la campagne (chaîne de caractères)
     * @param unMessage : texte des mails de la campagne (chaîne de caractères)
     * @param listes : listes des personnes qui recevront les mails (liste de listes de diffusion)
     * @param utilisateur : utilisateur qui gère la campagne, cad celui qui la créé ou qui l'a modifié en dernier (Utilisateur)
     */
    public Campagne(int id, String titre, MyDate dateDebut, MyDate dateFin, MyTime heure, String frequence, String unObjet, String unMessage, List<ListeDeDiffusion> listes,Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heure = heure;
        this.frequence = frequence;
        this.dateProchainMail = null;
        this.tempsProchainMail = null;
        this.terminee = 0;
        this.objetMail = unObjet;
        this.messageMail = unMessage;
        this.listes = listes;
        this.utilisateur = utilisateur;
    }
    
    /**
     * Constructeur d'une campagne avec des valeurs calculées
     * @param id : id de la campagne (entier)
     * @param titre : c'est le nom donné à la campagne (chaine de caractères)
     * @param dateDebut : date de début de la campagne (date)
     * @param dateFin : date de fin de la campagne (date)
     * @param heure : heure d'envoi des messages (heure)
     * @param frequence : frequence à laquelle sont envoyés les messages (chaîne de caractères)
     * @param unObjet : objet des mails de la campagne (chaîne de caractères)
     * @param unMessage : texte des mails de la campagne (chaîne de caractères)
     * @param listes : listes des personnes qui recevront les mails (liste de listes de diffusion)
     * @param dateProchainEmail : date d'envoi du prochain mail de la camapgne (date)
     * @param terminee : entier qui vaut 1 si la campagne est terminée et 0 sinon (entier)
     * @param utilisateur : utilisateur qui gère la campagne, cad celui qui la créé ou qui l'a modifié en dernier (Utilisateur)
     */
    public Campagne(int id, String titre, MyDate dateDebut, MyDate dateFin, MyTime heure, String frequence, String unObjet, String unMessage, List<ListeDeDiffusion> listes, MyDate dateProchainEmail, int terminee, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heure = heure;
        this.frequence = frequence;
        this.dateProchainMail = dateProchainEmail;
        this.terminee = terminee;
        this.objetMail = unObjet;
        this.messageMail = unMessage;
        this.listes = listes;
        this.utilisateur = utilisateur;
    }

    /**
     *
     * @return l'id de la campagne
     */
    public int getId() {
        return id;
    }
    
    /**
     *
     * @return le titre de la campagne
     */
    public String getTitre() {
        return titre;
    }

    /**
     *
     * @return la date de début de la campagne
     */
    public MyDate getDateDebut() {
        return dateDebut;
    }

    /**
     *
     * @return la date de fin de la campagne
     */
    public MyDate getDateFin() {
        return dateFin;
    }

    /**
     *
     * @return l'heure d'envoi des messages
     */
    public MyTime getHeure() {
        return heure;
    }
    
    /**
     *
     * @return la frequence à laquelle sont envoyés les messages
     */
    public String getFrequence() {
        return frequence;
    }
    
    /**
     *
     * @return l'objet des mails de la campagne
     */
    public String getObjetMail() {
        return objetMail;
    }

    /**
     *
     * @return le texte des mails de la campagne
     */
    public String getMessageMail() {
        return messageMail;
    }
    
    /**
     *
     * @return les listes des personnes qui recevront les mails
     */
    public List<ListeDeDiffusion> getListes() {
        return listes;
    }
    
    /**
     *
     * @return la date d'envoi du prochain mail de la camapgne
     */
    public MyDate getDateProchainMail() {
        return dateProchainMail;
    }
    
    /**
     *
     * @return un entier qui vaut 1 si la campagne est terminée et 0 sinon
     */
    public int getTerminee() {
        return terminee;
    }

    /**
     *
     * @return l'utilisateur qui gère la campagne (celui qui la créé ou qui l'a modifié en dernier)
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    
    /**
     * Méthode qui permet de modifier la date d'envoi du prochain mail de la camapgne
     * @param dateProchainMail : nouvelle date
     */
    public void setDateProchainMail(MyDate dateProchainMail) {
        this.dateProchainMail = dateProchainMail;
    }
    
    /**
     * Méthode qui permet de modifier l'état de la campagne (terminée ou non)
     * @param terminee : entier qui vaut 0 ou 1
     */
    public void setTerminee(int terminee) {
        this.terminee = terminee;
    }

    /**
     * Méthode qui permet de modifier l'utilisateur
     * @param utilisateur : nouvel utilisateur
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}