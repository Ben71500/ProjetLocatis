package Locatis;
import java.util.*;

public class Campagne {
    private ArrayList<String> listeEmail;
    
    private int id;
    private MyDate dateDebut;
    private MyDate dateFin;
    private String frequence;
    private Utilisateur utilisateur;

    public Campagne(int id, MyDate dateDebut, MyDate dateFin, String frequence, Utilisateur utilisateur) {
        this.id = id;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.frequence = frequence;
        this.utilisateur = utilisateur;
    }

    public int getId() {
        return id;
    }

    public MyDate getDateDebut() {
        return dateDebut;
    }

    public MyDate getDateFin() {
        return dateFin;
    }

    public String getFrequence() {
        return frequence;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
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
