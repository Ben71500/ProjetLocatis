package Exceptions;

import javax.swing.JLabel;

/**
 * Classe dérivée de la classe Exception
 * Cette exception est levée si l'utilisateur a saisi un login ou un mot de passe incorrect
 * @author Benjamin Mathilde
 */
public class UserNotFoundException extends Exception{
    
    /**
     * Constructeur
     */
    public UserNotFoundException() {
        super("Erreur");
    }
    
    /**
     * Méthode qui affiche un message d'erreur dans un JLabel
     * @param message : JLabel où le message sera affiché
     */
    public void afficherErreur(JLabel message){
        message.setText("Login ou mot de passe incorrect !");
    }
}
