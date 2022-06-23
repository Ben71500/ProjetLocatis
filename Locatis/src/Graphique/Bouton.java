package Graphique;

import java.awt.Color;
import javax.swing.JButton;

/**
 * Classe dérivée de la classe JButton qui permet d'uniformiser tous les boutons
 * @author Benjamin Mathilde
 */
public class Bouton extends JButton{
    
    /**
     * Constructeur du bouton
     * @param nomBouton
     */
    public Bouton(String nomBouton){
        this.setText(nomBouton);
        this.setBackground(new Color(108,71,149));
        this.setForeground(Color.white);
    }
}