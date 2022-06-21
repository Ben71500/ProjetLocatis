/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controleurs;

import Modeles.Modele_Statistique;
import Vues.Vue_Menu;
import Vues.Vue_Statistique;
import Objets_Locatis.Utilisateur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe de l'objet Conroleur_Statistique implementant l'interface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_Statistique implements ActionListener{

    private final Vue_Statistique laVue;
    private final Modele_Statistique leModele;
    private Utilisateur userConnecte;

    /**
     * Constructeur de l'objet Controleur_Statistique
     * @param uneVue : vue du controleur
     * @param unModele : modele du controleur
     * @param user : Utilisateur en cours d'utilisation
     */
    public Controleur_Statistique(Vue_Statistique uneVue, Modele_Statistique unModele, Utilisateur user) {
        laVue = uneVue;
        leModele = unModele;
        userConnecte = user;
        this.laVue.getGraphiqueDesCampagnesTerminer().setData("campagne");
        this.laVue.getGraphiqueParLogementOccuper().setData("logement");
        this.laVue.getGraphiqueParTrancheAge().setData("age");
        this.laVue.getCentre().add(this.laVue.getGraphiqueParLogementOccuper().createDemoPanel());
        this.laVue.getCentre().add(this.laVue.getGraphiqueParTrancheAge().createDemoPanel());
        this.laVue.getCentre().add(this.laVue.getGraphiqueDesCampagnesTerminer().createDemoPanel());
        this.laVue.pack();
        this.laVue.ajouterEcouteur("retour", this);
    }
    
    /**
     *
     * @return Vue_Statistique
     */
    public Vue_Statistique getVue() {
        return laVue;
    }

    /**
     *
     * @return Modele_Statistique
     */
    public Modele_Statistique getModele() {
        return leModele;
    }
    
    /**
     *
     * @return Utilisateur
     */
    public Utilisateur getUser(){
        return this.userConnecte;
    }

    /**
     * Méthode de la classe abstraite ActionListener
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (source.getText().toUpperCase()) {
            case "RETOUR" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        //Controleur_Ajout_Listes controleur = new Controleur_Ajout_Listes(new Vue_Ajout_Listes(), new Modele_Ajout_Listes(), userConnecte);
                        Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(userConnecte), userConnecte);
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
                
            }
        }
    }
}
