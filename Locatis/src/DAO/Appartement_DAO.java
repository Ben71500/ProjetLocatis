/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DAO;
import Locatis.Appartement;
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
            String requeteProc = "Insert into logement VALUES ('"+obj.getID()+"' , '"+obj.getAdresse()+"' , '"+obj.getApart()+"' , '"+obj.getEtage()+"' );";
            System.out.println(requeteProc);
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
            String requeteProc ="update logement set Adresse ='"+obj.getAdresse()+"' , NumeroAppartement='"+obj.getApart()+"' , NombreEtage='"+obj.getEtage()+"' where ID_batiment="+obj.getID()+" ;";
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
            ResultSet res = statement.executeQuery("Select * from logement where id=" + id +" AND NumeroAppartement IS NOT NULL");
            res.next();
            return new Appartement(res.getInt("ID_batiment"),
                    res.getString("Adresse"),
                    res.getInt("NumeroAppartement"),
                    res.getInt("NombreEtage")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Appartement selectByName(String adresse) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where Adresse='" + adresse + "' AND NumeroAppartement IS NOT NULL");
            res.next();

            return new Appartement(res.getInt("ID_batiment"), res.getString("Adresse"), res.getInt("NumeroAppartement"), res.getInt("NombreEtage"));

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
                        res.getString("Adresse"),
                        res.getInt("NumeroAppartement"),
                        res.getInt("NombreEtage"))
                );
            }
        } catch (SQLException ex) {
            return allAppartement;
        }
        return allAppartement;
    }
    
}
