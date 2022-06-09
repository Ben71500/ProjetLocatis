/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Habiter_DAO;
import DAO.Locataire_DAO;
import DAO.Maison_DAO;
import Locatis.Appartement;
import Locatis.Habiter;
import Locatis.Locataire;
import Locatis.Maison;
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
    
    public ArrayList<Maison> getListMaison(){
        Maison_DAO maison = new Maison_DAO(connBdd);
        listeMaison = (ArrayList<Maison>)maison.getAll();
        return listeMaison;
    }
    
    public void insertHabiter(Appartement appart, Locataire loca){
        Habiter_DAO nouvelHabitation=new Habiter_DAO(connBdd);
        Habiter habitation = new Habiter(loca.getId(), appart.getID());
        nouvelHabitation.create(habitation);
    }
    
    public void removeHabiter(Appartement appart, Locataire loca){
        Habiter_DAO nouvelHabitation=new Habiter_DAO(connBdd);
        Habiter habitation = new Habiter(loca.getId(), appart.getID());
        nouvelHabitation.delete(habitation);
    }
}
