/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locatis;
import java.util.*;
/**
 *
 * @author benja
 */
public class Campagne {
    private ArrayList<String> listeEmail;

    public Campagne() {}

    public ArrayList<String> getListeEmail() {
        return listeEmail;
    }

    public void setListeEmail(ArrayList<String> listeEmail) {
        this.listeEmail = listeEmail;
    }
  
    public void envoyerCampagne(){
        for (int i = 0; i < listeEmail.size(); i++){
            listeEmail.get(i);
            System.out.println("Message Envoyer");
        }
    }
}
