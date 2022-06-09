/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DAO;
import Locatis.Appartement;
import Locatis.Locataire;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author benja
 */
public class Appartement_DAO extends DAO<Appartement>{
    
    public Appartement_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Appartement obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc = "Insert into logement VALUES ('"+obj.getID()+"' , '"+obj.getNumeroRue()+"' , '"+obj.getNomRue()+"' , '"+obj.getVille()+"' , '"+obj.getCodePostal()+"' , '"+obj.getApart()+"' , '"+obj.getEtage()+"' );";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Appartement obj) {
        try {
            Statement etat;
            deleteAllLogementByIdLogementId(obj);
            etat = this.connection.createStatement();
            String requeteProc = "DELETE FROM logement where ID_batiment = "+obj.getID()+" ;";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Appartement obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update logement set NumeroRue ='"+obj.getNumeroRue()+"' , NomRue ='"+obj.getNomRue()+"' , Ville ='"+obj.getVille()+"' , CodePostal ='"+obj.getCodePostal()+"' ,NumeroAppartement='"+obj.getApart()+"' , NombreEtage='"+obj.getEtage()+"' where ID_batiment="+obj.getID()+" ;";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            return false;
        }
        
    }

    @Override
    public Appartement selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where ID_batiment=" + id +" AND NumeroAppartement IS NOT NULL");
            res.next();
            return new Appartement(res.getInt("ID_batiment"),
                    res.getString("NumeroRue"),
                    res.getString("NomRue"),
                    res.getString("Ville"),
                    res.getString("CodePostal"),
                    res.getInt("NumeroAppartement"),
                    res.getInt("NombreEtage")
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    @Override
    public Appartement selectByName(String adresse) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where Adresse='" + adresse + "' AND NumeroAppartement IS NOT NULL");
            res.next();

            return new Appartement(res.getInt("ID_batiment"),
                    res.getString("NumeroRue"),
                    res.getString("NomRue"),
                    res.getString("Ville"),
                    res.getString("CodePostal"),
                    res.getInt("NumeroAppartement"),
                    res.getInt("NombreEtage")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Appartement> getAll() {

        List<Appartement> allAppartement = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where NumeroAppartement IS NOT NULL");
            while (res.next()) {
                        allAppartement.add(new Appartement(
                            res.getInt("ID_batiment"),
                            res.getString("NumeroRue"),
                            res.getString("NomRue"),
                            res.getString("Ville"),
                            res.getString("CodePostal"),
                            res.getInt("NumeroAppartement"),
                            res.getInt("NombreEtage")
                        ));
            }
        } catch (SQLException ex) {
            return allAppartement;
        }
        return allAppartement;
    }
    
    public int getLogementEmpty(){
        try {
           Statement statement = this.connection.createStatement();
           ResultSet res = statement.executeQuery("Select COUNT(ID_batiment) as nombre from logement where ID_batiment NOT IN (Select ID_batiment from habiter)");
           res.next();
           return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public int getLogementFull(){
        try {
           Statement statement = this.connection.createStatement();
           ResultSet res = statement.executeQuery("Select COUNT(ID_batiment) as nombre from logement where ID_batiment IN (Select ID_batiment from habiter)");
           res.next();
           return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public int getAllLogement(){
        try {
           Statement statement = this.connection.createStatement();
           ResultSet res = statement.executeQuery("Select COUNT(ID_batiment) as nombre from logement");
           res.next();
           return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public boolean deleteAllLogementByIdLogementId(Appartement obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from habiter where ID_batiment=" + obj.getID());
        } catch (SQLException ex) {
            return false;
        }
    }
    
}
