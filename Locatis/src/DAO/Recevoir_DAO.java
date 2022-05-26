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
    
    
    public boolean create(int idCampagne, List<ListeDeDiffusion> obj) {
        try {
            boolean ok = true;
            Statement statement = this.connection.createStatement();
            ListeDeDiffusion_DAO listeDAO = new ListeDeDiffusion_DAO(this.connection);
            for(int i=0;i<obj.size();i++){
                listeDAO.create(obj.get(i));
                ok= !statement.execute("insert into recevoir (ID_campagne, ID_listeDiff) values("
                        + idCampagne + " , "
                        + obj.get(i).getId() + " )"
                );
            }
            return ok;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean delete(int idCampagne) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("delete from recevoir where ID_campagne=" + idCampagne);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean update(int idCampagne, List<ListeDeDiffusion> obj) {
        this.delete(idCampagne);
        this.create(idCampagne, obj);
        return true;
    }
    
    
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