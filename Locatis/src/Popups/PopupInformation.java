package Popups;

import javax.swing.*;

/**
 * Classe qui affiche une popup avec un message d'information
 * @author Benjamin Mathilde
 */
public class PopupInformation extends JFrame{
    
    /**
     * Constructeur de la popup
     * @param texte : texte qui est affiché
     */
    public PopupInformation(String texte){
	JOptionPane.showMessageDialog(this,texte);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }   
}