package Objets_Locatis;

import java.util.ArrayList;

public class ListeDeDiffusion{
    
    private int id;
    private String nom;
    private ArrayList<Personne> liste;

    public ListeDeDiffusion(int id, String nom, ArrayList<Personne> liste) {
        this.id = id;
        this.nom = nom;
        this.liste = liste;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public ArrayList<Personne> getListe() {
        return liste;
    }

    public void setListe(ArrayList<Personne> liste) {
        this.liste = liste;
    }
    
    public String getTypeListe(){
        if(liste.size()>0){
            if(liste.get(0) instanceof Locataire)
                return"locataire";
            if(liste.get(0) instanceof Utilisateur)
                return"utilisateur";
        }
        return "locataire";
    }
    
    public ArrayList<Integer> getListeId(){
        ArrayList<Integer> listeId = new ArrayList<>();
        for(int i=0; i<liste.size();i++){
            listeId.add(liste.get(i).getId());
        }
        return listeId;
    }
    
    /*public String toString(){
        String s ="\n***"+this.id+"\n"+nom+"\n";
        for(int i=0; i<liste.size();i++)
            s+=liste.get(i).toString()+" - ";
        return s;
    }*/
    
    @Override
    public String toString(){
        String s = id+" : "+nom;
        //String s ="\n***"+this.id+"\n"+nom+"\n";
        /*for(int i=0; i<liste.size();i++)
            s+=liste.get(i).toString()+" - ";*/
        return s;
    }
}