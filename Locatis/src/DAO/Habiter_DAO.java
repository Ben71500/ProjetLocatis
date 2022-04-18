/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.DAO;
import Locatis.Habiter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benjamin
 */
public class Habiter_DAO extends DAO<Habiter>{
    
    public Habiter_DAO(Connection connection) {
        super(connection);
    }
    
    @Override
    public boolean create(Habiter obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into habiter VALUES ('"+ obj.getID_batiment()+ "' , '"+ obj.getID_locataire()+ "' );";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Habiter obj) {
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from habiter where ID_batiment=" + obj.getID_batiment()+" AND ID_locataire = "+obj.getID_locataire());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Habiter obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update locataire set ID_locaire='"+obj.getID_locataire()+"' where ID_batiment="+obj.getID_batiment()+" ;";
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            return false;
        } 
    }

    @Override
    public Habiter selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where ID_locataire=" + id);
            res.next();
            return new Habiter(res.getInt("ID_batiment"),
                    res.getInt("ID_locataire")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Habiter selectByName(String nom) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where Nom='" + nom + "'");
            res.next();

            return new Habiter(res.getInt("ID_batiment"), res.getInt("ID_locataire"));

        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Habiter> getAll() {

        List<Habiter> allHabiter = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire");
            while (res.next()) {
                        allHabiter.add(new Habiter(
                        res.getInt("ID_batiment"),
                        res.getInt("ID_locataire")
                ));
            }
        } catch (SQLException ex) {
            return allHabiter;
        }
        return allHabiter;
    }
    
     public List<Habiter> getSearch(String texte) {

        List<Habiter> allHabiter = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where Nom like'%" + texte + "%'");
            while (res.next()) {
                        allHabiter.add(new Habiter(
                        res.getInt("ID_batiment"),
                        res.getInt("ID_locataire")
                ));
            }
        } catch (SQLException ex) {
            return allHabiter;
        }
        return allHabiter;
    }
}
