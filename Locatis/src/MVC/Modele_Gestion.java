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
    private Utilisateur utilisateur;

    /**
     * Constructeur du modèle
     */    
    public Modele_Gestion(String lesDonnees, Utilisateur user) {
        this.donnees = lesDonnees;
        this.utilisateur = user;
        switch(this.donnees){
            case "locataire" -> dao = new Locataire_DAO(this.connBdd);
            case "utilisateur" -> dao = new Utilisateurs_DAO(this.connBdd);
            case "appartement" -> dao = new Appartement_DAO(this.connBdd);
            case "maison" -> dao = new Maison_DAO(this.connBdd);
            case "campagne" -> dao = new Campagne_DAO(this.connBdd);
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
            case "locataire": dao = new Locataire_DAO(this.connBdd); modeleLocataires(); break;
            case "utilisateur": dao = new Utilisateurs_DAO(this.connBdd); modeleUtilisateurs(); break;
            case "appartement": dao = new Appartement_DAO(this.connBdd); modeleAppartement(); break;
            case "maison": dao = new Maison_DAO(this.connBdd); modeleMaison(); break;
            case "campagne": dao = new Campagne_DAO(this.connBdd); modeleCampagne(); break;
            case "liste": dao = new ListeDeDiffusion_DAO(this.connBdd); modeleListe(); break;
        }
    }

    public void modeleLocataires(){
        String[] tabEntetes = {"ID","Nom", "Prénom", "Age", "Date de naissance", "Mail", "Téléphone"};
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
            tableau [i][4]=leLocataire.getDateDeNaissance().getDateEcrite();
            tableau [i][5]=leLocataire.getMail();
            tableau [i][6]=leLocataire.getTelephone();
        }
    }
    
    public void modeleUtilisateurs(){
        String[] tabEntetes = {"ID","Login", "Catégorie"};
        this.setEntetes(tabEntetes);
        
        //Récupération des utilisateurs dans une ArrayList
        
        Utilisateurs_DAO userDAO = (Utilisateurs_DAO) dao ;
        switch(this.utilisateur.getCat()){
            case "ges1" ->
                liste=(ArrayList<Utilisateur>)userDAO.getAllUtilisateurs();
            case "ges2" ->
                liste=(ArrayList<Utilisateur>)userDAO.getAllUtilisateursEtGestionnaires();
            case "adm", "ges3" ->
                liste=(ArrayList<Utilisateur>)userDAO.getAll();
        }
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new String[liste.size()][3];
        for(int i=0; i<liste.size();i++){
            Utilisateur user=(Utilisateur) liste.get(i);
            tableau [i][0]=user.getId()+"";
            tableau [i][1]=user.getLogin();
            tableau [i][2]=user.getCat()+"";
        }
    }
    
    /*public void modeleMessages(){
        String[] tabEntetes = {"ID","Objet", "Contenu"};
        this.setEntetes(tabEntetes);
        
        //Récupération des messages dans une ArrayList
        //Message_DAO lesMessages= new Message_DAO(connBdd);
        liste=(ArrayList<Message>)dao.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new String[liste.size()][3];
        for(int i=0; i<liste.size();i++){
            Message unMessage=(Message) liste.get(i);
            tableau [i][0] = unMessage.getId()+"";
            tableau [i][1] = unMessage.getObjet();
            tableau [i][2] = unMessage.getMessage();
        }
    }*/
    
    public void modeleMaison(){
        String[] tabEntetes = {"ID","Numero Rue", "Nom Rue", "Ville", "Code Postal"};
        this.setEntetes(tabEntetes);
        //Maison_DAO lesMaison = new Maison_DAO(connBdd);
        liste = (ArrayList<Maison>)dao.getAll();
        this.tableau = new String[liste.size()][6];
        for(int i=0; i<liste.size();i++){
            Maison uneMaison = (Maison) liste.get(i);
            tableau [i][0]= uneMaison.getID()+"";
            tableau [i][1]= uneMaison.getNumeroRue();
            tableau [i][2]= uneMaison.getNomRue();
            tableau [i][3]= uneMaison.getVille();
            tableau [i][4]= uneMaison.getCodePostal();
        }
    }
    
    public void modeleAppartement(){
        String[] tabEntetes = {"ID","Numero Rue", "Nom Rue", "Ville", "Code Postal", "Numéro étage", "Numéro appartement"};
        this.setEntetes(tabEntetes);
        //Appartement_DAO lesApparts = new Appartement_DAO(connBdd);
        liste = (ArrayList<Appartement>)dao.getAll();
        this.tableau = new String[liste.size()][8];
        for(int i=0; i<liste.size();i++){
            Appartement unAppartement = (Appartement) liste.get(i);
            tableau [i][0]= unAppartement.getID()+"";
            tableau [i][1]= unAppartement.getNumeroRue();
            tableau [i][2]= unAppartement.getNomRue();
            tableau [i][3]= unAppartement.getVille();
            tableau [i][4]= unAppartement.getCodePostal();
            tableau [i][5]= unAppartement.getEtage()+"";
            tableau [i][6]= unAppartement.getApart()+"";
        }
    }
    
    public void modeleCampagne(){
        String[] tabEntetes = {"ID","Titre","Date de début", "Heure", "Date de fin", "Fréquence", "Objet", "Contenu", "ID Utilisateur"};
        this.setEntetes(tabEntetes);
        //Campagne_DAO lesCampagnes = new Campagne_DAO(connBdd);
        liste = (ArrayList<Campagne>)dao.getAll();
        this.tableau = new String[liste.size()][9];
        for(int i=0; i<liste.size();i++){
            Campagne uneCampagne = (Campagne) liste.get(i);
            tableau [i][0]= uneCampagne.getId()+"";
            tableau [i][1]= uneCampagne.getTitre();
            tableau [i][2]= uneCampagne.getDateDebut().getDateEcrite();
            tableau [i][3]= uneCampagne.getHeure().getTimeSQL();
            tableau [i][4]= uneCampagne.getDateFin().getDateEcrite();
            tableau [i][5]= uneCampagne.getFrequence();
            tableau [i][6] = uneCampagne.getObjetMail();
            tableau [i][7] = uneCampagne.getMessageMail();
            tableau [i][8]= uneCampagne.getUtilisateur().getId()+"";
            
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
    
    public ArrayList<Campagne> getListeCampagne(int idLocataire){
        ListeDeDiffusion_DAO listDao = new ListeDeDiffusion_DAO(connBdd);
        ArrayList<Integer> listIdListDiff = new ArrayList<>();
        listIdListDiff = (ArrayList<Integer>) listDao.searchListLocataireByIdLocataire(idLocataire);
        Campagne_DAO cmpDao = new Campagne_DAO(connBdd);
        return (ArrayList <Campagne>) cmpDao.getIdCampagneByListeDeDiffusionBy(listIdListDiff);
    }
}