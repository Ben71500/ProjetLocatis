package MVC;

import DAO.*;
import Locatis.ListeDeDiffusion;
import java.sql.Connection;
import java.util.ArrayList;

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
    
    public <O> void  ajouter(O obj){
        dao.create(obj);
    }
    
    public <O> void modifier(O obj){
        dao.update(obj);
    }
    
    /*public ArrayList<String> retournerListesDeDiffusion(){
        DAO listeDiffusionDAO = new ListeDeDiffusion_DAO(this.connBdd);
        ArrayList<ListeDeDiffusion> listesDeDiffusion = (ArrayList<ListeDeDiffusion>) listeDiffusionDAO.getAll();
        ArrayList<String> listeNomsListes = new ArrayList<>();
        for(int i=0;i<listesDeDiffusion.size();i++)
            listeNomsListes.add(listesDeDiffusion.get(i).getNom());
        return listeNomsListes;
    }*/
    
    public ArrayList<ListeDeDiffusion> retournerListesDeDiffusion(){
        DAO listeDiffusionDAO = new ListeDeDiffusion_DAO(this.connBdd);
        ArrayList<ListeDeDiffusion> listesDeDiffusion = (ArrayList<ListeDeDiffusion>) listeDiffusionDAO.getAll();
        /*ArrayList<String> listeNomsListes = new ArrayList<>();
        for(int i=0;i<listesDeDiffusion.size();i++)
            listeNomsListes.add(listesDeDiffusion.get(i).getNom());
        return listeNomsListes;*/
        return listesDeDiffusion;
    }
    
    /*public ArrayList<ListeDeDiffusion> retournerListesDeDiffusionSelectionnees(){
        DAO listeDiffusionDAO = new ListeDeDiffusion_DAO(this.connBdd);
        ArrayList<ListeDeDiffusion> listesDeDiffusion = (ArrayList<ListeDeDiffusion>) listeDiffusionDAO.getAll();
        ArrayList<String> listeNomsListes = new ArrayList<>();
        for(int i=0;i<listesDeDiffusion.size();i++)
            listeNomsListes.add(listesDeDiffusion.get(i).getNom());
        return listeNomsListes;
    }*/
}