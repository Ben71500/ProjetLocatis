package Modeles;

import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Appartement;
import Objets_Locatis.Maison;
import Objets_Locatis.Utilisateur;
import Objets_Locatis.Locataire;
import Objets_Locatis.Campagne;
import DAO.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe modèle de l'interface de gestion des données
 * @author Benjamin Mathilde
 */
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
     * @param lesDonnees : type de données
     * @param user : utilisateur qui utilise l'interface
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

    /**
     *
     * @return le tableau des données
     */
    public String[][] getTableau() {
        return tableau;
    }

    /**
     *
     * @return le tableau des entetes
     */
    public String[] getEntetes() {
        return entetes;
    }
    
    /**
     *
     * @param nb : le numéro de la ligne de l'objet
     * @return l'objet sélectionné
     */
    public Object getSelection(int nb){
        return liste.get(nb);
    }
    
    /**
     *
     * @param donnees : type de données
     */
    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }
    
    /**
     *
     * @param entetes : tableau d'entetes
     */
    public void setEntetes(String[] entetes) {
        this.entetes = entetes;
    }

    
    /**
     * Méthode qui permet de définir l'objet dao et de
     * définir le tableau des données en fonction du type des données
     */
    public void initialiser(){
        switch(this.donnees){
            case "locataire": dao = new Locataire_DAO(this.connBdd); modeleLocataires(); break;
            case "utilisateur": dao = new Utilisateurs_DAO(this.connBdd); modeleUtilisateurs(); break;
            case "appartement": dao = new Appartement_DAO(this.connBdd); modeleAppartement(); break;
            case "maison": dao = new Maison_DAO(this.connBdd); modeleMaison(); break;
            case "campagne": dao = new Campagne_DAO(this.connBdd); modeleCampagne(); break;
            case "liste": dao = new ListeDeDiffusion_DAO(this.connBdd); modeleListe(); break;
        }
    }

    /**
     * Méthode qui permet de définir le tableau des locataires
     */
    public void modeleLocataires(){
        String[] tabEntetes = {"ID","Nom", "Prénom", "Age", "Date de naissance", "Mail", "Téléphone"};
        this.setEntetes(tabEntetes);
        //Récupération des locataires dans une ArrayList
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
    
    /**
     * Méthode qui permet de définir le tableau des utilisateurs
     */
    public void modeleUtilisateurs(){
        String[] tabEntetes = {"ID","Login", "Catégorie"};
        this.setEntetes(tabEntetes);
        //Récupération des utilisateurs dans une ArrayList
        Utilisateurs_DAO userDAO = (Utilisateurs_DAO) dao ;
        //Les droits ne sont pas les mêmes en fonction de la catégorie de l'utilisateur connecté
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
    
    /**
     * Méthode qui permet de définir le tableau des maisons
     */
    public void modeleMaison(){
        String[] tabEntetes = {"ID","Numero Rue", "Nom Rue", "Ville", "Code Postal"};
        this.setEntetes(tabEntetes);
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
    
    /**
     * Méthode qui permet de définir le tableau des appartements
     */
    public void modeleAppartement(){
        String[] tabEntetes = {"ID","Numero Rue", "Nom Rue", "Ville", "Code Postal", "Numéro étage", "Numéro appartement"};
        this.setEntetes(tabEntetes);
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
    
    /**
     * Méthode qui permet de définir le tableau des campagnes
     */
    public void modeleCampagne(){
        String[] tabEntetes = {"ID","Titre","Date de début", "Heure", "Date de fin", "Fréquence", "Objet", "Contenu", "ID Utilisateur"};
        this.setEntetes(tabEntetes);
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
    
    /**
     * Méthode qui permet de définir le tableau des listes de diffusion
     */
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
    
    /**
     * Méthode qui permet d'insérer des données via CSV
     * @param liste_csv : liste de données
     */
    public void insererViaCSV(List liste_csv){
        for (int i = 0; i < liste_csv.size(); i++){
            dao.create(liste_csv.get(i));
        }
    }
    
    /**
     * Méthode qui permet de supprimer un élément
     * @param n : numéro de la ligne de l'élément
     */
    public void supprimer(int n){
        dao.delete(this.getSelection(n));
        this.liste.remove(n);
    }
}