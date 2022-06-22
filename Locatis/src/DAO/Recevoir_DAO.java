package DAO;

import Objets_Locatis.ListeDeDiffusion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de se connecter à la base de données pour la table Recevoir et d'effectuer divers actions sur la table
 * @author Benjamin Mathilde
 */
public class Recevoir_DAO {
    
    private Connection connection;

    /**
     * Constructeur de l'objet Recevoir_DAO
     * @param connection
     */
    public Recevoir_DAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Méthode qui crée un lien entre une campagne et une liste de diffusion dans la table recevoir
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param idCampagne : id d'une camapagne
     * @param obj : List de listes de diffusion
     * @return boolean
     */
    public boolean create(int idCampagne, List<ListeDeDiffusion> obj) {
        try {
            boolean ok = true;
            Statement statement = this.connection.createStatement();
            for(int i=0;i<obj.size();i++){
                ok = ok && !statement.execute("insert into recevoir (ID_campagne, ID_listeDiff) values("
                        + idCampagne + " , "
                        + obj.get(i).getId() + " )"
                );
            }
            return ok;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui supprime un lien entre une campagne et une liste de diffusion dans la table recevoir
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param idCampagne : id d'une camapagne
     * @return boolean
     */
    public boolean delete(int idCampagne) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("delete from recevoir where ID_campagne=" + idCampagne);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui supprime un lien entre une campagne et une liste de diffusion dans la table recevoir
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param idListe : id d'une liste de diffusion
     * @return boolean
     */
    public boolean deleteByListe(int idListe) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("delete from recevoir where ID_listeDiff=" + idListe);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui met à jour la table recevoir : elle supprime toutes les listes d'une campagne et les ajoute avec la liste à jour
     * @param idCampagne : id de la campagne qui a été mise à jour
     * @param obj : nouvelles listes de listes de diffusion
     * @return true si tout s'est bien passé
     */
    public boolean update(int idCampagne, List<ListeDeDiffusion> obj) {
        return this.delete(idCampagne) && this.create(idCampagne, obj);
    }
    
    /**
     * Méthode qui recupére une liste de diffusion par un id de campagne 
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param idCampagne : id d'une campagne
     * @return List<ListeDeDiffusion>
     */
    public List<ListeDeDiffusion> getListes(int idCampagne){
        try{
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_listeDiff from recevoir where ID_campagne=" + idCampagne);
            ListeDeDiffusion_DAO listeDAO = new ListeDeDiffusion_DAO(this.connection);
            List<ListeDeDiffusion> listes = new ArrayList<>();
            while (res.next()) {
                listes.add(listeDAO.selectById(res.getInt("ID_listeDiff")));
            }
            return listes;
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     * Méthode qui retourne la liste des emails de toutes les listes de diffusion d'une campagne
     * @param idCampagne : id de la campagne
     * @return la liste des emails
     */
    public ArrayList<String> getListeEmails(int idCampagne){
        ArrayList<ListeDeDiffusion> lesListes = (ArrayList<ListeDeDiffusion>) this.getListes(idCampagne);
        ArrayList<String> listeEmails = new ArrayList<>();
        for(int i=0; i<lesListes.size(); i++){
            for(int j=0; j<lesListes.get(i).getListe().size(); j++){
                if(!listeEmails.contains(lesListes.get(i).getListe().get(j).getMail()))
                    listeEmails.add(lesListes.get(i).getListe().get(j).getMail());
            }
        }
        return listeEmails;
    }
}