package Modeles;

import DAO.*;
import Objets_Locatis.ListeDeDiffusion;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Classe modèle de l'interface d'ajouts et de modifications
 * @author Benjamin Mathilde
 */
public class Modele_AjoutModif{
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private DAO dao;

    /**
     * Constructeur du modèle
     * @param lesDonnees : type de donnéées
     */    
    public Modele_AjoutModif(String lesDonnees) {
        switch(lesDonnees.toUpperCase()){
            case "LOCATAIRE" ->
                dao = new Locataire_DAO(connBdd);
            case "UTILISATEUR" ->
                dao = new Utilisateurs_DAO(connBdd);
            case "APPARTEMENT" ->
                dao = new Appartement_DAO(connBdd);
            case "MAISON" ->
                dao = new Maison_DAO(connBdd);
            case "CAMPAGNE" ->
                dao = new Campagne_DAO(connBdd);
        }
    }
    
    /**
     * Méthode qui permet d'appeler la méthode create des objets DAO
     * @param <O> : représente la classe des objets Java à manipuler
     * @param obj : objet à ajouter
     */
    public <O> void  ajouter(O obj){
        dao.create(obj);
    }
    
    /**
     * Méthode qui permet d'appeler la méthode update des objets DAO
     * @param <O> : représente la classe des objets Java à manipuler
     * @param obj : objet à modifier
     */
    public <O> void modifier(O obj){
        dao.update(obj);
    }
    
    /**
     * Méthode qui permet de récupérer les listes de diffusion pour les campagnes
     * @return la liste des listes de diffusion
     */
    public ArrayList<ListeDeDiffusion> retournerListesDeDiffusion(){
        DAO listeDiffusionDAO = new ListeDeDiffusion_DAO(this.connBdd);
        ArrayList<ListeDeDiffusion> listesDeDiffusion = (ArrayList<ListeDeDiffusion>) listeDiffusionDAO.getAll();
        return listesDeDiffusion;
    }
}