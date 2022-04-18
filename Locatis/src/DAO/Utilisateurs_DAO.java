package DAO;

import DAO.Connexion;
import DAO.ConnectionBDD;
import DAO.DAO;
import Locatis.Utilisateur;
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
            return !statement.execute("insert into utilisateur (ID_utilisateur, login, Mdp, CAT) values("
                    + obj.getId() + " , '"
                    + obj.getLogin() + "' , '"
                    + obj.getMotDePasse() + "' , '"
                    + obj.getCat() + "')"
            );
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(Utilisateur uti) {
        try {
            Statement statement = this.connection.createStatement();
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
                    + "CAT='" + obj.getCat() +"' "
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
            ResultSet res = statement.executeQuery("Select * from utilisateur where id=" + id);
            res.next();
            return new Utilisateur(res.getInt("ID_utilisateur"),
                    res.getString("login"),
                    res.getString("Mdp"),
                    res.getString("CAT")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Utilisateur selectByName(String login) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where login='" + login + "'");
            res.next();

            return new Utilisateur(res.getInt("ID_utilisateur"), res.getString("login"), res.getString("Mdp"), res.getString("CAT"));

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
                        res.getString("CAT"))
                );
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }    
}
