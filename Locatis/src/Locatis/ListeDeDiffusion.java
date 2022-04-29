package Locatis;

import java.util.ArrayList;

public class ListeDeDiffusion {
    
    private int id;
    private String nom;
    private ArrayList<Locataire> liste;

    public ListeDeDiffusion(int id, String nom, ArrayList<Locataire> liste) {
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

    public ArrayList<Locataire> getListe() {
        return liste;
    }
    
    public String toString(){
        String s ="\n***"+this.id+"\n"+nom+"\n";
        for(int i=0; i<liste.size();i++)
            s+=liste.get(i).getNom()+" - ";
        return s;
    }
}