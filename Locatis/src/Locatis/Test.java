package Locatis;

import DAO.*;
import java.sql.*;
import java.util.ArrayList;

public class Test {
    public static void main (String args[]){
        
        //Campagne c = new Campagne(1, "t", new MyDate(2022,04,20), new MyDate(2022,04,30), "12:00:00", "mensuel", new Utilisateur(0,"admin","admin", "cat"));
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        //Campagne_DAO cd = new Campagne_DAO(connBdd);
        //System.out.println(cd.update(c));
        /*ArrayList<Campagne> liste = (ArrayList<Campagne>) cd.getAll();
        for(int i=0;i<liste.size();i++)
            System.out.println(liste.get(i).getTitre());*/
        //System.out.println(cd.selectByName("t").getId());
        //cd.delete(c);
        
        Locataire_DAO loc = new Locataire_DAO(connBdd);
        ArrayList<Locataire> listloca= (ArrayList<Locataire>) loc.getAll();
        listloca.remove(0);
        
        ListeDeDiffusion_DAO ld=new ListeDeDiffusion_DAO(connBdd);
        
        ListeDeDiffusion l = new ListeDeDiffusion(1,"nomListe",null);
        ListeDeDiffusion l2 = new ListeDeDiffusion(2,"nomListe2-essai",null);
        ListeDeDiffusion l3 = new ListeDeDiffusion(3,"nomListe3",listloca);
        ListeDeDiffusion l4 = new ListeDeDiffusion(0,"nomListe4",listloca);
        ListeDeDiffusion l5 = new ListeDeDiffusion(5,"nomListe5",listloca);
        //ld.create(l);
        //ld.create(l2);
        /*ld.delete(l5);
        ld.create(l5);*/
        //ld.update(l);
        //ld.update(l2);
        //ld.update(l5);
        System.out.println(ld.selectByName("nomListe4").toString());
        ArrayList<ListeDeDiffusion> liste = (ArrayList<ListeDeDiffusion>) ld.getAll();
        for(int i=0;i<liste.size();i++)
            System.out.println(liste.get(i).toString());
        
        /*System.out.println("\n"+ld.selectById(2).getNom());
        System.out.println("\n"+ld.selectByName("nomListe").getId());*/
        
        //ld.delete(l5);
        /*ArrayList<ListeDeDiffusion> liste2 = (ArrayList<ListeDeDiffusion>) ld.getAll();
        for(int i=0;i<liste2.size();i++)
            System.out.println(liste2.get(i).getNom());*/
        
    }
}
