package DAO;

import DAO.Connexion;
import DAO.ConnectionBDD;
import DAO.DAO;
import Objets_Locatis.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Utilisateurs_DAO extends DAO<Utilisateur> {
    
    public Utilisateurs_DAO() {
        super(ConnectionBDD.getInstance(new Connexion()));
    }
    
    public Utilisateurs_DAO(Connection connection) {
        super(connection);
    }
    
    public void augmenterLesDroits(String cat, int id){
        try {
            Statement statement = this.connection.createStatement();
            String sql = "update utilisateur set CAT='" + cat +"' "+ "where  ID_utilisateur=" + id+"";
            statement.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public boolean create(Utilisateur obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("insert into utilisateur (ID_utilisateur, login, Mdp, CAT, Email, Password) values("
                    + obj.getId() + " , '"
                    + obj.getLogin() + "' , '"
                    + obj.getMotDePasse() + "' , '"
                    + obj.getCat() + "' , '"
                    + obj.getMail() + "' , '"
                    + obj.getPassword() + "')"
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Utilisateur uti) {
        try {
            Statement statement = this.connection.createStatement();
            deleteAllListesByIdUtilisateur(uti);
            return !statement.execute("delete from utilisateur where ID_utilisateur=" + uti.getId());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Utilisateur obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("update utilisateur set "
                    + "login='" + obj.getLogin()+ "' , "
                    + "Mdp='" + obj.getMotDePasse()+ "' , "
                    + "CAT='" + obj.getCat() +"' , "
                    + "Email='" + obj.getMail() + "' , "
                    + "Password='" + obj.getPassword() + "' "
                    + "where  ID_utilisateur=" + obj.getId()
            );
        } catch (SQLException ex) {
            return false;
        }
        
    }

    @Override
    public Utilisateur selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where ID_utilisateur=" + id);
            res.next();
            return new Utilisateur(res.getInt("ID_utilisateur"),
                    res.getString("login"),
                    res.getString("Mdp"),
                    res.getString("CAT"),
                    res.getString("Email"),
                    res.getString("Password")
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Utilisateur selectByName(String login) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where login='" + login + "'");
            res.next();

            return new Utilisateur(res.getInt("ID_utilisateur"), res.getString("login"), res.getString("Mdp"), res.getString("CAT"), res.getString("Email"), res.getString("Password"));

        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Utilisateur> getAll() {

        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur");
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                        res.getInt("ID_utilisateur"),
                        res.getString("login"),
                        res.getString("Mdp"),
                        res.getString("CAT"),
                        res.getString("Email"),
                        res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    public List<Utilisateur> getAllUtilisateurs() {

        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where CAT='uti1' OR CAT='uti2'");
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                        res.getInt("ID_utilisateur"),
                        res.getString("login"),
                        res.getString("Mdp"),
                        res.getString("CAT"),
                        res.getString("Email"),
                        res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    public List<Utilisateur> getAllUtilisateursEtGestionnaires() {

        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where CAT!='adm'");
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                        res.getInt("ID_utilisateur"),
                        res.getString("login"),
                        res.getString("Mdp"),
                        res.getString("CAT"),
                        res.getString("Email"),
                        res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    public List<Utilisateur> getRequete(String requete) {

        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery(requete);
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                    res.getInt("ID_utilisateur"),
                    res.getString("login"),
                    res.getString("Mdp"),
                    res.getString("CAT"),
                    res.getString("Email"),
                    res.getString("Password")
                ));            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    public boolean deleteAllListesByIdUtilisateur(Utilisateur obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from utilisateur_liste where ID_utilisateur=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }
}
