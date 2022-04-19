package MVC;

import DAO.*;
import Locatis.*;
import java.sql.Connection;

public class Modele_AjoutModif{
    
    private String donnees;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());

    /**
     * Constructeur du modèle
     */    
    public Modele_AjoutModif(String lesDonnees) {
        this.donnees = lesDonnees;
    }
    
    public void ajouterLocataire(Locataire nouveauLocataire){
        Locataire_DAO locataireDAO=new Locataire_DAO(connBdd);
        locataireDAO.create(nouveauLocataire);
    }
    
    public void ajouterUtilisateur(Utilisateur nouvelUtilisateur){
        Utilisateurs_DAO utilisateurDAO=new Utilisateurs_DAO(connBdd);
        utilisateurDAO.create(nouvelUtilisateur);
    }
    
    public void ajouterMessage(Message nouveauMessage){
        Message_DAO messageDAO=new Message_DAO(connBdd);
        messageDAO.create(nouveauMessage);
    }
    
    public void ajouterCampagne(Campagne nouvelleCampagne){
        /*Message_DAO messageDAO=new Message_DAO(connBdd);
        messageDAO.create(nouveauMessage);*/
    }
    
    public void modifierLocataire(Locataire leLocataire){
        Locataire_DAO locataireDAO=new Locataire_DAO(connBdd);
        locataireDAO.update(leLocataire);
    }
    
    public void modifierUtilisateur(Utilisateur utilisateur){
        Utilisateurs_DAO utilisateurDAO=new Utilisateurs_DAO(connBdd);
        utilisateurDAO.update(utilisateur);
    }
    
    public void modifierMessage(Message leMessage){
        Message_DAO messageDAO=new Message_DAO(connBdd);
        messageDAO.update(leMessage);
    }
    
    public void modifierCampagne(Campagne laCampagne){
        /*Message_DAO messageDAO=new Message_DAO(connBdd);
        messageDAO.update(leMessage);*/
    }
}