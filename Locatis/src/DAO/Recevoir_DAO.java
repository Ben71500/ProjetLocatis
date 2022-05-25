package DAO;

import Locatis.ListeDeDiffusion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Recevoir_DAO {
    
    private Connection connection;

    public Recevoir_DAO(Connection connection) {
        this.connection = connection;
    }
    
    /*
    public boolean create(int idCampagne, Message obj) {
        try {
            Statement statement = this.connection.createStatement();
            Message_DAO messageDAO = new Message_DAO(this.connection);
            messageDAO.create(obj);
            return !statement.execute("insert into contient (ID_campagne, ID_message) values("
                    + idCampagne + " , "
                    + obj.getId() + " )"
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idCampagne) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("idelete from contient where ID_campagne=" + idCampagne);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean update(int idCampagne, Message obj) {
        try {
            Statement statement = this.connection.createStatement();
            Message_DAO messageDAO = new Message_DAO(this.connection);
            messageDAO.create(obj);
            return !statement.execute("update contient set "
                    + "ID_campagne = "+ idCampagne + " , "
                    + "ID_message = "+ obj.getId() + " )"
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    */
    
    public List<ListeDeDiffusion> getListes(int idCampagne){
        try{
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_listeDiff from recevoir where ID_campagne=" + idCampagne);
            ListeDeDiffusion_DAO listeDAO = new ListeDeDiffusion_DAO(this.connection);
            List<ListeDeDiffusion> listes = new ArrayList<>();
            while (res.next()) {
                listes.add(listeDAO.selectById(res.getInt("ID_listeDiff")));
            }
            return listes;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
}