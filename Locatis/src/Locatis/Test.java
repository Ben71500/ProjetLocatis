package Locatis;

import DAO.*;
import java.sql.*;
import java.util.ArrayList;

public class Test {
    public static void main (String args[]){
        
        Campagne c = new Campagne(1, "t", new MyDate(2022,04,20), new MyDate(2022,04,30), "12:00:00", "mensuel", new Utilisateur(0,"admin","admin", "cat"));
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        Campagne_DAO cd = new Campagne_DAO(connBdd);
        //System.out.println(cd.update(c));
        ArrayList<Campagne> liste = (ArrayList<Campagne>) cd.getAll();
        for(int i=0;i<liste.size();i++)
            System.out.println(liste.get(i).getTitre());
        //System.out.println(cd.selectByName("t").getId());
        cd.delete(c);
    }
}
