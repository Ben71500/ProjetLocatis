package Vues;

import javax.swing.*;
import Objets_Locatis.PieChart;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

/**
 * Classe dérivée de JFrame qui décrit la vue permettant d'afficher les statistiques
 * @author Benjamin Mathilde
 */
public class Vue_Statistique extends JFrame{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel bas = new JPanel();
    
    private JLabel titre = new JLabel("Statistique");
    
    private PieChart graphiqueParTrancheAge = new PieChart("Tranche d'âge");
    private PieChart graphiqueParLogementOccuper = new PieChart("Logement occupé");
    private PieChart graphiqueDesCampagnesTerminer = new PieChart("Campagne en cours");
    
    private JButton retour = new JButton("Retour");
    
    /**
     * Constructeur de la vue
     */
    public Vue_Statistique(){
        
        super("Statistiques");
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        haut.add(titre);
        centre.setLayout(new GridLayout(1, 3));
        bas.add(retour);
        
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        this.add(panneau);
        this.pack();
    }

    /**
     *
     * @return le JPanel au centre
     */
    public JPanel getCentre() {
        return centre;
    }

    /**
     *
     * @return le PieChart de l'âge
     */
    public PieChart getGraphiqueParTrancheAge() {
        return graphiqueParTrancheAge;
    }

    /**
     *
     * @return le PieChart des logements
     */
    public PieChart getGraphiqueParLogementOccuper() {
        return graphiqueParLogementOccuper;
    }

    /**
     *
     * @return le PieChart des campagnes
     */
    public PieChart getGraphiqueDesCampagnesTerminer() {
        return graphiqueDesCampagnesTerminer;
    }
    
    /**
     * Méthode qui permet d'ajouter des écouteurs à un bouton
     * @param nomBouton : nom du bouton
     * @param listener : écouteur
     */
    public void ajouterEcouteur(String nomBouton, ActionListener listener) {
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
    
    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
}