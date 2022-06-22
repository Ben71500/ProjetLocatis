package Vues;

import Objets_Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author mathi
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
     *
     * @param user
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
     * @return
     */
    public JTextArea getLabel() {
        return label;
    }
    
    /**
     *
     * @param nomBouton
     * @param listener
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
     *
     */
    public void quitter() {
        this.dispose();
    }
}