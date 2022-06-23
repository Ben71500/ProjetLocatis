package Graphique;

import java.awt.Color;
import javax.swing.JButton;

public class Bouton extends JButton{
    
    public Bouton(String nomBouton){
        this.setText(nomBouton);
        this.setBackground(new Color(108,71,149));
        this.setForeground(Color.white);
    }
}