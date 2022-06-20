package DAO;

import DAO.Connexion;
import DAO.ConnectionBDD;
import DAO.DAO;
import Objets_Locatis.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class permettant de se connecter à la base de donnée pour la table Utilisateur et d'effectuer divers action sur la table
 * @author Benjamin Mathilde
 */
public class Utilisateurs_DAO extends DAO<Utilisateur> {
    
    /**
     * Constructeur de l'objet Utilisateur_DAO
     */
    public Utilisateurs_DAO() {
        super(ConnectionBDD.getInstance(new Connexion()));
    }
    
    /**
     * Constructeur de l'objet Utilisateur_DAO
     * @param connection
     */
    public Utilisateurs_DAO(Connection connection) {
        super(connection);
    }
    
    /**
     * Méthode qui augmente les droit d'un utilisateur dans la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param cat : catégorie utilisateur
     * @param id : id utilisateur
     */
    public void augmenterLesDroits(String cat, int id){
        try {
            Statement statement = this.connection.createStatement();
            String sql = "update utilisateur set CAT='" + cat +"' "+ "where  ID_utilisateur=" + id+"";
            statement.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Méthode qui crée un Utilisateur en base de donnée
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
     * Méthode qui supprime un Utilisateur en base de donnée
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
     * Méthode qui modifie un Utilisateur en base de donnée
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
     * Méthode qui selectionne un Utilisateur par son identifiant de base de donnée
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
     * Méthode qui selectionne un Utilisateur par son login de base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param login : login de l'utilisateur
     * @return Utilisateur
     */
    @Override
    public Utilisateur selectByName(String login) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from utilisateur where login='" + login + "'");
            res.next();

            return new Utilisateur(res.getInt("ID_utilisateur"), res.getString("login"), res.getString("Mdp"), res.getString("CAT"), res.getString("Email"), res.getString("Password"));

        } catch (SQLException ex) {
            return null;
        }
    }

    /**
     * Méthode qui récupére l'intégralité des Utilisateur générale de la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Utilisateur incrémenter
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
     * Méthode qui selectionne tout les Utilisateur de grade utilisateur 1 et 2 de la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Utilisateur incrémenter
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
     * Méthode qui selectionne tout les grades d'Utilisateur diffèrent du grade administrateur de la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Utilisateur incrémenter
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
     * Méthode qui execute une requete et retourne une liste d'Utilisateur en fonction
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Utilisateur incrémenter
     * @param requete :  requete sql pour la selection des Utilisateurs
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
                ));            }
        } catch (SQLException ex) {
            return allUtilisateurs;
        }
        return allUtilisateurs;
    }
    
    /**
     * Méthode qui supprime l'Utilisateur d'une liste de diffusion
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
