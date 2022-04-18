package DAO;

import DAO.Connector;
import java.sql.*;

/**
 * Classe basé sur le Design Pattern Singleton Permet de limiter l'instanciation
 * des objets de connection à la base. Version générique de la classe délégant
 * le paramétrage de la base de donnée à un champ connector d'un type générique
 * dérivé de la classe abstraite connector
 *
 * @author Hervé Martinez
 * @param <T>
 */
public class ConnectionBDD<T extends Connector> {

    /**
     * Objet contenant les informations de connexion à la base
     */
    private final T connector;

    /**
     * Objet Connexion (champ d'instance : le seul)
     */
    private Connection connect;

    /**
     * Champ de classe destiné à recevoir l'instance unique de la classe
     * Connection BDD
     */
    private static ConnectionBDD laConnectionBDD;

    //Constructeur ConnectioBDD privé destiné à empêcher l'appel d'un constructeur
    private ConnectionBDD(T connector) {

        this.connector = connector;
        try {
            connect = DriverManager.getConnection(this.connector.getURL(), this.connector.getUSER(), this.connector.getPWD());
        } catch (SQLException ex) {
            connect = null;
        }
    }

    /**
     * Méthode d'obtention de l'instance unique de la connection
     *
     * @return le champ connection de l'instance unique de ConnectionBDD
     */
    public static Connection getInstance(Connector connector) {
        if (laConnectionBDD == null) {
            laConnectionBDD = new ConnectionBDD(connector);
        }
        return laConnectionBDD.connect;
    }

   

    /**
     * Vérifie si l'instance de la base de donnée existe pour ce connecteur
     *
     * @param connector le connecteur contenant les information de connection
     * @return
     */
    public static boolean isInstanceOf(Connector connector) {

        return laConnectionBDD.connect != null && laConnectionBDD.connector.equals(connector);
    }
    
    /**
     * Ferme la connexion en cours
     * @param connector1
     * @return
     */
    public static boolean killInstance(Connector connector1) {

        if (isInstanceOf(connector1)) {
            laConnectionBDD = null;
            return true;
        } else {
            return false;
        }
    }
}
