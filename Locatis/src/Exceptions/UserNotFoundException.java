package Exceptions;

import javax.swing.JLabel;

public class UserNotFoundException extends Exception{
    
    public UserNotFoundException() {
        super("");
    }
    
    public void afficherErreur(JLabel message){
        message.setText("Login ou mot de passe incorrect !");
    }
}
