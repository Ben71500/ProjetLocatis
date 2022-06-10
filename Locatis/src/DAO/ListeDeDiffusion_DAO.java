package DAO;

import Locatis.ListeDeDiffusion;
import Locatis.Locataire;
import Locatis.Personne;
import Locatis.Utilisateur;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListeDeDiffusion_DAO extends DAO<ListeDeDiffusion>{
    
    public ListeDeDiffusion_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(ListeDeDiffusion obj) {
        try {
            Statement etat = this.connection.createStatement();
            String requeteProc ="Insert into listediffusion VALUES ('"+ obj.getId()+ "' , '"+ obj.getNom()+ "' );";
            etat.execute(requeteProc);
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_listeDiff from listediffusion");
            res.next();
            int id=res.getInt("ID_listeDiff");
            switch(obj.getTypeListe()){
                case "locataire" -> createListeLocataires (id,obj.getListe());
                case "utilisateur" -> createListeUtilisateurs (id,obj.getListe());
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean createListeLocataires(int id, ArrayList<Personne> liste) {
        try {
            Statement etat = this.connection.createStatement();
            for(int i=0; i<liste.size();i++){
                String requete ="Insert into locataire_liste VALUES ("+ id + " , "+ liste.get(i).getId()+ " );";
                etat.execute(requete);
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean createListeUtilisateurs(int id, ArrayList<Personne> liste) {
        try {
            Statement etat = this.connection.createStatement();
            for(int i=0; i<liste.size();i++){
                String requete ="Insert into utilisateur_liste VALUES ("+ id + " , "+ liste.get(i).getId()+ " );";
                etat.execute(requete);
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(ListeDeDiffusion obj) {
        try {
            deleteListe(obj.getId());
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            recevoir.deleteByListe(obj.getId());
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from listediffusion where ID_listeDiff=" + obj.getId());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    public boolean deleteListe(int id) {
        try {
            Statement etat = this.connection.createStatement();
            return !etat.execute("delete from locataire_liste where ID_listeDiff=" + id) && !etat.execute("delete from utilisateur_liste where ID_listeDiff=" + id);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(ListeDeDiffusion obj) {
        try {
            Statement etat;
            etat = this.connection.createStatement();
            String requeteProc ="update listediffusion set Nom_liste='"
                    +obj.getNom()+"' where ID_listeDiff="+obj.getId()+" ;";
            etat.execute(requeteProc);
            deleteListe(obj.getId());
            switch(obj.getTypeListe()){
                case "locataire" -> createListeLocataires (obj.getId(),obj.getListe());
                case "utilisateur" -> createListeUtilisateurs (obj.getId(),obj.getListe());
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        } 
    }

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
    
    /*public List<Personne> getAllPersonnes(int id) {

        List<Personne> allPersonnes = new ArrayList<>();
        try {//distinct
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select ID_utilisateur from utilisateur_liste where ID_listeDiff="+id);
            Utilisateurs_DAO utilisateurs = new Utilisateurs_DAO(this.connection);
            while (res.next()) {
                allPersonnes.add(utilisateurs.selectById(res.getInt("ID_utilisateur")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return allPersonnes;
    }*/
    
    //public List<ListeDeDiffusion>
}