package Exceptions;

import Popups.PopupInformation;

/**
 * Classe dérivée de la classe Exception
 * Cette exception est levée si aucune ligne n'est sélectionnée
 * @author Benjamin Mathilde
 */
public class PasDeLignesSelectionneesException extends Exception{
    
    /**
     * Constructeur
     * @param message : champ qui a déclenché l'exception
     */
    public PasDeLignesSelectionneesException(String message) {
        super(message);
    }
    
    /**
     * Méthode qui affiche ce qui a déclenché l'exception dans une popup
     */
    public void afficherErreur(){
        new PopupInformation("Veuillez sélectionner "+this.getMessage());
    }
}