package DAO;

import Locatis.ListeDeDiffusion;
import Locatis.Locataire;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListeDeDiffusion_DAO extends DAO<ListeDeDiffusion>{
    
    public ListeDeDiffusion_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(ListeDeDiffusion obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into listediffusion VALUES ('"+ obj.getId()+ "' , '"+ obj.getNom()+ "' );";
            etat.execute(requeteProc);
            this.createListeLocataires(this.selectByName(obj.getNom()).getId(), obj.getListe());
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean createListeLocataires(int id, ArrayList<Locataire> liste) {
        try {
            Statement etat = this.connection.createStatement();
            for(int i=0; i<liste.size();i++){
                String requete2 ="Insert into locataire_liste VALUES ("+ id + " , "+ liste.get(i).getId()+ " );";
                etat.execute(requete2);
                System.out.println("ok");
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(ListeDeDiffusion obj) {
        try {
            deleteListeLocataires(obj.getId());
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from listediffusion where ID_listeDiff=" + obj.getId());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteListeLocataires(int id) {
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from locataire_liste where ID_listeDiff=" + id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ListeDeDiffusion obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update listediffusion set Nom_liste='"
                    +obj.getNom()+"' where ID_listeDiff="+obj.getId()+" ;";
            etat.execute(requeteProc);
            deleteListeLocataires(obj.getId());
            createListeLocataires(obj.getId(), obj.getListe());
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } 
    }

    @Override
    public ListeDeDiffusion selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from listeDiffusion where ID_listeDiff=" + id);
            res.next();
            ArrayList<Locataire> locataires = (ArrayList<Locataire>) this.getAllLocataires(id);
            return new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                    res.getString("Nom_liste"),
                    locataires
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public ListeDeDiffusion selectByName(String nom) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from listediffusion where Nom_liste='" + nom + "'");
            res.next();
            ArrayList<Locataire> locataires = (ArrayList<Locataire>) this.getAllLocataires(res.getInt("ID_listeDiff"));
            return new ListeDeDiffusion(res.getInt("ID_listeDiff"), res.getString("Nom_liste"), locataires);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public List<ListeDeDiffusion> getAll() {

        List<ListeDeDiffusion> allListes = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from listediffusion");
            while (res.next()) {
                
                ArrayList<Locataire> locataires = (ArrayList<Locataire>) this.getAllLocataires(res.getInt("ID_listeDiff"));//(this.selectById(res.getInt("ID_listeDiff")));
                allListes.add(new ListeDeDiffusion(
                    res.getInt("ID_listeDiff"),
                    res.getString("Nom_liste"),
                    locataires
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allListes;
    }
    
    public List<Locataire> getAllLocataires(int id) {

        List<Locataire> allLocataires = new ArrayList<>();
        try {//distinct
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_locataire from locataire_liste where ID_listeDiff="+id);
            Locataire_DAO locataires = new Locataire_DAO(this.connection);
            while (res.next()) {
                allLocataires.add(locataires.selectById(res.getInt("ID_locataire")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allLocataires;
    }
}