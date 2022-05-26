/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controleur_Statistique{

    private final Vue_Statistique laVue;
    private final Modele_Statistique leModele;
    private Utilisateur userConnecte;

    public Controleur_Statistique(Vue_Statistique uneVue, Modele_Statistique unModele, Utilisateur user) {
        laVue = uneVue;
        leModele = unModele;
        userConnecte = user;
    }
    
    public Vue_Statistique getVue() {
        return laVue;
    }

    public Modele_Statistique getModele() {
        return leModele;
    }
    
    public Utilisateur getUser(){
        return this.userConnecte;
    }
}
