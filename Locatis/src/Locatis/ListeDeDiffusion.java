package Locatis;

import java.util.ArrayList;

public class ListeDeDiffusion <O>{
    
    private int id;
    private String nom;
    private ArrayList<O> liste;

    public ListeDeDiffusion(int id, String nom, ArrayList<O> liste) {
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

    public ArrayList<O> getListe() {
        return liste;
    }
    
    /*public String toString(){
        String s ="\n***"+this.id+"\n"+nom+"\n";
        for(int i=0; i<liste.size();i++)
            s+=liste.get(i).toString()+" - ";
        return s;
    }*/
}