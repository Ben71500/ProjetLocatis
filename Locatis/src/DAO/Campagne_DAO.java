package DAO;

import Locatis.Campagne;
import Locatis.ListeDeDiffusion;
import Locatis.Locataire;
import Locatis.Personne;
import java.sql.*;
import java.util.*;

public class Campagne_DAO extends DAO<Campagne>{

    public Campagne_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            statement.execute("insert into campagne (ID_campagne, Titre_campagne, Date_Debut, Date_Fin, Heure, frequence, ID_utilisateur, Objet, Contenu) values("
                    + obj.getId() + " , '"
                    + obj.getTitre() + "' , "
                    + obj.getDateDebut().getDateSQL() + " , "
                    + obj.getDateFin().getDateSQL() + " , "
                    + obj.getHeure().getTimeSQL() + " , '"
                    + obj.getFrequence() + "' , "
                    + obj.getUtilisateur().getId() + " , '"
                    + obj.getObjetMail() + "' , '"
                    + obj.getMessageMail() + "')"
            );
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            ResultSet res = statement.executeQuery("Select LAST_INSERT_ID() as ID_campagne from campagne");
            res.next();
            int id=res.getInt("ID_campagne");
            recevoir.create(id, obj.getListes());
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            recevoir.delete(obj.getId());
            return !statement.execute("delete from campagne where ID_campagne=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            recevoir.update(obj.getId(), obj.getListes());
            return !statement.execute("update campagne set "
                    + "Titre_campagne ='" + obj.getTitre()+ "' , "
                    + "Date_Debut =" + obj.getDateDebut().getDateSQL()+ " , "
                    + "Date_Fin =" + obj.getDateFin().getDateSQL()+ " , "
                    + "Heure =" + obj.getHeure().getTimeSQL()+ " , "
                    + "frequence ='" + obj.getFrequence()+ "' , "
                    //Math : j'y ai mis en commentaires car ça lance une erreur si on modifie une campagne
                    // A voire si tu en a besoin pour envoyer les mails :)
                        /*+ "DateProchainMail='" +obj.getDateProchainMail()+ "', "
                        + "Terminer='" +obj.getTerminer()+ "', "*/
                    + "ID_utilisateur =" + obj.getUtilisateur().getId()+" , "
                    + "Objet='" + obj.getObjetMail()+ "' , "
                    + "Contenu='" + obj.getMessageMail()+ "'"
                    + " where  ID_campagne=" + obj.getId()
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public Campagne selectById(int id) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from campagne where ID_campagne=" + id);
            res.next();
            Utilisateurs_DAO user = new Utilisateurs_DAO(this.connection);
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            int idCampagne = res.getInt("ID_campagne");
            return new Campagne(idCampagne,
                    res.getString("Titre_campagne"),
                    this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")),
                    this.getMyTime(res.getTime("Heure")),
                    res.getString("frequence"),
                    res.getString("Objet"),
                    res.getString("Contenu"),
                    recevoir.getListes(idCampagne),
                    user.selectById(res.getInt("ID_utilisateur"))
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Campagne selectByName(String campagne) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from campagne where Titre_campagne='" + campagne + "'");
            res.next();
            Utilisateurs_DAO user = new Utilisateurs_DAO(this.connection);
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            int idCampagne = res.getInt("ID_campagne");
            return new Campagne(idCampagne, res.getString("Titre_campagne"), this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")), this.getMyTime(res.getTime("Heure")), res.getString("frequence"), res.getString("Objet"),
                    res.getString("Contenu"), recevoir.getListes(idCampagne), user.selectById(res.getInt("ID_utilisateur")));
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Campagne> getAll() {
        List<Campagne> allCampagnes = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            Utilisateurs_DAO user = new Utilisateurs_DAO(this.connection);
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            ResultSet res = statement.executeQuery("Select * from campagne");
            
            while (res.next()) {
                int idCampagne = res.getInt("ID_campagne");
                allCampagnes.add(new Campagne(idCampagne,
                    res.getString("Titre_campagne"),
                    this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")),
                    this.getMyTime(res.getTime("Heure")),
                    res.getString("frequence"),
                    res.getString("Objet"),
                    res.getString("Contenu"),
                    recevoir.getListes(idCampagne),
                    user.selectById(res.getInt("ID_utilisateur"))
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return allCampagnes;
        }
        return allCampagnes;
    }   
    
    public List<Campagne> getAllSurveillance() {
        List<Campagne> allCampagnes = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            Utilisateurs_DAO user = new Utilisateurs_DAO(this.connection);
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            ResultSet res = statement.executeQuery("Select * from campagne where Date_Fin > NOW() AND END != 1 AND Date_Debut < NOW() AND (DateProchainMail <= NOW() OR DateProchainMail = NULL)");
            while (res.next()) {
                int idCampagne = res.getInt("ID_campagne");
                allCampagnes.add(new Campagne(idCampagne,
                    res.getString("Titre_campagne"),
                    this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")),
                    this.getMyTime(res.getTime("Heure")),
                    res.getString("frequence"),
                    res.getString("Objet"),
                    res.getString("Contenu"),
                    recevoir.getListes(idCampagne),
                    this.getMyDate(res.getDate("DateProchainMail")),
                    res.getInt("Terminer"),
                    user.selectById(res.getInt("ID_utilisateur"))
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return allCampagnes;
        }
        return allCampagnes;
    }
    
    public ListeDeDiffusion getListeDeDiffusionByIdCampagne(int id){
        ArrayList<Personne> listeLocataire = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ArrayList<Integer> listIdList = new ArrayList<>();
            ResultSet res = statement.executeQuery("Select ID_listDiff from recevoir where ID_campagne = "+id);
            while (res.next()) {
                listIdList.add(res.getInt("ID_listDiff"));
            }
            String condition = "";
            for(int j = 0; j < listIdList.size(); j++){
                if(j == 0){
                    condition += "where ID_listeDiff = "+listIdList.get(j);
                }
                else{
                    condition += " OR ID_listeDiff = "+listIdList.get(j);
                }
            }
            res = statement.executeQuery("Select DISTINCT(*) from locataire where ID_locataire in ( Select ID_locataire FROM locataire_liste "+condition+" )");
            while (res.next()) {
                        listeLocataire.add(new Locataire(
                        res.getInt("ID_locataire"),
                        res.getString("Nom"),
                        res.getString("Prenom"),
                        res.getInt("Age"),
                        this.getMyDate(res.getDate("Anciennete")),
                        res.getString("Mail"),
                        res.getString("Telephone")
                ));
            }
            ListeDeDiffusion listeDeDiffusionSurveillance = new ListeDeDiffusion(0, "surveillance", listeLocataire);
            return listeDeDiffusionSurveillance;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return new ListeDeDiffusion(0, "surveillance", listeLocataire);
        }
    }
    
    public int getFinishCampagne(){
        try{    
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_campagne) as nombre from campagne where END = 1");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }

    }
    
    public int getNotBeginCampagne(){
        try{    
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_campagne) as nombre from campagne where Date_Debut < NOW()");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }

    }
    
    public int getNowCampagne(){
        try{    
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_campagne) as nombre from campagne where END = 0");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }

    }
    
    public int getAllCampagne(){
        try{    
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_campagne) as nombre from campagne");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }

    }

}