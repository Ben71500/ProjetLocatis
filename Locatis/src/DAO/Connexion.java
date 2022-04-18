package DAO;

import DAO.Connector;

public class Connexion extends Connector{
    
    public Connexion() {
        super("jdbc:mysql://localhost:3306/javalocatis","root","","javalocatis");
    }
}