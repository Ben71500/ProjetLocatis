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
import com.sun.jdi.connect.spi.Connection;
import java.util.*;

/**
 *
 * @author Benjamin
 */
public class Modele_Association {
    private ArrayList<Locataire> listeLocataire = new ArrayList<>();
    private ArrayList<Appartement> listeAppartement = new ArrayList<>();
    private ArrayList<Maison> listeMaison = new ArrayList<>();
    private java.sql.Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    public Modele_Association(){
        
    }
    
    public ArrayList<Locataire> getListeLocataire(){
        Locataire_DAO loca = new Locataire_DAO(connBdd);
        listeLocataire = (ArrayList<Locataire>)loca.getAll();
        return listeLocataire;
    }
    
    public ArrayList<Appartement> getListeAppartement(){
        Appartement_DAO appart = new Appartement_DAO(connBdd);
        listeAppartement = (ArrayList<Appartement>)appart.getAll();
        return listeAppartement;
    }
    
    public ArrayList<Appartement> getListeAppartementByIdLocataire(int id){
        Habiter_DAO habiter = new Habiter_DAO(connBdd);
        listeAppartement = (ArrayList<Appartement>)habiter.getAppartementByIdLocataire(id);
        return listeAppartement;
    }
    
    public ArrayList<Maison> getListMaison(){
        Maison_DAO maison = new Maison_DAO(connBdd);
        listeMaison = (ArrayList<Maison>)maison.getAll();
        return listeMaison;
    }
    
    public ArrayList<Maison> getListMaisonByIdLocataire(int id){
        Habiter_DAO habiter = new Habiter_DAO(connBdd);
        listeMaison = (ArrayList<Maison>)habiter.getMaisonByIdLocataire(id);
        return listeMaison;
    }
    
    public void insertHabiter(int idBatiment, Locataire loca){
        Habiter_DAO nouvelHabitation=new Habiter_DAO(connBdd);
        //Habiter habitation = new Habiter(idBatiment, loca.getId());
        //nouvelHabitation.create(habitation);
        nouvelHabitation.create(idBatiment, loca.getId());
    }
    
    public void removeHabiter(int idLogement, Locataire loca){
        Habiter_DAO nouvelHabitation=new Habiter_DAO(connBdd);
        /*Habiter habitation = new Habiter(loca.getId(), idLogement);
        nouvelHabitation.delete(habitation);*/
        nouvelHabitation.delete(idLogement, loca.getId());
    }
}
