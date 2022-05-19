package MVC;

import DAO.*;
import java.sql.Connection;

public class Modele_AjoutModif{
    
    private String donnees;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private DAO dao;

    /**
     * Constructeur du modèle
     */    
    public Modele_AjoutModif(String lesDonnees) {
        this.donnees = lesDonnees;
        switch(lesDonnees.toUpperCase()){
            case "LOCATAIRES" ->
                dao = new Locataire_DAO(connBdd);
            case "UTILISATEURS" ->
                dao = new Utilisateurs_DAO(connBdd);
            case "APPARTEMENTS" ->
                dao = new Appartement_DAO(connBdd);
            case "MAISONS" ->
                dao = new Maison_DAO(connBdd);
            case "MESSAGES" ->
                dao = new Message_DAO(connBdd);
            case "CAMPAGNES" ->
                dao = new Campagne_DAO(connBdd);
        }
    }
    
    public <O> void  ajouter(O obj){
        dao.create(obj);
    }
    
    public <O> void modifier(O obj){
        dao.update(obj);
    }
}