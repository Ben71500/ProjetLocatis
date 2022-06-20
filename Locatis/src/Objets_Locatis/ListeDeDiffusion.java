package Objets_Locatis;

import java.util.ArrayList;

/**
 * Classe décrivant une liste de diffusion de personnes.
 * @author Benjamin Mathilde
 */
public class ListeDeDiffusion{
    
    private int id;
    private String nom;
    private ArrayList<Personne> liste;

    /**
     * Constructeur d'une liste de diffusion
     * @param id : id de la liste de diffusion (entier)
     * @param nom : nom donné à cette liste de diffusion (chaîne de caractères)
     * @param liste : liste de personnes
     */
    public ListeDeDiffusion(int id, String nom, ArrayList<Personne> liste) {
        this.id = id;
        this.nom = nom;
        this.liste = liste;
    }

    /**
     *
     * @return l'id de la liste de diffusion
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @return le nom donné à la liste de diffusion
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return les personnes de la liste de diffusion
     */
    public ArrayList<Personne> getListe() {
        return liste;
    }

    /**
     * Méthode qui permet de modifier les personnes de la liste de diffusion
     * @param liste : nouvelle liste de personnes
     */
    public void setListe(ArrayList<Personne> liste) {
        this.liste = liste;
    }
    
    /**
     * Méthode qui détermine si la liste est une liste de locataires ou une liste d'utilisateurs
     * @return une chaine de caractère qui identifie le type des personnes de la liste
     */
    public String getTypeListe(){
        if(liste.size()>0){
            if(liste.get(0) instanceof Locataire)
                return"locataire";
            if(liste.get(0) instanceof Utilisateur)
                return"utilisateur";
        }
        return "locataire";
    }
    
    /**
     * Méthode qui retourne une liste des id des personnes de la liste de diffusion
     * @return la liste des id des personnes
     */
    public ArrayList<Integer> getListeId(){
        ArrayList<Integer> listeId = new ArrayList<>();
        for(int i=0; i<liste.size();i++){
            listeId.add(liste.get(i).getId());
        }
        return listeId;
    }
    
    @Override
    public String toString(){
        return id+" : "+nom;
    }
}