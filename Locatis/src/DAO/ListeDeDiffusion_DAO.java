package DAO;

import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Personne;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de se connecter à la base de données pour la table Liste
 * de diffusion et d'effectuer divers actions sur la table
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
     * Méthode qui crée une liste de locataires dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne retourne false
     * @param id : id de la liste de diffusion
     * @param liste : Liste de Personnes
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
     * Méthode qui crée une liste d'utilisateurs dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne retourne false
     * @param id : id de la liste de diffusion
     * @param liste : Liste de Personnes
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
     * Méthode qui supprime une liste de diffusion dans la base de données
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
     * Méthode qui supprime les liens entre une liste et ses Utilisateurs / Locataires dans la base de données
     * @param id : id de la liste de diffusion
     * @return boolean
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
     * Méthode qui modifie une liste dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
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
     * Méthode qui récupére une liste de diffusion grâce à son id dans la base de données
     * @exception SQLException si la requête n'aboutie pas retourne retourne null
     * @param id : id de la liste de diffusion
     * @return ListeDeDiffusion
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
     * Méthode qui récupère le dernier id inséré dans la table listeDiffusion
     * @exception SQLException si la requête n'aboutie pas retourne 0
     * @return int
     */
    @Override
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
     * Méthode qui récupère toutes les listes de diffusion de la base de données
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste de listes de diffusion incrémentées
     * @return List<ListeDeDiffusion>
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
     * Méthode qui récupère tous les locataires de la base de données avec l'id d'une liste de diffusion
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste de personnes incrémentées
     * @param id : id d'une liste de diffusion
     * @return List<Personne>
     */
    public List<Personne> getAllLocataires(int id) {

        List<Personne> allLocataires = new ArrayList<>();
        try {
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
     * Méthode qui récupère tous les utilisateurs de la base de données avec l'id d'une liste de diffusion
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'utilisateurs incrémentés
     * @param id : id d'une liste de diffusion
     * @return List<Utilisateur>
     */
    public List<Personne> getAllUtilisateurs(int id) {

        List<Personne> allUtilisateurs = new ArrayList<>();
        try {
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
     * Méthode qui récupère tous les id des listes de diffusion auxquels l'id locataire est associé
     * @exception SQLException si la requête n'aboutie pas retourne la liste null ou la liste d'entiers incrémentés
     * @param id : id locataire
     * @return List<Integer>
     */
    public List<Integer> searchListLocataireByIdLocataire(int id){
        List<Integer> allList = new ArrayList<>();
        try {
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