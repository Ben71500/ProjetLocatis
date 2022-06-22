package Exceptions;

import Popups.PopupInformation;

/**
 * Classe dérivée de la classe Exception
 * Cette exception est levée si aucune case n'est cochée
 * @author Benjamin Mathilde
 */
public class PasDeCaseCocheeException extends Exception{
    
    /**
     * Constructeur
     */
    public PasDeCaseCocheeException() {
        super("Pas de case cochée");
    }
    
    /**
     * Méthode qui affiche l'erreur dans une popup
     */
    public void afficherErreur(){
        new PopupInformation("Veuillez cocher au moins un locataire ");
    }
}