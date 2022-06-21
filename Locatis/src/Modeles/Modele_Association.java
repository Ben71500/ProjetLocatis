package Modeles;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Habiter_DAO;
import DAO.Locataire_DAO;
import DAO.Maison_DAO;
import Objets_Locatis.Appartement;
import Objets_Locatis.Locataire;
import Objets_Locatis.Maison;
import java.util.*;

/**
 * Classe modèle de l'interface permettant d'associer un logement et un locataire
 * @author Benjamin Mathilde
 */
public class Modele_Association {
    
    private ArrayList<Locataire> listeLocataire = new ArrayList<>();
    private ArrayList<Appartement> listeAppartement = new ArrayList<>();
    private ArrayList<Maison> listeMaison = new ArrayList<>();
    private java.sql.Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    /**
     * Constructeur du modèle
     */
    public Modele_Association(){
        
    }
    
    /**
     *
     * @return la liste de tous les locataires
     */
    public ArrayList<Locataire> getListeLocataire(){
        Locataire_DAO loca = new Locataire_DAO(connBdd);
        listeLocataire = (ArrayList<Locataire>)loca.getAll();
        return listeLocataire;
    }
    
    /**
     *
     * @return la liste de tous les appartements
     */
    public ArrayList<Appartement> getListeAppartement(){
        Appartement_DAO appart = new Appartement_DAO(connBdd);
        listeAppartement = (ArrayList<Appartement>)appart.getAll();
        return listeAppartement;
    }
    
    /**
     *
     * @return la liste de toutes les maisons
     */
    public ArrayList<Maison> getListMaison(){
        Maison_DAO maison = new Maison_DAO(connBdd);
        listeMaison = (ArrayList<Maison>)maison.getAll();
        return listeMaison;
    }
    
    /**
     * Méthode qui retourne une liste d'appartements dans lesquels un locataire habitent
     * @param id : id du locataire
     * @return la liste des appartements
     */
    public ArrayList<Appartement> getListeAppartementByIdLocataire(int id){
        Habiter_DAO habiter = new Habiter_DAO(connBdd);
        listeAppartement = (ArrayList<Appartement>)habiter.getAppartementByIdLocataire(id);
        return listeAppartement;
    }
    
    /**
     * Méthode qui retourne une liste de maisons dans lesquelles un locataire habitent
     * @param id : id du locataire
     * @return la liste des maisons
     */
    public ArrayList<Maison> getListMaisonByIdLocataire(int id){
        Habiter_DAO habiter = new Habiter_DAO(connBdd);
        listeMaison = (ArrayList<Maison>)habiter.getMaisonByIdLocataire(id);
        return listeMaison;
    }
    
    /**
     * Méthode qui permet de définir qu'un locataire a un logement
     * @param idBatiment : id de l'appartement ou de la maison
     * @param loca : le locataire
     */
    public void insertHabiter(int idBatiment, Locataire loca){
        Habiter_DAO nouvelHabitation=new Habiter_DAO(connBdd);
        nouvelHabitation.create(idBatiment, loca.getId());
    }
    
    /**
     * Méthode qui permet de définir qu'un locataire n'habite plus dans un logement
     * @param idLogement : id de l'appartement ou de la maison
     * @param loca : le locataire
     */
    public void removeHabiter(int idLogement, Locataire loca){
        Habiter_DAO nouvelHabitation=new Habiter_DAO(connBdd);
        nouvelHabitation.delete(idLogement, loca.getId());
    }
}