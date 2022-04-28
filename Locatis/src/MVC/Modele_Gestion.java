package MVC;

import DAO.*;
import Locatis.*;
import static interfaceGraphique.GestionBatiment.getListeMaison;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class  Modele_Gestion {
    
    private String[][] tableau;
    private String[] entetes;
    private List liste;
    private String donnees;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());

    /**
     * Constructeur du modèle
     */    
    public Modele_Gestion(String lesDonnees) {
        this.donnees = lesDonnees;
    }

    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }
    
    

    public void setEntetes(String[] entetes) {
        this.entetes = entetes;
    }

    public String[][] getTableau() {
        return tableau;
    }

    public String[] getEntetes() {
        return entetes;
    }

    public List getListe() {
        return liste;
    }
    
    public Object getSelection(int nb){
        return liste.get(nb);
    }
    
    public void initialiser(){
        switch(this.donnees){
            case "locataires" -> modeleLocataires();
            case "utilisateurs" -> modeleUtilisateurs();
            case "messages" -> modeleMessages();
            case "appartements" -> modeleAppartement();
            case "maisons" -> modeleMaison();
            case "campagnes" -> modeleCampagne();
        }
    }

    public void modeleLocataires(){
        String[] tabEntetes = {"ID","Nom", "Prénom", "Age", "Ancienneté", "Mail", "Téléphone"};
        this.setEntetes(tabEntetes);
        
        //Récupération des locataires dans une ArrayList
        Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        liste=(ArrayList<Locataire>)lesLocataires.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        tableau = new String[liste.size()][7];
        for(int i=0; i<liste.size();i++){
            Locataire leLocataire = (Locataire) liste.get(i);
            tableau [i][0]=leLocataire.getId()+"";
            tableau [i][1]=leLocataire.getNom();
            tableau [i][2]=leLocataire.getPrenom();
            tableau [i][3]=leLocataire.getAge()+"";
            tableau [i][4]=leLocataire.getAnciennete().getDateEcrite();
            tableau [i][5]=leLocataire.getMail();
            tableau [i][6]=leLocataire.getTelephone();
        }
    }
    
    public void modeleUtilisateurs(){
        String[] tabEntetes = {"ID","Login", "Catégorie"};
        this.setEntetes(tabEntetes);
        
        //Récupération des utilisateurs dans une ArrayList
        Utilisateurs_DAO lesUtilisateurs= new Utilisateurs_DAO(connBdd);
        liste=(ArrayList<Utilisateur>)lesUtilisateurs.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new String[liste.size()][3];
        for(int i=0; i<liste.size();i++){
            Utilisateur user=(Utilisateur) liste.get(i);
            tableau [i][0]=user.getId()+"";
            tableau [i][1]=user.getLogin();
            tableau [i][2]=user.getCat()+"";
        }
    }
    
    public void modeleMessages(){
        String[] tabEntetes = {"ID","Contenu", "Date d'écriture"};
        this.setEntetes(tabEntetes);
        
        //Récupération des messages dans une ArrayList
        Message_DAO lesMessages= new Message_DAO(connBdd);
        liste=(ArrayList<Message>)lesMessages.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new String[liste.size()][3];
        for(int i=0; i<liste.size();i++){
            Message unMessage=(Message) liste.get(i);
            tableau [i][0]=unMessage.getId()+"";
            tableau [i][1]=unMessage.getMessage();
            tableau [i][2]=unMessage.getDateEcriture().getDateEcrite()+"";
        }
    }
    
    public void modeleMaison(){
        String[] tabEntetes = {"ID","Adresse"};
        this.setEntetes(tabEntetes);
        Maison_DAO lesMaison = new Maison_DAO(connBdd);
        liste = (ArrayList<Maison>)lesMaison.getAll();
        this.tableau = new String[liste.size()][2];
        for(int i=0; i<liste.size();i++){
            Maison uneMaison = (Maison) liste.get(i);
            tableau [i][0]= uneMaison.getID()+"";
            tableau [i][1]= uneMaison.getAdresse();
        }
    }
    
    public void modeleAppartement(){
        String[] tabEntetes = {"ID","Adresse", "Numéro étage", "Numéro appartement"};
        this.setEntetes(tabEntetes);
        Appartement_DAO lesApparts = new Appartement_DAO(connBdd);
        liste = (ArrayList<Appartement>)lesApparts.getAll();
        this.tableau = new String[liste.size()][4];
        for(int i=0; i<liste.size();i++){
            Appartement unAppartement = (Appartement) liste.get(i);
            tableau [i][0]= unAppartement.getID()+"";
            tableau [i][1]= unAppartement.getAdresse()+"";
            tableau [i][2]= unAppartement.getEtage()+"";
            tableau [i][3]= unAppartement.getApart()+"";
        }
    }
    
    public void modeleCampagne(){
        String[] tabEntetes = {"ID","Titre","Date de début", "Heure", "Date de fin", "Fréquence", "ID Utilisateur"};
        this.setEntetes(tabEntetes);
        Campagne_DAO lesCampagnes = new Campagne_DAO(connBdd);
        liste = (ArrayList<Campagne>)lesCampagnes.getAll();
        this.tableau = new String[liste.size()][7];
        for(int i=0; i<liste.size();i++){
            Campagne uneCampagne = (Campagne) liste.get(i);
            tableau [i][0]= uneCampagne.getId()+"";
            tableau [i][1]= uneCampagne.getTitre();
            tableau [i][2]= uneCampagne.getDateDebut().getDateEcrite();
            tableau [i][3]= uneCampagne.getHeure().getTimeSQL();
            tableau [i][4]= uneCampagne.getDateFin().getDateEcrite();
            tableau [i][5]= uneCampagne.getFrequence();
            tableau [i][6]= uneCampagne.getUtilisateur().getId()+"";
        }
    }
    
    public void supprimer(int n){
        switch(this.donnees){
            case "locataires" -> {
                Locataire_DAO locDao=new Locataire_DAO(connBdd);
                locDao.delete((Locataire) this.getSelection(n));
            }
            case "utilisateurs" -> {
                Utilisateurs_DAO userDao=new Utilisateurs_DAO(connBdd);
                userDao.delete((Utilisateur) this.getSelection(n));
            }
            case "appartements" -> {
                Appartement_DAO appartementDao=new Appartement_DAO(connBdd);
                appartementDao.delete((Appartement) this.getSelection(n));
            }
            case "maisons" -> {
                Maison_DAO maisonDao=new Maison_DAO(connBdd);
                maisonDao.delete((Maison) this.getSelection(n));
            }
        }
        this.liste.remove(n);
    }
}