package Exceptions;

import Popups.PopupInformation;

/**
 * Classe dérivée de la classe Exception
 * Cette exception est levée si une valeur saisie est incorrecte
 * @author Benjamin Mathilde
 */
public class ValeurIncorrecteException extends Exception{
    
    /**
     * Constructeur
     * @param message : champ qui a déclenché l'exception
     */
    public ValeurIncorrecteException(String message) {
        super(message);
    }
    
    /**
     * Méthode qui affiche ce qui a déclenché l'exception dans une popup
     */
    public void afficherErreur(){
        new PopupInformation("Veuillez saisir "+this.getMessage()+" correcte");
    }   
}