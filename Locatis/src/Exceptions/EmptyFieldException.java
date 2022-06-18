package Exceptions;

import Popups.PopupInformation;

public class EmptyFieldException extends Exception {

    public EmptyFieldException(String message) {
        super(message);
    }
    
    public void afficherErreur(){
        new PopupInformation("Veuillez saisir "+this.getMessage());
    }
}