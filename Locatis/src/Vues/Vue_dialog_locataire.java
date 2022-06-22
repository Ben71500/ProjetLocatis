/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vues;

import Objets_Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author benja
 */
public class Vue_dialog_locataire extends JDialog{
    JPanel panneau = new JPanel();
    JPanel haut = new JPanel();
    JPanel centre = new JPanel();
    JPanel bas = new JPanel();
    
    JTextArea label = new JTextArea();
    JLabel titre = new JLabel("Outils de recherche");
    
    JButton retour = new JButton("Retour");
    
    public Vue_dialog_locataire(Utilisateur user){
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        haut.add(titre);
        
        centre.setLayout(new GridLayout(1, 1));
        
        centre.add(label);
        
        bas.add(retour);
        retour.setText("Retour");
        
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        label.setEditable(false);
        this.add(panneau);
        
        this.pack();
    }

    public JPanel getPanneau() {
        return panneau;
    }

    public void setPanneau(JPanel panneau) {
        this.panneau = panneau;
    }

    public JPanel getHaut() {
        return haut;
    }

    public void setHaut(JPanel haut) {
        this.haut = haut;
    }

    public JPanel getCentre() {
        return centre;
    }

    public void setCentre(JPanel centre) {
        this.centre = centre;
    }

    public JPanel getBas() {
        return bas;
    }

    public void setBas(JPanel bas) {
        this.bas = bas;
    }

    public JTextArea getLabel() {
        return label;
    }

    public void setLabel(JTextArea label) {
        this.label = label;
    }

    public JLabel getTitre() {
        return titre;
    }

    public void setTitre(JLabel titre) {
        this.titre = titre;
    }

    public JButton getRetour() {
        return retour;
    }

    public void setRetour(JButton retour) {
        this.retour = retour;
    }
    
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        JButton bouton;
        bouton = switch (nomBouton.toUpperCase()) {
            case "RETOUR" ->
                bouton = retour;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
    }
    
    public void quitter() {
        //System.exit(0);
        this.dispose();
    }
}
