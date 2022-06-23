package DAO;

/**
 *
 * @author Benjamin Mathilde
 */
public class Connexion extends Connector{
    
    /**
     * Méthode qui rentre les informations de connection a la base de données utlisée
     */
    public Connexion() {
        super("jdbc:mysql://localhost:3306/javalocatis","root","","javalocatis");
    }
}