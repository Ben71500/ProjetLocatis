package Modeles;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Utilisateurs_DAO;
import Objets_Locatis.Utilisateur;
import Exceptions.UserNotFoundException;
import java.sql.Connection;
import javax.swing.JLabel;

/**
 * Classe modèle de l'interface de connexion
 * @author Benjamin Mathilde
 */
public class Modele_Connexion {
    
    /**
     * Constructeur du modèle de connexion
     */
    public Modele_Connexion(){
    }
    
    /**
     * Méthode qui permet de vérifier qu'un utilisateur a bien saisi des informations correctes
     * @param login : login de l'utilisateur
     * @param mdp : mot de passe de l'utilisateur lui permettant de se connecter
     * @param message : JLabel dans lequel peut etre affiché un message d'erreur
     * @return l'utilisateur connecté
     */
    public Utilisateur trouverUser(String login, String mdp, JLabel message){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        
        Utilisateurs_DAO user = new Utilisateurs_DAO(connBdd);
        try{
            Utilisateur leUser=user.selectByName(login);
            if(leUser==null)
                throw new UserNotFoundException();
            if(leUser.getMotDePasse().equals(mdp))
                return leUser;
            else
                throw new UserNotFoundException();
        }
        catch(UserNotFoundException ex){
            ex.afficherErreur(message);
        }
        return null;
    }
}