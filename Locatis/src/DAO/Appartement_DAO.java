package DAO;

import Objets_Locatis.Appartement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class permettant de se connecter à la base de donnée pour la table Appartement et d'effectuer divers actions sur la table
 * @author Benjamin Mathilde
 */
public class Appartement_DAO extends DAO<Appartement>{
    
    /**
     * Constructeur de l'objet Appartement_DAO
     * @param connection
     */
    public Appartement_DAO(Connection connection) {
        super(connection);
    }

    /**
     * Méthode qui ajoute un appartement dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean create(Appartement obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc = "Insert into logement VALUES ('"+obj.getID()+"' , '"+obj.getNumeroRue()+"' , '"+
                obj.getNomRue()+"' , '"+obj.getVille()+"' , '"+obj.getCodePostal()+"' , '"+obj.getApart()+"' , '"+obj.getEtage()+"' );";
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui supprime un appartement de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean delete(Appartement obj) {
        try {
            Statement etat;
            Boolean b = deleteAllLogementByIdLogementId(obj);
            etat = this.connection.createStatement();
            String requeteProc = "DELETE FROM logement where ID_batiment = "+obj.getID()+" ;";
            return !etat.execute(requeteProc) && b;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    /**
     * Méthode qui modifie un appartement de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean update(Appartement obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update logement set NumeroRue ='"+obj.getNumeroRue()+"' , NomRue ='"+obj.getNomRue()+"' , Ville ='"+obj.getVille()+"' , CodePostal ='"+obj.getCodePostal()+"' ,NumeroAppartement='"+obj.getApart()+"' , NombreEtage='"+obj.getEtage()+"' where ID_batiment="+obj.getID()+" ;";
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            return false;
        }
        
    }
    
    /**
     * Méthode qui récupére un appartement par son id dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param id
     * @return Appartement
     */
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

    /**
     * Méthode qui récupére un appartement par le dernier id inséré dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne 0
     * @return int
     */
    @Override
    public int getLastInsertId(){
        try{
            Statement etat = this.connection.createStatement();
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_Appartement from logement");
            res.next();
            return res.getInt("ID_Appartement");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * Méthode qui récupére la totalité des appartements dans une List d'appartements
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Appartement incrémentée
     * @return List<Appartement>
     */
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
    
    /**
     * Méthode qui récupére les logements vides dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne -1
     * @return int
     */
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
    
    /**
     * Méthode qui récupére les logements occupés de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne -1
     * @return int
     */
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
    
    /**
     * Méthode qui récupére le nombre total de logements de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne -1
     * @return int
     */
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
    
    /**
     * Méthode qui supprime les logements de la table habiter
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    public boolean deleteAllLogementByIdLogementId(Appartement obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from habiter where ID_batiment=" + obj.getID());
        } catch (SQLException ex) {
            return false;
        }
    }   
}