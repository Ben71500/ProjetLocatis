package DAO;

import Objets_Locatis.Maison;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de se connecter à la base de données pour la table Logement pour
 * les attributs n'ayant pas de numéro d'étage et d'effectuer divers actions sur la table
 * @author Benjamin Mathilde
 */
public class Maison_DAO extends DAO<Maison>{
    
    /**
     * Constructeur de l'objet Utilisateur_DAO
     * @param connection
     */
    public Maison_DAO(Connection connection) {
        super(connection);
    }

    /**
     * Méthode qui crée une Maison dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param Maison
     * @return
     */
    @Override
    public boolean create(Maison obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="Insert into logement (`ID_batiment`, `NumeroRue`, `NomRue`, `Ville`, `CodePostal`) VALUES ("
                    + obj.getID()+ " , '"
                    + obj.getNumeroRue()+ "' , '"
                    + obj.getNomRue()+"' , '"
                    + obj.getVille()+ "' , '"
                    + obj.getCodePostal()+ "')";
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui supprime une Maison dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param Maison
     * @return boolean
     */
    @Override
    public boolean delete(Maison obj) {
        try {
            Statement etat;
            deleteAllLogementByIdLogementId(obj);
            etat = this.connection.createStatement();
            String requeteProc ="DELETE FROM logement where ID_batiment = "+obj.getID()+" ;";
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Méthode qui modifie une Maison dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param Maison
     * @return boolean
     */
    @Override
    public boolean update(Maison obj) {
        try {
            Statement statement = this.connection.createStatement();
            String sql = "update logement set "
                    + "NumeroRue='" + obj.getNumeroRue()
                    + "' , NomRue='" + obj.getNomRue()
                    + "' , Ville='" + obj.getVille()
                    + "' , CodePostal='" + obj.getCodePostal()
                    + "' where  ID_batiment=" + obj.getID();
            return !statement.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui sélectionne une Maison par son identifiant dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param id : id Maison
     * @return Maison
     */
    @Override
    public Maison selectById(int id) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from logement where ID_batiment=" + id +" AND NumeroAppartement IS NULL");
            res.next();
            return new Maison(res.getInt("ID_batiment"),
                    res.getString("NumeroRue"),
                    res.getString("NomRue"),
                    res.getString("Ville"),
                    res.getString("CodePostal")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Méthode qui récupère la totalité des appartements dans une List de maisons
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste de Maisons incrémentées
     * @return List<Maison>
     */
    @Override
    public List<Maison> getAll() {

        List<Maison> allMaison = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_batiment, NumeroRue, NomRue, Ville, CodePostal from logement where NumeroAppartement IS NULL ;");
            while (res.next()) {
                    allMaison.add(new Maison(res.getInt("ID_batiment"),
                    res.getString("NumeroRue"),
                    res.getString("NomRue"),
                    res.getString("Ville"),
                    res.getString("CodePostal")
                    ));
            }
        } catch (SQLException ex) {
            return allMaison;
        }
        return allMaison;
    }
    
    /**
     * Méthode qui récupère une maison par le dernier id inséré dans la base de données
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
     * Méthode qui supprime les maisons de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param Maison
     * @return boolean
     */
    public boolean deleteAllLogementByIdLogementId(Maison obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from habiter where ID_batiment=" + obj.getID());
        } catch (SQLException ex) {
            return false;
        }
    }
}