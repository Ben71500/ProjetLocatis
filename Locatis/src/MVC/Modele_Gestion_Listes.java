package MVC;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import Locatis.Batiment;
import Locatis.Locataire;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Modele_Gestion_Listes {
    
    private Object[][] tableau;
    private String[] entetes;
    private List liste;
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private ArrayList<Integer> listeCasesCochees = new ArrayList<>();

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
    
    public void getTri(String categorie, String signe, int nombre){
        String requete = "Select * from locataire where "+categorie+" "+signe+" "+nombre;
        executerRequete(requete);
    }
    
    public void getAll(){
        String requete = "Select * from locataire";
        executerRequete(requete);
    }
    
    public void trierPar(String categorie){
        String requete = "Select * from locataire";
        if(!categorie.equals("Tous"))
            requete += " order by ";
        switch(categorie){
            case "ID" -> requete+="ID_locataire";
            case "Nom" -> requete+="Nom";
            case "Prénom" -> requete+="Prenom";
            case "Age" -> requete+="Age";
            case "Ancienneté" -> requete+="Anciennete";
        }
        executerRequete(requete);
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
    
    public void executerRequete(String requete){
        //Récupération des locataires dans une ArrayList
        Locataire_DAO lesLocataires= new Locataire_DAO(this.connBdd);
        
        liste=(ArrayList<Locataire>)lesLocataires.getRequete(requete);
        //On convertit cette ArrayList en tableau à deux dimensions
        tableau = new Object[liste.size()][8];
        for(int i=0; i<liste.size();i++){
            Locataire leLocataire = (Locataire) liste.get(i);
            
            ArrayList<Batiment> listebat=(ArrayList<Batiment>) lesLocataires.getLocation(leLocataire.getId());
            System.out.println(listebat);
            
            tableau [i][0]= listeCasesCochees.contains(leLocataire.getId());
            tableau [i][1]=leLocataire.getId();
            tableau [i][2]=leLocataire.getNom();
            tableau [i][3]=leLocataire.getPrenom();
            tableau [i][4]=leLocataire.getAge();
            tableau [i][5]=leLocataire.getAnciennete().getDateEcrite();
            tableau [i][6]=leLocataire.getMail();
            tableau [i][7]=leLocataire.getTelephone();
        }
        
    }
    
    public void cocher(int ligne){
        int id=(int) tableau[ligne][1];
        /*System.out.println(id);*/
        if(listeCasesCochees.contains(id)){
            listeCasesCochees.remove((Object)id);
            /*System.out.println("enleve");*/
        }else{
            listeCasesCochees.add(id);
            /*System.out.println("ajoute");*/
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
}