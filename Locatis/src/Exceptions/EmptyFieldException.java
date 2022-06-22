package Exceptions;

import Popups.PopupInformation;

/**
 * Classe dérivée de la classe Exception
 * Cette exception est levée si un champ n'est pas saisi
 * @author Benjamin Mathilde
 */
public class EmptyFieldException extends Exception {

    /**
     * Constructeur
     * @param message : champ qui a déclenché l'exception
     */
    public EmptyFieldException(String message) {
        super(message);
    }
    
    /**
     * Méthode qui affiche ce qui a déclenché l'exception dans une popup
     */
    public void afficherErreur(){
        new PopupInformation("Veuillez saisir "+this.getMessage());
    }
}