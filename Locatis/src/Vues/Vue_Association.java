package Vues;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Graphique.Panneau;
import Graphique.Bouton;

/**
 * Classe dérivée de JFrame qui décrit la vue permettant d'ajouter
 * ou d'enlever une association entre un locataire et un logement
 * @author Benjamin Mathilde
 */
public class Vue_Association extends JFrame{
    
    private Panneau panneau = new Panneau();
    private Panneau haut = new Panneau();
    private Panneau centre = new Panneau();
    private Panneau centreHaut = new Panneau();
    private Panneau centreBas = new Panneau();
    private Panneau bas = new Panneau();
    
    private JComboBox logementList = new JComboBox();
    private JComboBox locataireList = new JComboBox();
    private JComboBox logementListByLocataire = new JComboBox();
    private JComboBox locataireListByLocataire = new JComboBox();
    
    private Bouton ajouter = new Bouton("Ajouter");
    private Bouton retirer = new Bouton("Retirer");
    private Bouton retour = new Bouton("Retour");
    
    private JLabel titre = new JLabel("Attribution de logement");
    
    /**
     * Constructeur de la vue
     */
    public Vue_Association(){
        super("Associer un locataire et un logement");
        
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

    /**
     *
     * @return la JComboBox des logements
     */
    public JComboBox getLogementList() {
        return logementList;
    }

    /**
     *
     * @return la JComboBox des locataires
     */
    public JComboBox getLocataireList() {
        return locataireList;
    }

    /**
     *
     * @return la JComboBox des logements associés au locataire
     */
    public JComboBox getLogementListByLocataire() {
        return logementListByLocataire;
    }

    /**
     *
     * @return la JComboBox des locataires
     */
    public JComboBox getLocataireListByLocataire() {
        return locataireListByLocataire;
    }
    
    /**
     * Méthode qui permet d'ajouter des écouteurs
     * @param nomElement : nom de l'élément auquel on ajoute un écouteur
     * @param listener : écouteur à ajouter
     */
    public void ajouterEcouteur(String nomElement, ActionListener listener) { 
        switch (nomElement.toUpperCase()) {
            case "AJOUTER" ->
                ajouter.addActionListener(listener);
            case "RETOUR" ->
                retour.addActionListener(listener);
            case "RETIRER" ->
                retirer.addActionListener(listener);
            case "LISTELOCA" ->
                this.locataireListByLocataire.addActionListener(listener);
        }
    }
    
    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
}