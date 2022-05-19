package DAO;

import Locatis.Campagne;
import Locatis.ListeDeDiffusion;
import Locatis.Locataire;
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
            return !statement.execute("insert into campagne (ID_campagne, Titre_campagne, Date_Debut, Date_Fin, Heure, frequence, ID_utilisateur) values("
                    + obj.getId() + " , '"
                    + obj.getTitre() + "' , "
                    + obj.getDateDebut().getDateSQL() + " , "
                    + obj.getDateFin().getDateSQL() + " , "
                    + obj.getHeure().getTimeSQL() + " , '"
                    + obj.getFrequence() + "' , "
                    + obj.getUtilisateur().getId() + ")"
            );
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("delete from campagne where ID_campagne=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("update campagne set "
                    + "Titre_campagne ='" + obj.getTitre()+ "' , "
                    + "Date_Debut =" + obj.getDateDebut().getDateSQL()+ " , "
                    + "Date_Fin =" + obj.getDateFin().getDateSQL()+ " , "
                    + "Heure =" + obj.getHeure().getTimeSQL()+ " , "
                    + "frequence ='" + obj.getFrequence()+ "' , "
                    + "ID_utilisateur =" + obj.getUtilisateur().getId()
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
            return new Campagne(res.getInt("ID_campagne"),
                    res.getString("Titre_campagne"),
                    this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")),
                    this.getMyTime(res.getTime("Heure")),
                    res.getString("frequence"),
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
            return new Campagne(res.getInt("ID_campagne"), res.getString("Titre_campagne"), this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")), this.getMyTime(res.getTime("Heure")), res.getString("frequence"), user.selectById(res.getInt("ID_utilisateur")));
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
            ResultSet res = statement.executeQuery("Select * from campagne");
            while (res.next()) {
                allCampagnes.add(new Campagne(res.getInt("ID_campagne"),
                    res.getString("Titre_campagne"),
                    this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")),
                    this.getMyTime(res.getTime("Heure")),
                    res.getString("frequence"),
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
            ResultSet res = statement.executeQuery("Select * from campagne where Date_Fin > NOW() AND END != 1");
            while (res.next()) {
                allCampagnes.add(new Campagne(res.getInt("ID_campagne"),
                    res.getString("Titre_campagne"),
                    this.getMyDate(res.getDate("Date_Debut")),
                    this.getMyDate(res.getDate("Date_Fin")),
                    this.getMyTime(res.getTime("Heure")),
                    res.getString("frequence"),
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
        ArrayList<Locataire> listeLocataire = new ArrayList<>();
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
    
}