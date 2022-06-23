package DAO;

import Objets_Locatis.Appartement;
import Objets_Locatis.Batiment;
import Objets_Locatis.Locataire;
import Objets_Locatis.Maison;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de se connecter à la base de données pour la table Locataire et d'effectuer diverses actions sur la table
 * @author Benjamin Mathilde
 */
public class Locataire_DAO extends DAO<Locataire>{
    
    /**
     * Consturcteur de l'objet Locataire_DAO
     * @param connection
     */
    public Locataire_DAO(Connection connection) {
        super(connection);
    }
    
    /**
     * Méthode qui crée un locataire dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
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
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui supprime un locataire dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
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

    /**
     * Méthode qui modifie un locataire dans la base de données
     * @param obj
     * @return boolean
     */
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
            return !etat.execute(requeteProc);
        } catch (SQLException ex) {
            return false;
        } 
    }

    /**
     * Méthode qui récupère un Locataire par rapport à un id
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param id : id de locataire
     * @return Locataire
     */
    @Override
    public Locataire selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from locataire where ID_locataire=" + id);
            res.next();
            return new Locataire(res.getInt("ID_locataire"),
                    res.getString("Nom"),
                    res.getString("Prenom"),
                    this.getMyDate(res.getDate("DateDeNaissance")),
                    res.getString("Mail"),
                    res.getString("Telephone")
            );
        } catch (SQLException ex) {
            return null;
        }
    }
    
    /**
     * Méthode qui récupère un locataire par le dernier id inséré dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne 0
     * @return int
     */
    @Override
    public int getLastInsertId(){
        try{
            Statement etat = this.connection.createStatement();
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_Locataire from locataire");
            res.next();
            return res.getInt("ID_Locataire");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * Méthode qui récupère tous les locataires de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste de Locataires incrémentés
     * @return List<Locataire>
     */
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
    
    /**
     * Méthode qui exécute une requete et retourne une liste de Locataires en fonction
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste de Locataires incrémentés
     * @param requete : requete sql pour la selection des Locataires
     * @return List<Locataire>
     */
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
    
    /**
     * Méthode qui retourne la liste des batiments d'un locataire par son id
     * @exception SQLException si la requête n'aboutie pas retourne retourne null
     * @param id : id locataire
     * @return List<Batiment>
     */
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
    
    /**
     * Méthode qui récupère un batiment par rapport à son id
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param id : id Batiment
     * @return Batiment
     */
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
    
    /**
     * Méthode qui compte le nombre de locataires compris entre deux ages
     * @param liste : liste de locataires
     * @param ageMin : age minimum
     * @param ageMax : age maximum
     * @return int
     */
    public int compterLocataire(List<Locataire> liste, int ageMin, int ageMax){
        int nombre =0;
        for(int i=0; i<liste.size();i++){
            if(liste.get(i).getAge() >= ageMin && liste.get(i).getAge() < ageMax)
                nombre++;
        }
        return nombre;
    }
    
    /**
     * Méthode qui supprime le lien entre les batiments d'un locataire
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    public boolean deleteAllLogementByIdLocataire(Locataire obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from habiter where ID_locataire=" + obj.getId());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui supprime le locataire des listes de diffusion
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    public boolean deleteAllListesByIdLocataire(Locataire obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from locataire_liste where ID_locataire=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }
}