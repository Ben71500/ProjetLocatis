package DAO;

import DAO.DAO;
import Locatis.Appartement;
import Locatis.Batiment;
import Locatis.Locataire;
import Locatis.Maison;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Locataire_DAO extends DAO<Locataire>{
    
    public Locataire_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Locataire obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into locataire VALUES ('"+ obj.getId()+ "' , '"
                    + obj.getNom()+ "' , '"
                    + obj.getPrenom()+ "' , '"
                    + obj.getAge()+ "' , "
                    + obj.getAnciennete().getDateSQL()+ " , '"
                    + obj.getMail()+ "' , '"
                    + obj.getTelephone()+ "' );";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Locataire obj) {
        try {
            Statement etat = this.connection.createStatement();
            //return !etat.execute("delete from locataire, habiter where ID_locataire=" + obj.getId());
            return !etat.execute("delete from locataire where ID_locataire=" + obj.getId()) /*&& !etat.execute("delete from habiter where ID_locataire="+obj.getId())*/;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Locataire obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update locataire set Nom='"
                    +obj.getNom()+"' , Prenom='"
                    +obj.getPrenom()+"' , Age='"
                    +obj.getAge()+"' , Anciennete="
                    +obj.getAnciennete().getDateSQL()+" , Mail='"
                    +obj.getMail()+"' , Telephone='"
                    +obj.getTelephone()+"' where ID_locataire="
                    +obj.getId()+" ;";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            return false;
        } 
    }

    @Override
    public Locataire selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where ID_locataire=" + id);
            res.next();
            return new Locataire(res.getInt("ID_locataire"),
                    res.getString("Nom"),
                    res.getString("Prenom"),
                    res.getInt("Age"),
                    this.getMyDate(res.getDate("Anciennete")),
                    res.getString("Mail"),
                    res.getString("Telephone")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Locataire selectByName(String nom) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where Nom='" + nom + "'");
            res.next();

            return new Locataire(res.getInt("ID_locataire"), res.getString("Nom"), res.getString("Prenom"), res.getInt("Age"), this.getMyDate(res.getDate("Anciennete")), res.getString("Mail"), res.getString("Telephone"));

        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Locataire> getAll() {

        List<Locataire> allLocataire = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire");
            while (res.next()) {
                        allLocataire.add(new Locataire(
                        res.getInt("ID_locataire"),
                        res.getString("Nom"),
                        res.getString("Prenom"),
                        res.getInt("Age"),
                        this.getMyDate(res.getDate("Anciennete")),
                        res.getString("Mail"),
                        res.getString("Telephone")
                ));
            }
        } catch (SQLException ex) {
            return allLocataire;
        }
        return allLocataire;
    }
    
    public List<Locataire> getSearch(String texte) {

        List<Locataire> allLocataire = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where Nom like'%" + texte + "%'");
            while (res.next()) {
                        allLocataire.add(new Locataire(
                        res.getInt("ID_locataire"),
                        res.getString("Nom"),
                        res.getString("Prenom"),
                        res.getInt("Age"),
                        this.getMyDate(res.getDate("Anciennete")),
                        res.getString("Mail"),
                        res.getString("Telephone")
                ));
            }
        } catch (SQLException ex) {
            return allLocataire;
        }
        return allLocataire;
    }
    
    public List<Locataire> getRequete(String requete) {

        List<Locataire> allLocataire = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery(requete);
            while (res.next()) {
                        allLocataire.add(new Locataire(
                        res.getInt("ID_locataire"),
                        res.getString("Nom"),
                        res.getString("Prenom"),
                        res.getInt("Age"),
                        this.getMyDate(res.getDate("Anciennete")),
                        res.getString("Mail"),
                        res.getString("Telephone")
                ));
            }
        } catch (SQLException ex) {
            return allLocataire;
        }
        return allLocataire;
    }
    
    public List<Batiment> getLocation(int id){
        List<Batiment> allBatiments = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_batiment from habiter where ID_locataire=" + id);
            while (res.next()) {
                allBatiments.add(selectBatimentById(res.getInt("ID_batiment")));
            }
            return allBatiments;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    public Batiment selectBatimentById(int id){
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where ID_batiment=" + id);
            res.next();
            if(res.getString("NumeroAppartement")==null)   
                return new Maison(res.getInt("ID_batiment"),
                    res.getString("Adresse"));
            else
                return new Appartement(res.getInt("ID_batiment"),
                    res.getString("Adresse"),
                    res.getInt("NombreEtage"),
                    res.getInt("NumeroAppartement")
                );
        } catch (SQLException ex) {
            return null;
        }
    }
}
