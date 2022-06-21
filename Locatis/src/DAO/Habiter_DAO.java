package DAO;

import Objets_Locatis.Appartement;
import Objets_Locatis.Maison;
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
public class Habiter_DAO{
    
    private Connection connection;
    
    public Habiter_DAO(Connection connection) {
        this.connection = connection;
    }
    
    
    public boolean create(int idBatiment, int idLocataire) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into habiter VALUES ('"+ idLocataire + "' , '"+ idBatiment + "' );";
            System.out.println(requeteProc);
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /*public boolean create(Habiter obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into habiter VALUES ('"+ obj.getID_batiment()+ "' , '"+ obj.getID_locataire()+ "' );";
            System.out.println(requeteProc);
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }*/
    
    
    public boolean delete(int idBatiment, int idLocataire) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc = "delete from habiter where ID_batiment=" + idBatiment+" AND ID_locataire = "+idLocataire;
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            return false;
        }
    }

    /*@Override
    public boolean delete(Habiter obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc = "delete from habiter where ID_batiment=" + obj.getID_batiment()+" AND ID_locataire = "+obj.getID_locataire();
            System.out.println(requeteProc);
            etat.execute(requeteProc);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }*/

    /*@Override
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
    }*/

    /*@Override
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
    }*/

    /*@Override
    public Habiter selectByName(String nom) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where Nom='" + nom + "'");
            res.next();

            return new Habiter(res.getInt("ID_batiment"), res.getInt("ID_locataire"));

        } catch (SQLException ex) {
            return null;
        }
    }*/

    /*@Override
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
    }*/
    
     /*public List<Habiter> getSearch(String texte) {

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
    }*/
     
    public List<Appartement> getAppartementByIdLocataire(int id){
         List<Appartement> appartement = new ArrayList<>();
         try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where ID_batiment in (select ID_batiment from habiter where ID_locataire = "+id+") AND NumeroAppartement is not NULL;");
            while (res.next()) {
                    appartement.add(new Appartement(
                        res.getInt("ID_batiment"),
                        res.getString("NumeroRue"),
                        res.getString("NomRue"),
                        res.getString("Ville"),
                        res.getString("CodePostal"),
                        res.getInt("NumeroAppartement"),
                        res.getInt("NombreEtage")
                    )
                );
            }
            return appartement;
        } catch (SQLException ex) {
            return appartement;
        }
     }
     
     public List<Maison> getMaisonByIdLocataire(int id){
         List<Maison> maison = new ArrayList<>();
         try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where ID_batiment in (select ID_batiment from habiter where ID_locataire = "+id+") AND NumeroAppartement is NULL;");
            while (res.next()) {
                    maison.add(new Maison(
                        res.getInt("ID_batiment"),
                        res.getString("NumeroRue"),
                        res.getString("NomRue"),
                        res.getString("Ville"),
                        res.getString("CodePostal")
                    )
                );
            }
            return maison;
        } catch (SQLException ex) {
            return maison;
        }
     }
}
