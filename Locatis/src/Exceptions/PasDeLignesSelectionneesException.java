package Exceptions;

import Popups.PopupInformation;

public class PasDeLignesSelectionneesException extends Exception{
    
    public PasDeLignesSelectionneesException(String message) {
        super(message);
    }
    
    public void afficherErreur(){
        new PopupInformation("Veuillez sélectionner "+this.getMessage());
    }
}