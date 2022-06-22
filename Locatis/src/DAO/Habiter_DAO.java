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
 * Classe permettant de se connecter à la base de données pour la table Habiter et d'effectuer divers actions sur la table
 * @author Benjamin Mathilde
 */
public class Habiter_DAO{
    
    private Connection connection;
    
    /**
     * Constructeur
     * @param connection
     */
    public Habiter_DAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Méthode qui insère le lien entre un batiment et un locataire
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param idBatiment : id d'un batiment
     * @param idLocataire : id d'un locataire
     * @return boolean
     */
    public boolean create(int idBatiment, int idLocataire) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into habiter VALUES ('"+ idLocataire + "' , '"+ idBatiment + "' );";
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui supprime le lien entre un batiment et un locataire
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param idBatiment : id batiment
     * @param idLocataire : id locataire
     * @return boolean
     */
    public boolean delete(int idBatiment, int idLocataire) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc = "delete from habiter where ID_batiment=" + idBatiment+" AND ID_locataire = "+idLocataire;
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Méthode qui récupère tous les appartements d'un locataire
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'appartements incrémentés
     * @param id : id locataire
     * @return List<Appartement>
     */
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
     
    /**
     * Méthode qui récupère toutes les maisons d'un locataire
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste de maison incrémentées
     * @param id : id locataire
     * @return List<Maison>
     */
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