package Locatis;
import java.util.*;

public class Campagne {
    private ArrayList<String> listeEmail;
    
    private int id;
    private String titre;
    private MyDate dateDebut;
    private MyDate dateFin;
    private MyTime heure;
    private String frequence;
    private MyDate dateProchainMail;
    private MyTime tempsProchainMail;
    private int terminer;
    private String objetMail;
    private String messageMail;
    private List<ListeDeDiffusion> listes;
    private Utilisateur utilisateur;
    private int idUtilisateur;

    public Campagne(String titre, MyDate dateDebut, MyDate dateFin, MyTime heure, String frequence, String unObjet, int utilisateur) {
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heure = heure;
        this.frequence = frequence;
        this.objetMail = unObjet;
        this.idUtilisateur = utilisateur;
    }
    
    public Campagne(int id, String titre, MyDate dateDebut, MyDate dateFin, MyTime heure, String frequence, String unObjet, String unMessage, List<ListeDeDiffusion> listes,Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heure = heure;
        this.frequence = frequence;
        this.dateProchainMail = null;
        this.tempsProchainMail = null;
        this.terminer = 0;
        this.objetMail = unObjet;
        this.messageMail = unMessage;
        this.listes = listes;
        this.utilisateur = utilisateur;
    }
    
    public Campagne(int id, String titre, MyDate dateDebut, MyDate dateFin, MyTime heure, String frequence, String unObjet, String unMessage, List<ListeDeDiffusion> listes, MyDate dateProchainEmail, int terminer, Utilisateur utilisateur) {
        this.id = id;
        this.titre = titre;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.heure = heure;
        this.frequence = frequence;
        this.dateProchainMail = dateProchainEmail;
        this.terminer = terminer;
        this.objetMail = unObjet;
        this.messageMail = unMessage;
        this.listes = listes;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }
    
    public String getTitre() {
        return titre;
    }

    public MyDate getDateDebut() {
        return dateDebut;
    }

    public MyDate getDateFin() {
        return dateFin;
    }

    public MyTime getHeure() {
        return heure;
    }
    
    public String getFrequence() {
        return frequence;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public MyDate getDateProchainMail() {
        return dateProchainMail;
    }

    public void setDateProchainMail(MyDate dateProchainMail) {
        this.dateProchainMail = dateProchainMail;
    }

    public MyTime getTempsProchainMail() {
        return tempsProchainMail;
    }

    public void setTempsProchainMail(MyTime tempsProchainMail) {
        this.tempsProchainMail = tempsProchainMail;
    }

    public int getTerminer() {
        return terminer;
    }

    public void setTerminer(int terminer) {
        this.terminer = terminer;
    }

    public String getObjetMail() {
        return objetMail;
    }

    public String getMessageMail() {
        return messageMail;
    }

    public List<ListeDeDiffusion> getListes() {
        return listes;
    }
    
    
    
    

    public ArrayList<String> getListeEmail() {
        return listeEmail;
    }

    public void setListeEmail(ArrayList<String> listeEmail) {
        this.listeEmail = listeEmail;
    }
  
    public void envoyerCampagne(){
        for (int i = 0; i < listeEmail.size(); i++){
            listeEmail.get(i);
            System.out.println("Message Envoyer");
        }
    }
}
