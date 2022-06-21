package DAO;

import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Locataire;
import Objets_Locatis.Personne;
import Objets_Locatis.Utilisateur;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class permettant de se connecter à la base de donnée pour la table Liste de diffusion et d'effectuer divers action sur la table
 * @author Benjamin Mathilde
 */
public class ListeDeDiffusion_DAO extends DAO<ListeDeDiffusion>{
    
    /**
     * Constructeur de l'objet ListeDiffusion_DAO
     * @param connection
     */
    public ListeDeDiffusion_DAO(Connection connection) {
        super(connection);
    }

    /**
     * Méthode qui crée une liste de diffusion de type locataire ou utilisateur
     * @exception SQLException si la requête n'aboutie pas retourne retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean create(ListeDeDiffusion obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc = "Insert into listediffusion VALUES ('"+ obj.getId()+ "' , '"+ obj.getNom()+ "' );";
            boolean b = !etat.execute(requeteProc);
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_listeDiff from listediffusion");
            res.next();
            int id=res.getInt("ID_listeDiff");
            switch(obj.getTypeListe()){
                case "locataire" -> b = b && createListeLocataires (id,obj.getListe());
                case "utilisateur" -> b = b && createListeUtilisateurs (id,obj.getListe());
            }
            return b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui crée une liste de locataire dans la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne false
     * @param id : id de la liste de diffusion
     * @param liste : Liste de Personne
     * @return boolean
     */
    public boolean createListeLocataires(int id, ArrayList<Personne> liste) {
        try {
            Statement etat = this.connection.createStatement();
            Boolean b = true;
            for(int i=0; i<liste.size();i++){
                String requete ="Insert into locataire_liste VALUES ("+ id + " , "+ liste.get(i).getId()+ " );";
                b = b && !etat.execute(requete);
            }
            return b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui crée une liste d'utilisateur dans la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne false
     * @param id : id de la liste de diffusion
     * @param liste : Liste de Personne
     * @return boolean
     */
    public boolean createListeUtilisateurs(int id, ArrayList<Personne> liste) {
        try {
            Statement etat = this.connection.createStatement();
            Boolean b = true;
            for(int i=0; i<liste.size();i++){
                String requete ="Insert into utilisateur_liste VALUES ("+ id + " , "+ liste.get(i).getId()+ " );";
                b = b && !etat.execute(requete);
            }
            return b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui supprime une liste de diffusion dans la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean delete(ListeDeDiffusion obj) {
        try {
            deleteListe(obj.getId());
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            Boolean b = recevoir.deleteByListe(obj.getId());
            Statement etat = this.connection.createStatement();
            return b && !etat.execute("delete from listediffusion where ID_listeDiff=" + obj.getId());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     *
     * @param id
     * @return
     */
    public boolean deleteListe(int id) {
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from locataire_liste where ID_listeDiff=" + id) && !etat.execute("delete from utilisateur_liste where ID_listeDiff=" + id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean update(ListeDeDiffusion obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update listediffusion set Nom_liste='"
                    +obj.getNom()+"' where ID_listeDiff="+obj.getId()+" ;";
            Boolean b = !etat.execute(requeteProc);
            deleteListe(obj.getId());
            switch(obj.getTypeListe()){
                case "locataire" -> b = b && createListeLocataires (obj.getId(),obj.getListe());
                case "utilisateur" -> b = b && createListeUtilisateurs (obj.getId(),obj.getListe());
            }
            return b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } 
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ListeDeDiffusion selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from listeDiffusion where ID_listeDiff=" + id);
            res.next();
            
            ArrayList<Personne> locataires = (ArrayList<Personne>) this.getAllLocataires(id);
            if(!locataires.isEmpty()){  
                return new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                        res.getString("Nom_liste"),
                        locataires
                );
            }else{
                ArrayList<Personne> utilisateurs = (ArrayList<Personne>) this.getAllUtilisateurs(id);
                return new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                        res.getString("Nom_liste"),
                        utilisateurs
                );
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }
    
    /**
     *
     * @return
     */
    public int getLastInsertId(){
        try{
            Statement etat = this.connection.createStatement();
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_Liste from listeDiffusion");
            res.next();
            int id = res.getInt("ID_Liste");
            return id;
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     *
     * @param nom
     * @return
     */
    @Override
    public ListeDeDiffusion selectByName(String nom) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from listediffusion where Nom_liste='" + nom + "'");
            res.next();
            ArrayList<Personne> locataires = (ArrayList<Personne>) this.getAllLocataires(res.getInt("ID_listeDiff"));
            if(!locataires.isEmpty()){  
                return new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                        res.getString("Nom_liste"),
                        locataires
                );
            }else{
                ArrayList<Personne> utilisateurs = (ArrayList<Personne>) this.getAllUtilisateurs(res.getInt("ID_listeDiff"));
                return new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                        res.getString("Nom_liste"),
                        utilisateurs
                );
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     *
     * @return
     */
    @Override
    public List<ListeDeDiffusion> getAll() {

        List<ListeDeDiffusion> allListes = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from listediffusion");
            while (res.next()) {
                
                ArrayList<Personne> locataires = (ArrayList<Personne>) this.getAllLocataires(res.getInt("ID_listeDiff"));
                if(!locataires.isEmpty()){
                    allListes.add( new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                            res.getString("Nom_liste"),
                            locataires
                    ));
                }else{
                    ArrayList<Personne> utilisateurs = (ArrayList<Personne>) this.getAllUtilisateurs(res.getInt("ID_listeDiff"));
                    allListes.add( new ListeDeDiffusion(res.getInt("ID_listeDiff"),
                            res.getString("Nom_liste"),
                            utilisateurs
                    ));
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allListes;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public List<Personne> getAllLocataires(int id) {

        List<Personne> allLocataires = new ArrayList<>();
        try {//distinct
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_locataire from locataire_liste where ID_listeDiff="+id);
            Locataire_DAO locataires = new Locataire_DAO(this.connection);
            while (res.next()) {
                allLocataires.add(locataires.selectById(res.getInt("ID_locataire")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allLocataires;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public List<Personne> getAllUtilisateurs(int id) {

        List<Personne> allUtilisateurs = new ArrayList<>();
        try {//distinct
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_utilisateur from utilisateur_liste where ID_listeDiff="+id);
            Utilisateurs_DAO utilisateurs = new Utilisateurs_DAO(this.connection);
            while (res.next()) {
                allUtilisateurs.add(utilisateurs.selectById(res.getInt("ID_utilisateur")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allUtilisateurs;
    }
    
    /**
     *
     * @param id
     * @return
     */
    public List<Integer> searchListLocataireByIdLocataire(int id){
        List<Integer> allList = new ArrayList<>();
        try {//distinct
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_listeDiff from locataire_liste where ID_locataire="+id);
            while (res.next()) {
                allList.add(res.getInt("ID_listeDiff"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allList;
    }
}