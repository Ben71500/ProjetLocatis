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

/**
 * Classe modèle de l'interface d'ajouts et de modifications des listes de diffusion
 * @author Benjamin Mathilde
 */
public class Modele_Ajout_Listes {
    
    private Object[][] tableau;
    private String[] entetes;
    private List liste;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private ArrayList<Integer> listeCasesCochees = new ArrayList<>();
    private String donnees;
    private Utilisateur utilisateur;

    /**
     * Constructeur du modèle en cas d'ajout d'une liste
     * @param lesDonnees : type de données que contient la liste
     * @param user : utilisateur qui utilise l'interface
     */    
    public Modele_Ajout_Listes(String lesDonnees, Utilisateur user) {
        this.donnees = lesDonnees;
        this.utilisateur = user;
        choisirModele();
    }
    
    /**
     * Constructeur du modèle en cas de modification d'une liste
     * @param lesDonnees : type de données que contient la liste
     * @param user : utilisateur qui utilise l'interface
     * @param listeId : liste des id des personnes déjà cochées dans la liste
     */
    public Modele_Ajout_Listes(String lesDonnees, Utilisateur user, ArrayList<Integer> listeId) {
        this.donnees = lesDonnees;
        this.utilisateur = user;
        this.listeCasesCochees = listeId;
        choisirModele();
    }
    
    /**
     * Méthode qui définit le modèle en fonction du type de personne
     */
    public void choisirModele(){
        switch(this.donnees){
            case "locataire" -> modeleLocataires();
            case "utilisateur" -> modeleUtilisateurs();
        }
    }
    
    /**
     * Méthode qui permet de générer un tableau des locataires
     */
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
            tableau [i][0]= listeCasesCochees.contains(leLocataire.getId());//case à cocher
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
    
    /**
     * Méthode qui permet de générer un tableau des utilisateurs
     */
    public void modeleUtilisateurs(){
        String[] tabEntetes = {"Case", "ID","Login", "Catégorie"};
        this.setEntetes(tabEntetes);
        
        //Récupération des utilisateurs dans une ArrayList
        Utilisateurs_DAO utilisateurs= new Utilisateurs_DAO(connBdd);
        //on affiche dans le tableau les utilisateurs en fonction de leurs catégorie
        //et de la catégorie de l'utilisateur qui utilise l'interface
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
            tableau [i][0]= listeCasesCochees.contains(user.getId());//case à cocher
            tableau [i][1]=user.getId();
            tableau [i][2]=user.getLogin();
            tableau [i][3]=user.getCat();
        }
    }
    
    /**
     *
     * @return la liste des id des personnes dont la case est cochée
     */
    public ArrayList<Integer> getListeCasesCochees() {
        return listeCasesCochees;
    }

    /**
     * Méthode qui permet de changer le type de peresonne
     * @param donnees : type de personnes
     */
    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    /**
     * Méthode qui permet de changer l'entête du tableau
     * @param entetes : tableau avec le nom de chaque colonne du tableau
     */
    public void setEntetes(String[] entetes) {
        this.entetes = entetes;
    }

    /**
     *
     * @return le tableau avec les données
     */
    public Object[][] getTableau() {
        return tableau;
    }

    /**
     *
     * @return le tableau des entêtes
     */
    public String[] getEntetes() {
        return entetes;
    }
    
    /**
     * Méthode qui permet de cocher une case si elle ne l'est pas et de décocher une case si elle est cochée
     * @param ligne : le numéro de ligne de la case
     */
    public void cocher(int ligne){
        int id=(int) tableau[ligne][1];
        if(listeCasesCochees.contains(id)){
            listeCasesCochees.remove((Object)id);
        }else{
            listeCasesCochees.add(id);
        }
    }
    
    /**
     * Méthode qui permet de cocher toutes les cases
     */
    public void cocherTout(){
        for (Object[] tableau1 : tableau) {
            tableau1[0] = true;
            if (!listeCasesCochees.contains((Integer)tableau1[1])) {
                listeCasesCochees.add((Integer) tableau1[1]);
            }
        }
    }
    
    /**
     * Méthode qui permet de décocher toutes les cases
     */
    public void decocherTout(){
        for (Object[] tableau1 : tableau) {
            tableau1[0] = false;
            if (listeCasesCochees.contains((Integer)tableau1[1])) {
                listeCasesCochees.remove((Object) tableau1[1]);
            }
        }
    }
    
    /**
     * Méthode qui génère une requête SQL permettant de filtrer avec des valeurs
     * @param categorie : type de données à trier
     * @param signe : <, > ou =
     * @param nombre : nombre du tri
     */
    public void getTri(String categorie, String signe, String nombre){
        String requete = "Select * from "+this.donnees+" where "+categorie+" "+signe+" "+nombre;
        executerRequeteLocataire(requete);
    }
    
    /**
     * Méthode qui génère une requête SQL permettant de trier par catégorie
     * @param categorie : type de données à trier
     */
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
    
    /**
     * Méthode qui permet de récupérer des locataires triés grâce à une requête
     * @param requete : requête SQL
     */
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
    
    /**
     * Méthode qui permet de récupérer des utilisateurs triés grâce à une requête
     * @param requete : requête SQL
     */
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
    
    /**
     * Méthode qui permet de créer une liste de diffusion
     * @param nom : nom de la liste
     */
    public void ajouter(String nom){
        ListeDeDiffusion_DAO lesListes= new ListeDeDiffusion_DAO(this.connBdd);
        lesListes.create(new ListeDeDiffusion(0,nom,this.convertirEnListePersonnes()));
    }
    
    /**
     * Méthode qui permet de modifier une liste de diffusion
     * @param listeDiffusion : liste à modifier
     */
    public void modifier(ListeDeDiffusion listeDiffusion){
        ListeDeDiffusion_DAO lesListes= new ListeDeDiffusion_DAO(this.connBdd);
        listeDiffusion.setListe(this.convertirEnListePersonnes());
        lesListes.update(listeDiffusion);
    }
    
    /**
     * Méthode qui permet de convertir la liste d'id en liste de personnes
     */
    private ArrayList<Personne> convertirEnListePersonnes(){
        ArrayList<Personne> listePersonnes = new ArrayList<>();
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