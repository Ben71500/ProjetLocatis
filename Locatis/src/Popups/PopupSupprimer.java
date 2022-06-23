package Popups;

import javax.swing.*;

/**
 * Classe qui affiche une popup pour la suppression
 * @author Benjamin Mathilde
 */
public class PopupSupprimer extends JFrame{
    
    private int choix;
    
    /**
     * Constructeur de la popup : la popup affiche un message et dispose de deux boutons : YES et NO
     */
    public PopupSupprimer(){
        this.choix = JOptionPane.showConfirmDialog(this,"Confirmer la suppression ?","Supprimer", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    /**
     * Méthode qui retourne le choix de l'utilisateur
     * @return true si l'utilisateur a cliqué sur YES et false sinon
     */
    public boolean getConfirmation(){
        return this.choix==0;
    }
}