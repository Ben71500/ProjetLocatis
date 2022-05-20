package MVC;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.DAO;
import DAO.ListeDeDiffusion_DAO;
import DAO.Locataire_DAO;
import DAO.Utilisateurs_DAO;
import Locatis.Batiment;
import Locatis.ListeDeDiffusion;
import Locatis.Locataire;
import Locatis.Utilisateur;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Modele_Ajout_Listes {
    
    private Object[][] tableau;
    private String[] entetes;
    private List liste;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private ArrayList<Integer> listeCasesCochees = new ArrayList<>();
    private String donnees;
    /*private DAO dao;*/

    /**
     * Constructeur du modèle
     */    
    public Modele_Ajout_Listes(String lesDonnees) {
        this.donnees = lesDonnees;
        choisirModele();
    }
    
    public void choisirModele(){
        switch(this.donnees){
            case "locataire" -> modeleLocataires();
            case "utilisateur" -> modeleUtilisateurs();
        }
    }
    
    public void modeleLocataires(){
        String[] tabEntetes = {"Case","ID","Nom", "Prénom", "Age", "Ancienneté", "Mail", "Téléphone","Logements"};
        this.setEntetes(tabEntetes);
        
        //Récupération des locataires dans une ArrayList
        Locataire_DAO locataires = new Locataire_DAO(this.connBdd);
        liste=(ArrayList<Locataire>)locataires.getAll();
        
        //On convertit cette ArrayList en tableau à deux dimensions
        tableau = new Object[liste.size()][9];
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
            tableau [i][8]=(List<Batiment>)locataires.getLocation(leLocataire.getId());
        }
    }
    
    public void modeleUtilisateurs(){
        String[] tabEntetes = {"Case", "ID","Login", "Catégorie"};
        this.setEntetes(tabEntetes);
        
        //Récupération des utilisateurs dans une ArrayList
        Utilisateurs_DAO utilisateurs= new Utilisateurs_DAO(connBdd);
        liste=(ArrayList<Utilisateur>)utilisateurs.getAll();
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new Object[liste.size()][4];
        for(int i=0; i<liste.size();i++){
            Utilisateur user=(Utilisateur) liste.get(i);
            tableau [i][0]= false;
            tableau [i][1]=user.getId();
            tableau [i][2]=user.getLogin();
            tableau [i][3]=user.getCat();
        }
    }
    
    public ArrayList<Integer> getListeCasesCochees() {
        return listeCasesCochees;
    }

    public void setDonnees(String donnees) {
        this.donnees = donnees;
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
    
    public void cocher(int ligne){
        int id=(int) tableau[ligne][1];
        if(listeCasesCochees.contains(id)){
            listeCasesCochees.remove((Object)id);
        }else{
            listeCasesCochees.add(id);
        }
    }
    
    public void cocherTout(){
        for (Object[] tableau1 : tableau) {
            tableau1[0] = true;
            if (!listeCasesCochees.contains((Integer)tableau1[1])) {
                listeCasesCochees.add((Integer) tableau1[1]);
            }
        }
    }
    
    public void decocherTout(){
        for (Object[] tableau1 : tableau) {
            tableau1[0] = false;
            if (listeCasesCochees.contains((Integer)tableau1[1])) {
                listeCasesCochees.remove((Object) tableau1[1]);
            }
        }
    }
    
    
    ///////////////////
    public void getTri(String categorie, String signe, String nombre){
        String requete = "Select * from locataire where "+categorie+" "+signe+" "+nombre;
        executerRequeteLocataire(requete);
    }
    
    public void getAll(){
        String requete = "Select * from "+this.donnees;
        switch(this.donnees){
            case "locataire" : executerRequeteLocataire(requete);
            case "utilisateur" : executerRequeteUtilisateur(requete);
        }
    }
    
    public void trierPar(String categorie){
        String requete = "Select * from "+this.donnees;
        if(!categorie.equals("Tous") && !categorie.equals("")){
            requete += " order by ";
            switch(categorie){
                case "ID" -> requete+="ID_"+this.donnees;
                case "Nom" -> requete+="Nom";
                case "Prénom" -> requete+="Prenom";
                case "Age" -> requete+="Age";
                case "Ancienneté" -> requete+="Anciennete";
                case "Login" -> requete+="login";
                case "Catégorie" -> requete+="CAT";
            }
        }
        switch(this.donnees){
            case "locataire" : executerRequeteLocataire(requete);
            case "utilisateur" : executerRequeteUtilisateur(requete);
        }
    }
    
    public void executerRequeteLocataire(String requete){
        //Récupération des locataires dans une ArrayList
        Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        
        liste=(ArrayList<Locataire>)lesLocataires.getRequete(requete);
        //On convertit cette ArrayList en tableau à deux dimensions
        tableau = new Object[liste.size()][9];
        for(int i=0; i<liste.size();i++){
            Locataire leLocataire = (Locataire) liste.get(i);
            tableau [i][0]= listeCasesCochees.contains(leLocataire.getId());
            tableau [i][1]=leLocataire.getId();
            tableau [i][2]=leLocataire.getNom();
            tableau [i][3]=leLocataire.getPrenom();
            tableau [i][4]=leLocataire.getAge();
            tableau [i][5]=leLocataire.getAnciennete().getDateEcrite();
            tableau [i][6]=leLocataire.getMail();
            tableau [i][7]=leLocataire.getTelephone();
            tableau [i][8]=lesLocataires.getLocation(leLocataire.getId());
        }
    }
    
    public void executerRequeteUtilisateur(String requete){
        //Récupération des locataires dans une ArrayList
        Utilisateurs_DAO lesUtilisateurs= new Utilisateurs_DAO(this.connBdd);
        
        liste=(ArrayList<Utilisateur>)lesUtilisateurs.getRequete(requete);
        //On convertit cette ArrayList en tableau à deux dimensions
        this.tableau = new Object[liste.size()][4];
        for(int i=0; i<liste.size();i++){
            Utilisateur user=(Utilisateur) liste.get(i);
            tableau [i][0]= listeCasesCochees.contains(user.getId());
            tableau [i][1]=user.getId();
            tableau [i][2]=user.getLogin();
            tableau [i][3]=user.getCat();
        }
    }
    
    
    /*
    public Object getNouveauTableau(){
        Object[][] tab = new Object[liste.size()][8];
        for(int i=0; i<liste.size();i++){
            Locataire leLocataire = (Locataire) liste.get(i);
            tab [i][0]= tableau[i][0];
            tab [i][1]=leLocataire.getId();
            tab [i][2]=leLocataire.getNom();
            tab [i][3]=leLocataire.getPrenom();
            tab [i][4]=leLocataire.getAge();
            tab [i][5]=leLocataire.getAnciennete().getDateEcrite();
            tab[i][6]=leLocataire.getMail();
            tab [i][7]=leLocataire.getTelephone();
        }
        return tab;
    }*/
    
    /*public void ajouter(String nom){
        ListeDeDiffusion_DAO lesListes= new ListeDeDiffusion_DAO(this.connBdd);
        lesListes.create(new ListeDeDiffusion(0,nom,this.convertir()));
    }
    /*
    private <O> ArrayList<O> convertir(){
        ArrayList<O> listeLoca = new ArrayList<>();
        /*Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        for(int i=0;i<this.listeCasesCochees.size();i++)
            listeLoca.add(lesLocataires.selectById(this.listeCasesCochees.get(i)));*/
        /*return listeLoca;
    }*/
}