package Modeles;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.DAO;
import DAO.ListeDeDiffusion_DAO;
import DAO.Locataire_DAO;
import DAO.Utilisateurs_DAO;
import Objets_Locatis.Batiment;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Locataire;
import Objets_Locatis.Personne;
import Objets_Locatis.Utilisateur;
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
    private Utilisateur utilisateur;
    /*private DAO dao;*/

    /**
     * Constructeur du modèle
     */    
    public Modele_Ajout_Listes(String lesDonnees, Utilisateur user) {
        this.donnees = lesDonnees;
        this.utilisateur = user;
        choisirModele();
    }
    
    public Modele_Ajout_Listes(String lesDonnees, Utilisateur user, ArrayList<Integer> listeId) {
        this.donnees = lesDonnees;
        this.utilisateur = user;
        this.listeCasesCochees = listeId;
        choisirModele();
    }
    
    public void choisirModele(){
        switch(this.donnees){
            case "locataire" -> modeleLocataires();
            case "utilisateur" -> modeleUtilisateurs();
        }
    }
    
    public void modeleLocataires(){
        String[] tabEntetes = {"Case","ID","Nom", "Prénom", "Age", "Date de naissance", "Mail", "Téléphone","Logements"};
        this.setEntetes(tabEntetes);
        
        //Récupération des locataires dans une ArrayList
        Locataire_DAO locataires = new Locataire_DAO(this.connBdd);
        liste=(ArrayList<Locataire>)locataires.getAll();
        
        //On convertit cette ArrayList en tableau à deux dimensions
        tableau = new Object[liste.size()][9];
        for(int i=0; i<liste.size();i++){
            Locataire leLocataire = (Locataire) liste.get(i);
            tableau [i][0]= listeCasesCochees.contains(leLocataire.getId());
            tableau [i][1]=leLocataire.getId();
            tableau [i][2]=leLocataire.getNom();
            tableau [i][3]=leLocataire.getPrenom();
            tableau [i][4]=leLocataire.getAge();
            tableau [i][5]=leLocataire.getDateDeNaissance().getDateEcrite();
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
        switch(this.utilisateur.getCat()){
            case "ges1" ->
                liste=(ArrayList<Utilisateur>)utilisateurs.getAllUtilisateurs();
            case "ges2" ->
                liste=(ArrayList<Utilisateur>)utilisateurs.getAllUtilisateursEtGestionnaires();
            case "adm", "ges3" ->
                liste=(ArrayList<Utilisateur>)utilisateurs.getAll();
        }
        
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
        String requete = "Select * from "+this.donnees+" where "+categorie+" "+signe+" "+nombre;
        executerRequeteLocataire(requete);
    }
    
    /*public void getAll(){
        String requete = "Select * from "+this.donnees;
        switch(this.donnees){
            case "locataire" : executerRequeteLocataire(requete);
            case "utilisateur" : executerRequeteUtilisateur(requete);
        }
    }*/
    
    public void trierPar(String categorie){
        String requete = "Select * from "+this.donnees;
        if(!categorie.equals("Tous") && !categorie.equals("")){
            requete += " order by ";
            switch(categorie){
                case "ID" -> requete+="ID_"+this.donnees;
                case "Nom" -> requete+="Nom";
                case "Prénom" -> requete+="Prenom";
                case "Age" -> requete+="Age";
                case "Date de naissance" -> requete+="DateDeNaissance";
                case "Login" -> requete+="login";
                case "Catégorie" -> requete+="CAT";
            }
        }
        switch(this.donnees){
            case "locataire" -> executerRequeteLocataire(requete);
            case "utilisateur" -> executerRequeteUtilisateur(requete);
        }
    }
    
    public void executerRequeteLocataire(String requete){
        //Récupération des locataires dans une ArrayList
        String[] tabEntetes = {"Case","ID","Nom", "Prénom", "Age", "Date de naissance", "Mail", "Téléphone","Logements"};
        this.setEntetes(tabEntetes);
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
            tableau [i][5]=leLocataire.getDateDeNaissance().getDateEcrite();
            tableau [i][6]=leLocataire.getMail();
            tableau [i][7]=leLocataire.getTelephone();
            tableau [i][8]=lesLocataires.getLocation(leLocataire.getId());
        }
    }
    
    public void executerRequeteUtilisateur(String requete){
        String[] tabEntetes = {"Case", "ID","Login", "Catégorie"};
        this.setEntetes(tabEntetes);
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
            tab [i][5]=leLocataire.getDateDeNaissance().getDateEcrite();
            tab[i][6]=leLocataire.getMail();
            tab [i][7]=leLocataire.getTelephone();
        }
        return tab;
    }*/
    
    public void ajouter(String nom){
        ListeDeDiffusion_DAO lesListes= new ListeDeDiffusion_DAO(this.connBdd);
        lesListes.create(new ListeDeDiffusion(0,nom,this.convertirEnListePersonnes()));
        /*switch(this.donnees){
            case "locataire" -> lesListes.create(new ListeDeDiffusion(0,nom,this.convertirEnListeLocataires()));
            case "utilisateur" -> lesListes.create(new ListeDeDiffusion(0,nom,this.convertirEnListeUtilisateurs()));
        }*/
    }
    
    public void modifier(ListeDeDiffusion listeDiffusion){
        ListeDeDiffusion_DAO lesListes= new ListeDeDiffusion_DAO(this.connBdd);
        listeDiffusion.setListe(this.convertirEnListePersonnes());
        /*switch(this.donnees){
            case "locataire" -> listeDiffusion.setListe(this.convertirEnListeLocataires());
            case "utilisateur" -> listeDiffusion.setListe(this.convertirEnListeUtilisateurs());
        }*/
        lesListes.update(listeDiffusion);
    }
    
    /*private  ArrayList<Personne> convertirEnListeLocataires(){
        ArrayList<Personne> listeLocataires = new ArrayList<>();
        Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        for(int i=0;i<this.listeCasesCochees.size();i++)
            listeLocataires.add(lesLocataires.selectById(this.listeCasesCochees.get(i)));
        return listeLocataires;
    }
    
    private  ArrayList<Personne> convertirEnListeUtilisateurs(){
        ArrayList<Personne> listeUtilisateurs = new ArrayList<>();
        Utilisateurs_DAO lesUtilisateurs= new Utilisateurs_DAO(this.connBdd);
        for(int i=0;i<this.listeCasesCochees.size();i++)
            listeUtilisateurs.add(lesUtilisateurs.selectById(this.listeCasesCochees.get(i)));
        return listeUtilisateurs;
    }*/
    
    private  ArrayList<Personne> convertirEnListePersonnes(){
        ArrayList<Personne> listePersonnes = new ArrayList<>();
        //Utilisateurs_DAO lesUtilisateurs= new Utilisateurs_DAO(this.connBdd);
        DAO dao;
        switch(this.donnees){
            case "locataire" -> dao = new Locataire_DAO(this.connBdd);
            case "utilisateur" -> dao = new Utilisateurs_DAO(this.connBdd);
            default -> dao = null;
        }
        for(int i=0;i<this.listeCasesCochees.size();i++)
            listePersonnes.add((Personne) dao.selectById(this.listeCasesCochees.get(i)));
        return listePersonnes;
    }
}