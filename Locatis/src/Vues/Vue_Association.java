/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vues;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Benjamin
 */
public class Vue_Association extends JFrame{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel centreHaut = new JPanel();
    private JPanel centreBas = new JPanel();
    private JPanel bas = new JPanel();
    
    private JComboBox logementList = new JComboBox();
    private JComboBox locataireList = new JComboBox();
    
    private JComboBox logementListByLocataire = new JComboBox();
    private JComboBox locataireListByLocataire = new JComboBox();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton retirer = new JButton("Retirer");
    private JButton retour = new JButton("Retour");
    
    private JLabel titre = new JLabel("Attribution de logement");
    
    public Vue_Association(){
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        this.haut.add(this.titre);
        this.centre.setLayout(new GridLayout(2,1));
        this.centre.add(this.centreHaut);
        this.centre.add(this.centreBas);
        this.centreHaut.setLayout(new GridLayout(2,2));
        this.centreHaut.add(locataireList);
        this.centreHaut.add(logementList);
        this.centreHaut.add(ajouter);
        this.centreBas.setLayout(new GridLayout(2,2));
        this.centreBas.add(locataireListByLocataire);
        this.centreBas.add(logementListByLocataire);
        this.centreBas.add(retirer);
        this.bas.add(retour);
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
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

    public JComboBox getLogementList() {
        return logementList;
    }

    public void setLogementList(JComboBox logementList) {
        this.logementList = logementList;
    }

    public JComboBox getLocataireList() {
        return locataireList;
    }

    public void setLocataireList(JComboBox locataireList) {
        this.locataireList = locataireList;
    }

    public JPanel getBas() {
        return bas;
    }

    public void setBas(JPanel bas) {
        this.bas = bas;
    }

    public JButton getAjouter() {
        return ajouter;
    }

    public void setAjouter(JButton ajouter) {
        this.ajouter = ajouter;
    }

    public JButton getRetirer() {
        return retirer;
    }

    public JPanel getCentreHaut() {
        return centreHaut;
    }

    public void setCentreHaut(JPanel centreHaut) {
        this.centreHaut = centreHaut;
    }

    public JPanel getCentreBas() {
        return centreBas;
    }

    public void setCentreBas(JPanel centreBas) {
        this.centreBas = centreBas;
    }

    public JComboBox getLogementListByLocataire() {
        return logementListByLocataire;
    }

    public void setLogementListByLocataire(JComboBox logementListByLocataire) {
        this.logementListByLocataire = logementListByLocataire;
    }

    public JComboBox getLocataireListByLocataire() {
        return locataireListByLocataire;
    }

    public void setLocataireListByLocataire(JComboBox locataireListByLocataire) {
        this.locataireListByLocataire = locataireListByLocataire;
    }
    
    

    public void setRetirer(JButton retirer) {
        this.retirer = retirer;
    }

    public JButton getRetour() {
        return retour;
    }

    public void setRetour(JButton retour) {
        this.retour = retour;
    }

    public JLabel getTitre() {
        return titre;
    }

    public void setTitre(JLabel titre) {
        this.titre = titre;
    }
    
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        JButton bouton;
        bouton = switch (nomBouton.toUpperCase()) {
            case "AJOUTER" ->
                bouton = ajouter;
            case "RETOUR" ->
                bouton = retour;
            case "RETIRER" ->
                bouton = retirer;
            default -> null;
        };
        if(bouton != null)
            bouton.addActionListener(listener);
        if(nomBouton.toUpperCase().equals("LISTELOGEMENT"))
            this.logementListByLocataire.addActionListener(listener);
        if(nomBouton.toUpperCase().equals("LISTELOCA"))
            this.locataireListByLocataire.addActionListener(listener);
    }
    
    public void quitter() {
        //System.exit(0);
        this.dispose();
    }
}
