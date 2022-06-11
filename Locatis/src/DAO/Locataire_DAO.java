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

    public Locataire selectByIdCampagne(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where ID_locataire=" + id);
            res.next();
            return new Locataire(res.getInt("ID_locataire"),
                    res.getString("Nom"),
                    res.getString("Prenom"),
                    res.getInt("Age"),
                    this.getMyDate(res.getDate("DateDeNaissance")),
                    res.getString("Mail"),
                    res.getString("Telephone")
            );
        } catch (SQLException ex) {
            return null;
        }
    }
    
    @Override
    public boolean create(Locataire obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into locataire VALUES ('"+ obj.getId()+ "' , '"
                    + obj.getNom()+ "' , '"
                    + obj.getPrenom()+ "' , '"
                    + obj.getAge()+ "' , "
                    + obj.getDateDeNaissance().getDateSQL()+ " , '"
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
            deleteAllLogementByIdLocataire(obj);
            deleteAllListesByIdLocataire(obj);
            return !etat.execute("delete from locataire where ID_locataire=" + obj.getId());
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
                    +obj.getAge()+"' , DateDeNaissance="
                    +obj.getDateDeNaissance().getDateSQL()+" , Mail='"
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
                    this.getMyDate(res.getDate("DateDeNaissance")),
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

            return new Locataire(res.getInt("ID_locataire"), res.getString("Nom"), res.getString("Prenom"), res.getInt("Age"), this.getMyDate(res.getDate("DateDeNaissance")), res.getString("Mail"), res.getString("Telephone"));

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
                        this.getMyDate(res.getDate("DateDeNaissance")),
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
                        this.getMyDate(res.getDate("DateDeNaissance")),
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
                        this.getMyDate(res.getDate("DateDeNaissance")),
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
                    res.getString("NumeroRue"),
                    res.getString("NomRue"),
                    res.getString("Ville"),
                    res.getString("CodePostal"));
            else
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
    
    public int selectAgeBefore18(){
        String requete = "";
        try{
            Statement statement = this.connection.createStatement();
            requete = "Select COUNT(ID_locataire) as nombre FROM Locataire where Age <= 18";
            System.out.println(requete+"\n");
            ResultSet res = statement.executeQuery(requete);
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            System.out.println("error"+requete+"\n");
            return -1;
        }
    }
    
    public int selectAgeBetween19and35(){
        try{
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_locataire) as nombre FROM Locataire where Age <= 35 && Age >= 19");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public int selectAgeBetween36and60(){
        try{
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_locataire) as nombre FROM Locataire where Age <= 60 && Age >= 36");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public int selectAgeAfter61(){
        try{
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_locataire) as nombre FROM Locataire where Age > 60");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public int selectAllOfLocataire(){
        try{
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_locataire) as nombre FROM Locataire");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }
    }
    
    public boolean deleteAllLogementByIdLocataire(Locataire obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from habiter where ID_locataire=" + obj.getId());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteAllListesByIdLocataire(Locataire obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from locataire_liste where ID_locataire=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }
}
