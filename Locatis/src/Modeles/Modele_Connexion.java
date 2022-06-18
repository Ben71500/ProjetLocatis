package Modeles;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Utilisateurs_DAO;
import Objets_Locatis.Utilisateur;
import Exceptions.UserNotFoundException;
import java.sql.Connection;
import javax.swing.JLabel;

public class Modele_Connexion {
    
    public Modele_Connexion(){
    }
    
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
