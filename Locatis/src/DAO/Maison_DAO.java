/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.DAO;
import Locatis.Maison;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benja
 */
public class Maison_DAO extends DAO<Maison>{
    
    public Maison_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Maison obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="Insert into logement (`ID_batiment`, `Adresse`) VALUES ("+ obj.getID()+ " , '"+ obj.getAdresse()+ "' );";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Maison obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="DELETE FROM logement where ID_batiment = "+obj.getID()+" ;";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Maison obj) {
        try {
            Statement statement = this.connection.createStatement();
            System.out.println("Coucou");
            String sql = "update logement set "
                    + "Adresse='" + obj.getAdresse()
                    + "' where  ID_batiment=" + obj.getID();
            statement.execute(sql);
            System.out.println(sql);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }

    @Override
    public Maison selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where ID_batiment=" + id +" AND NumeroAppartement IS NULL");
            res.next();
            return new Maison(res.getInt("ID_batiment"),
                    res.getString("Adresse")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Maison selectByName(String adresse) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where Adresse='" + adresse + "' AND NumeroAppartement IS NULL");
            res.next();
            return new Maison(res.getInt("ID_batiment"), res.getString("Adresse"));

        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Maison> getAll() {

        List<Maison> allMaison = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_batiment, adresse from logement where NumeroAppartement IS NULL ;");
            while (res.next()) {
                        allMaison.add(new Maison(
                        res.getInt("ID_batiment"),
                        res.getString("Adresse"))
                );
            }
        } catch (SQLException ex) {
            return allMaison;
        }
        return allMaison;
    }
}
