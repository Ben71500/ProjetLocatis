package MVC;

import DAO.*;
import Locatis.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class  Modele_Gestion {
    
    private String[][] tableau;
    private String[] entetes;
    private List liste;
    private String donnees;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private DAO dao;

    /**
     * Constructeur du modèle
     */    
    public Modele_Gestion(String lesDonnees) {
        this.donnees = lesDonnees;
        switch(this.donnees){
            case "locataires" -> dao = new Locataire_DAO(this.connBdd);
            case "utilisateurs" -> dao = new Utilisateurs_DAO(this.connBdd);
            case "messages" -> dao = new Message_DAO(this.connBdd);
            case "appartements" -> dao = new Appartement_DAO(this.connBdd);
            case "maisons" -> dao = new Maison_DAO(this.connBdd);
            case "campagnes" -> dao = new Campagne_DAO(this.connBdd);
            case "liste" -> dao = new ListeDeDiffusion_DAO(this.connBdd);
        }
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
        // Patch appliqué car probléme avec les button radio 
        switch(this.donnees){
            case "locataires": dao = new Locataire_DAO(this.connBdd); modeleLocataires(); break;
            case "utilisateurs": dao = new Utilisateurs_DAO(this.connBdd); modeleUtilisateurs(); break;
            case "messages": dao = new Message_DAO(this.connBdd); modeleMessages(); break;
            case "appartements": dao = new Appartement_DAO(this.connBdd); modeleAppartement(); break;
            case "maisons": dao = new Maison_DAO(this.connBdd); modeleMaison(); break;
            case "campagnes": dao = new Campagne_DAO(this.connBdd); modeleCampagne(); break;
            case "liste": dao = new ListeDeDiffusion_DAO(this.connBdd); modeleListe(); break;
        }
    }

    public void modeleLocataires(){
        String[] tabEntetes = {"ID","Nom", "Prénom", "Age", "Ancienneté", "Mail", "Téléphone"};
        this.setEntetes(tabEntetes);
        
        //Récupération des locataires dans une ArrayList
        //Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        liste=(ArrayList<Locataire>)dao.getAll();
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
        //Utilisateurs_DAO lesUtilisateurs= new Utilisateurs_DAO(connBdd);
        liste=(ArrayList<Utilisateur>)dao.getAll();
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
        //Message_DAO lesMessages= new Message_DAO(connBdd);
        liste=(ArrayList<Message>)dao.getAll();
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
        //Maison_DAO lesMaison = new Maison_DAO(connBdd);
        liste = (ArrayList<Maison>)dao.getAll();
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
        //Appartement_DAO lesApparts = new Appartement_DAO(connBdd);
        liste = (ArrayList<Appartement>)dao.getAll();
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
        //Campagne_DAO lesCampagnes = new Campagne_DAO(connBdd);
        liste = (ArrayList<Campagne>)dao.getAll();
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
    
    public void modeleListe(){
        String[] tabEntetes = {"ID","Nom"};
        this.setEntetes(tabEntetes);
        
        //Récupération des listes dans une ArrayList
        liste=(ArrayList<ListeDeDiffusion>)dao.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new String[liste.size()][2];
        for(int i=0; i<liste.size();i++){
            ListeDeDiffusion listeDeDiffusion=(ListeDeDiffusion) liste.get(i);
            tableau [i][0]=listeDeDiffusion.getId()+"";
            tableau [i][1]=listeDeDiffusion.getNom();
        }
    }
    
    public void insererViaCSVLocataire(ArrayList<Locataire> loca_liste){
        Locataire_DAO loca_dao = new Locataire_DAO(connBdd);
        for (int i = 0; i < loca_liste.size(); i++){
            loca_dao.create(loca_liste.get(i));
        }
    }
    
    public void insererViaCSVMaison(ArrayList<Maison> maison_liste){
        Maison_DAO maison_dao = new Maison_DAO(connBdd);
        for (int i = 0; i < maison_liste.size(); i++){
            maison_dao.create(maison_liste.get(i));
        }
    }
    
    public void insererViaCSVAppartement(ArrayList<Appartement> appartement_liste){
        Appartement_DAO appartement_dao = new Appartement_DAO(connBdd);
        for (int i = 0; i < appartement_liste.size(); i++){
            appartement_dao.create(appartement_liste.get(i));
        }
    }
    
    //à tester
    public void insererViaCSV(List liste_csv){
        for (int i = 0; i < liste_csv.size(); i++){
            dao.create(liste_csv.get(i));
        }
    }
    
    public void supprimer(int n){
        /*switch(this.donnees){
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
            case "campagnes" -> {
                Campagne_DAO campagneDao=new Campagne_DAO(connBdd);
                campagneDao.delete((Campagne) this.getSelection(n));
            }
        }*/
        dao.delete(this.getSelection(n));
        this.liste.remove(n);
    }
}