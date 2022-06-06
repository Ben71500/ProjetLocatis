/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import Locatis.Appartement;
import Locatis.Locataire;
import com.sun.jdi.connect.spi.Connection;
import java.util.*;

/**
 *
 * @author Benjamin
 */
public class Modele_Association {
    private ArrayList<Locataire> listeLocataire = new ArrayList<>();
    private ArrayList<Appartement> listeAppartement = new ArrayList<>();
    private java.sql.Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    public Modele_Association(){
        
    }
    
    public ArrayList<Locataire> getListeLocataire(){
        Locataire_DAO loca = new Locataire_DAO(connBdd);
        listeLocataire = (ArrayList<Locataire>)loca.getAll();
        return listeLocataire;
    }
    
    public ArrayList<Appartement> getListeAppartement(){
        return listeAppartement;
    }
}
