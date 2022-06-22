package Vues;

import Objets_Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe dérivée de JDialog qui décrit la vue permettant
 * visualiser les campagnes dont un locataire fait partie
 * @author Benjamin Mathilde
 */
public class Vue_dialog_locataire extends JDialog{
    
    JPanel panneau = new JPanel();
    JPanel haut = new JPanel();
    JPanel centre = new JPanel();
    JPanel bas = new JPanel();
    
    JTextArea label = new JTextArea();
    JLabel titre = new JLabel("Outils de recherche");
    
    JButton retour = new JButton("Retour");
    
    /**
     * Constructeur de la vue
     * @param user : utilisateur dont on affiche ses campagnes
     */
    public Vue_dialog_locataire(Utilisateur user){
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        haut.add(titre);
        
        centre.setLayout(new GridLayout(1, 1));
        centre.add(label);
        label.setEditable(false);
        
        bas.add(retour);
        
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        this.add(panneau);
        this.pack();
    }

    /**
     *
     * @return le JTextArea contenant les informations
     */
    public JTextArea getLabel() {
        return label;
    }
    
    /**
     * Méthode qui permet d'ajouter des écouteurs à un bouton
     * @param nomBouton : nom du bouton
     * @param listener : écouteur
     */
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
    
    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
}