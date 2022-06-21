package DAO;

import DAO.Connector;

/**
 *
 * @author Benjamin Mathilde
 */
public class Connexion extends Connector{
    
    /**
     * Méthode qui rentre les informations de connection a la base de donnée utlisée
     */
    public Connexion() {
        super("jdbc:mysql://localhost:3306/javalocatis","root","","javalocatis");
    }
}