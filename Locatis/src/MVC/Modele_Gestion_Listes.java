/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import DAO.Appartement_DAO;
import DAO.Campagne_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import DAO.Maison_DAO;
import DAO.Message_DAO;
import DAO.Utilisateurs_DAO;
import Locatis.Appartement;
import Locatis.Campagne;
import Locatis.Locataire;
import Locatis.Maison;
import Locatis.Message;
import Locatis.Utilisateur;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathi
 */
public class Modele_Gestion_Listes {
    
    private Object[][] tableau;
    private String[] entetes;
    private List liste;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());

    /**
     * Constructeur du modèle
     */    
    public Modele_Gestion_Listes() {
        String[] tabEntetes = {"Case","ID","Nom", "Prénom", "Age", "Ancienneté", "Mail", "Téléphone"};
        this.setEntetes(tabEntetes);
        
        //Récupération des locataires dans une ArrayList
        Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        liste=(ArrayList<Locataire>)lesLocataires.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        tableau = new Object[liste.size()][8];
        for(int i=0; i<liste.size();i++){
            Locataire leLocataire = (Locataire) liste.get(i);
            tableau [i][0]= false;
            tableau [i][1]=leLocataire.getId();
            tableau [i][2]=leLocataire.getNom();
            tableau [i][3]=leLocataire.getPrenom();
            tableau [i][4]=leLocataire.getAge();
            tableau [i][5]=leLocataire.getAnciennete().getDateEcrite();
            tableau [i][6]=leLocataire.getMail();
            tableau [i][7]=leLocataire.getTelephone();
        }
    }

    public void setEntetes(String[] entetes) {
        this.entetes = entetes;
    }

    public Object[][] getTableau() {
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
}