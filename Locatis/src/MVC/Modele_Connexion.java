package MVC;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Utilisateurs_DAO;
import Locatis.Utilisateur;
import java.sql.Connection;

public class Modele_Connexion {
    
    public Utilisateur utilisateur;
    
    public Modele_Connexion(Utilisateur user){
        this.utilisateur = user;
    }
    
    public Utilisateur trouverUser(){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        
        Utilisateurs_DAO user = new Utilisateurs_DAO(connBdd);
        
            Utilisateur leUser=user.selectByName(utilisateur.getLogin());
            if(leUser.getMotDePasse().equals(utilisateur.getMotDePasse()))
                return leUser;
                
        return null;
    }
}
