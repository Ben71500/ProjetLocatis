package DAO;

import Objets_Locatis.Campagne;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Locataire;
import Objets_Locatis.Personne;
import com.mysql.cj.result.LocalDateTimeValueFactory;
import java.sql.*;
import java.util.*;
import java.time.*;

/**
 * Class permettant de se connecter à la base de donnée pour la table campagne et d'effectuer divers action sur la table
 * @author Benjamin Mathilde
 */
public class Campagne_DAO extends DAO<Campagne>{

    /**
     * Constructeur de l'objet Campagne_DAO
     * @param connection
     */
    public Campagne_DAO(Connection connection) {
        super(connection);
    }

    /**
     * Méthode qui insére en base de donnée une campagne
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean create(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            boolean b = !statement.execute("insert into campagne (ID_campagne, Titre_campagne, Date_Debut, Date_Fin, Heure, frequence, END, ID_utilisateur, Objet, Contenu) values("
                    + obj.getId() + " , '"
                    + obj.getTitre() + "' , "
                    + obj.getDateDebut().getDateSQL() + " , "
                    + obj.getDateFin().getDateSQL() + " , "
                    + obj.getHeure().getTimeSQL() + " , '"
                    + obj.getFrequence() + "' , "
                    + "0, "
                    + obj.getUtilisateur().getId() + " , '"
                    + obj.getObjetMail() + "' , '"
                    + obj.getMessageMail() + "')"
            );
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            ResultSet res = statement.executeQuery("Select LAST_INSERT_ID() as ID_campagne from campagne");
            res.next();
            int id=res.getInt("ID_campagne");
            return recevoir.create(id, obj.getListes()) && b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui supprime une campagne de la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean delete(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            boolean b = recevoir.delete(obj.getId());
            return !statement.execute("delete from campagne where ID_campagne=" + obj.getId()) && b;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Méthode qui modifie une campagne dans la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    @Override
    public boolean update(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            Boolean b = recevoir.update(obj.getId(), obj.getListes());
            return !statement.execute("update campagne set "
                    + "Titre_campagne ='" + obj.getTitre()+ "' , "
                    + "Date_Debut =" + obj.getDateDebut().getDateSQL()+ " , "
                    + "Date_Fin =" + obj.getDateFin().getDateSQL()+ " , "
                    + "Heure =" + obj.getHeure().getTimeSQL()+ " , "
                    + "frequence ='" + obj.getFrequence()+ "' , "
                    /*+ "DateProchainMail=" +obj.getDateProchainMail().getDateSQL()+ ", "
                    + "END='" +obj.getTerminer()+ "', "*/
                    + "ID_utilisateur =" + obj.getUtilisateur().getId()+" , "
                    + "Objet='" + obj.getObjetMail()+ "' , "
                    + "Contenu='" + obj.getMessageMail()+ "'"
                    + " where  ID_campagne=" + obj.getId()
            ) && b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
    
    /**
     * Méthode qui modifie l'état d'une campagne après l'envoie d'un mail
     * @exception SQLException si la requête n'aboutie pas retourne false
     * @param obj
     * @return boolean
     */
    public boolean updateEtatCampagne(Campagne obj) {
        try {
            Statement statement = this.connection.createStatement();
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            Boolean b = recevoir.update(obj.getId(), obj.getListes());
            return !statement.execute("update campagne set "
                    +"DateProchainMail=" +obj.getDateProchainMail().getDateSQL()+ ", "
                    + "END=" +obj.getTerminee()
                    + " where  ID_campagne=" + obj.getId()
            ) && b;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    /**
     * Méthode qui récupére une campagne grâce à l'id
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param id :  id de la campagne
     * @return Campagne
     */
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
            System.out.println(ex.getMessage());
            return null;
        }
    }

    /**
     * Méthode qui récupére une campagne par son nom
     * @exception SQLException si la requête n'aboutie pas retourne null
     * @param campagne : nom de la campagne
     * @return Campagne
     */
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
    
    /**
     * Méthode qui récupére une maison par le dernier id insérer dans la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne 0
     * @return int
     */
    public int getLastInsertId(){
        try{
            Statement etat = this.connection.createStatement();
            ResultSet res = etat.executeQuery("Select LAST_INSERT_ID() as ID_Campagne from campagne");
            res.next();
            int id = res.getInt("ID_Campagne");
            return id;
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    /**
     * Méthode qui récupére toute les campagnes de la base de donnée
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Campagne incrémenter
     * @return List<Camapagne>
     */
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
    
    /**
     * Méthode qui récupére uniquement les campagne en cours nécéssitant l'envoie d'un mail 
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Campagne incrémenter
     * @return List<Campagne>
     */
    public List<Campagne> getAllSurveillance() {
        List<Campagne> allCampagnes = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            Utilisateurs_DAO user = new Utilisateurs_DAO(this.connection);
            Recevoir_DAO recevoir = new Recevoir_DAO(this.connection);
            ResultSet res = statement.executeQuery("Select * from campagne where Date_Fin > NOW() AND Date_Debut < NOW() AND (DateProchainMail <= NOW() OR DateProchainMail is NULL)");
            while (res.next()){
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
                    res.getInt("END"),
                    user.selectById(res.getInt("ID_utilisateur"))
                ));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return allCampagnes;
        }
        return allCampagnes;
    }
    
    /**
     * Méthode qui récupére toute les listes de diffusion pour n'en faire qu'une seule hors de la base de donnée par rapport à une campagne
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste de liste de diffusion incrémenter
     * @param id : id camapagne
     * @return ListeDeDiffusion
     */
    public ListeDeDiffusion getListeDeDiffusionByIdCampagne(int id){
        ArrayList<Personne> listeLocataire = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ArrayList<Integer> listIdList = new ArrayList<>();
            ResultSet res = statement.executeQuery("Select ID_listeDiff from recevoir where ID_campagne = "+id);
            while (res.next()) {
                listIdList.add(res.getInt("ID_listeDiff"));
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
            res = statement.executeQuery("Select DISTINCT(ID_locataire), Nom, Prenom, Age, DateDeNaissance, Mail, Telephone from locataire where ID_locataire in ( Select ID_locataire FROM locataire_liste "+condition+" )");
            while (res.next()) {
                        listeLocataire.add(new Locataire(
                        res.getInt("ID_locataire"),
                        res.getString("Nom"),
                        res.getString("Prenom"),
                        this.getMyDate(res.getDate("DateDeNaissance")),
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
    
    /**
     * Méthode qui récupére le nombre de campagne finis
     * @exception SQLException si la requête n'aboutie pas retourne retourne -1
     * @return int
     */
    public int getFinishCampagne(){
        try{    
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_campagne) as nombre from campagne where Date_Fin < NOW()");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }

    }
    
    /**
     *
     * @return
     */
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
    
    /**
     * Méthode qui récupére le nombre de campagnes en cours 
     * @exception SQLException si la requête n'aboutie pas retourne retourne -1
     * @return int 
     */
    public int getNowCampagne(){
        try{    
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select COUNT(ID_campagne) as nombre from campagne where Date_Debut > NOW() AND Date_Fin > NOW()");
            res.next();
            return res.getInt("nombre");
        }
        catch(SQLException ex){
            return -1;
        }

    }
    
    /**
     * Méthode qui retourne le nombre total des campagnes
     * @exception SQLException si la requête n'aboutie pas retourne retourne -1
     * @return int
     */
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
    
    /**
     * Méthode qui récupére la liste des campagnes auquelle une liste de diffusion est associé
     * @exception SQLException si la requête n'aboutie pas retourne retourne la liste null ou la liste Campagne incrémenter
     * @param listeId : id liste de diffusion
     * @return List<Campagne>
     */
    public List<Campagne> getIdCampagneByListeDeDiffusionBy(ArrayList<Integer> listeId){
        List<Campagne> listeCampagne = new ArrayList<>();
        try {
            ArrayList<Integer> listeIdCampagne = new ArrayList<>();
            Statement statement = this.connection.createStatement();
            String condition = "";
            for(int j = 0; j < listeId.size(); j++){
                if(j == 0){
                    condition += "where ID_listeDiff = "+listeId.get(j);
                }
                else{
                    condition += " OR ID_listeDiff = "+listeId.get(j);
                }
            }
            ResultSet res = statement.executeQuery("Select ID_campagne from recevoir "+condition+" ");
            while (res.next()) {
                    listeIdCampagne.add(res.getInt("ID_campagne"));
            }
            for(int j = 0; j < listeIdCampagne.size(); j++){
                if(j == 0){
                    condition += "where ID_campagne = "+listeIdCampagne.get(j);
                }
                else{
                    condition += " OR ID_campagne = "+listeIdCampagne.get(j);
                }
            }
            for(int i = 0; i < listeIdCampagne.size(); i++){
                listeCampagne.add(this.selectById(listeIdCampagne.get(i)));
            }
            
            return listeCampagne;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return listeCampagne;
        }
    }

}