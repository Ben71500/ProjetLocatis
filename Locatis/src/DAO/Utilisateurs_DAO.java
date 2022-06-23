package DAO;

import Objets_Locatis.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de se connecter à la base de données pour la table Utilisateur et d'effectuer diverses actions sur la table
 * @author Benjamin Mathilde
 */
public class Utilisateurs_DAO extends DAO<Utilisateur> {
    
    /**
     * Constructeur de l'objet Utilisateur_DAO
     * @param connection
     */
    public Utilisateurs_DAO(Connection connection) {
        super(connection);
    }

    /**
     * Méthode qui crée un Utilisateur dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param Utilisateur
     * @return boolean
     */
    @Override
    public boolean create(Utilisateur obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("insert into utilisateur (ID_utilisateur, login, Mdp, CAT, Email, Password) values("
                    + obj.getId() + " , '"
                    + obj.getLogin() + "' , '"
                    + obj.getMotDePasse() + "' , '"
                    + obj.getCat() + "' , '"
                    + obj.getMail() + "' , '"
                    + obj.getPassword() + "')"
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui supprime un Utilisateur dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param Utilisateur
     * @return boolean
     */
    @Override
    public boolean delete(Utilisateur uti) {
        try {
            Statement statement = this.connection.createStatement();
            deleteAllListesByIdUtilisateur(uti);
            return !statement.execute("delete from utilisateur where ID_utilisateur=" + uti.getId());
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Méthode qui modifie un Utilisateur dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean update(Utilisateur obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("update utilisateur set "
                    + "login='" + obj.getLogin()+ "' , "
                    + "Mdp='" + obj.getMotDePasse()+ "' , "
                    + "CAT='" + obj.getCat() +"' , "
                    + "Email='" + obj.getMail() + "' , "
                    + "Password='" + obj.getPassword() + "' "
                    + "where  ID_utilisateur=" + obj.getId()
            );
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Méthode qui selectionne un Utilisateur par son identifiant dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param id
     * @return Utilisateur
     */
    @Override
    public Utilisateur selectById(int id) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where ID_utilisateur=" + id);
            res.next();
            return new Utilisateur(res.getInt("ID_utilisateur"),
                    res.getString("login"),
                    res.getString("Mdp"),
                    res.getString("CAT"),
                    res.getString("Email"),
                    res.getString("Password")
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     * Méthode qui récupère un utilisateur par le dernier id inséré dans la base de données
     * @exception SQLException si la requête n'aboutie pas, retourne 0
     * @return int
     */
    @Override
    public int getLastInsertId(){
        try{
            Statement etat = this.connection.createStatement();
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_Utilisateur from utilisateur");
            res.next();
            return res.getInt("ID_Utilisateur");
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * Méthode qui sélectionne un Utilisateur par son login dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param login : login de l'utilisateur
     * @return Utilisateur
     */
    public Utilisateur selectByName(String login) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where login='" + login + "'");
            res.next();
            return new Utilisateur(res.getInt("ID_utilisateur"), res.getString("login"),
                res.getString("Mdp"), res.getString("CAT"), res.getString("Email"), res.getString("Password"));
        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Méthode qui récupère l'intégralité des Utilisateurs de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'utilisateurs incrémentés
     * @return List<Utilisateur>
     */
    @Override
    public List<Utilisateur> getAll() {
        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur");
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                        res.getInt("ID_utilisateur"),
                        res.getString("login"),
                        res.getString("Mdp"),
                        res.getString("CAT"),
                        res.getString("Email"),
                        res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    /**
     * Méthode qui sélectionne tous les Utilisateurs de catégorie utilisateur 1 et 2 de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'utilisateurs incrémentés
     * @return List<Utilisateur>
     */
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where CAT='uti1' OR CAT='uti2'");
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                        res.getInt("ID_utilisateur"),
                        res.getString("login"),
                        res.getString("Mdp"),
                        res.getString("CAT"),
                        res.getString("Email"),
                        res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    /**
     * Méthode qui sélectionne toutes les catégories d'Utilisateurs différentes de la catégorie administrateur de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'utilisateurs incrémentés
     * @return List<Utilisateur>
     */
    public List<Utilisateur> getAllUtilisateursEtGestionnaires() {
        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where CAT!='adm'");
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                        res.getInt("ID_utilisateur"),
                        res.getString("login"),
                        res.getString("Mdp"),
                        res.getString("CAT"),
                        res.getString("Email"),
                        res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    /**
     * Méthode qui exécute une requete et retourne une liste d'Utilisateurs en fonction
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'Utilisateurs incrémentés
     * @param requete : requete sql pour la séléction des Utilisateurs
     * @return List<Utilisateur>
     */
    public List<Utilisateur> getRequete(String requete) {
        List<Utilisateur> allUtilisateurs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery(requete);
            while (res.next()) {
                allUtilisateurs.add(new Utilisateur(
                    res.getInt("ID_utilisateur"),
                    res.getString("login"),
                    res.getString("Mdp"),
                    res.getString("CAT"),
                    res.getString("Email"),
                    res.getString("Password")
                ));
            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    /**
     * Méthode qui supprime un utilisateur des listes de diffusion
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    public boolean deleteAllListesByIdUtilisateur(Utilisateur obj){
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from utilisateur_liste where ID_utilisateur=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }
}