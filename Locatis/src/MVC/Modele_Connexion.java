package MVC;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Utilisateurs_DAO;
import Locatis.Utilisateur;
import interfaceGraphique.UserNotFoundException;
import java.sql.Connection;
import javax.swing.JLabel;

public class Modele_Connexion {
    
    public Utilisateur utilisateur;
    
    public Modele_Connexion(Utilisateur user){
        this.utilisateur = user;
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
