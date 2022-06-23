package DAOTest;

import DAO.Campagne_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import Objets_Locatis.Campagne;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.MyDate;
import Objets_Locatis.MyTime;
import Objets_Locatis.Personne;
import Objets_Locatis.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe test de l'objet Campagne_DAO
 * @author Benjamin Mathilde
 */
public class CampagneDAOTest {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private ArrayList<ListeDeDiffusion> listes;
    private Campagne campagneTest;
    
    /**
     * Constructeur de l'objet CampagneDAOTest
     */
    public CampagneDAOTest(){
        this.listes = new ArrayList<>();
        ArrayList<Personne> liste1 = new ArrayList<>();
        Utilisateur uti =new Utilisateur(1,"loginTest","mdpTest","adm","email","password");
        liste1.add(uti);
        ListeDeDiffusion uneListe = new ListeDeDiffusion(1,"nomListe", liste1);
        listes.add(uneListe);
        this.campagneTest = new Campagne(0, "campagneTest", new MyDate(2022,06,24), new MyDate(2022,06,30), new MyTime(12,00), "Quotidien", "objetTest", "messageTest", listes, uti);
    
    }
    
    /**
     * Méthode qui désactive l'autocommit
     * @throws SQLException
     */
    @Before
    public void setUp() throws SQLException{
        connBdd.setAutoCommit(false);
    }
    
    /**
     * Test de la methode create de l'objet Campagne_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testCreate() throws SQLException{
        Campagne_DAO daoTest = new Campagne_DAO(connBdd);
        Assert.assertEquals(true, daoTest.create(campagneTest));
    }
    
    /**
     * Test de la methode insertById de l'objet Campagne_DAO
     * @throws SQLException 
     */
    @Test//(timeout=1000)
    public void testSelectById() throws SQLException{
        Campagne_DAO daoTest = new Campagne_DAO(connBdd);
        daoTest.create(campagneTest);
        int id = daoTest.getLastInsertId();
        int idCampagneTest = daoTest.selectById(id).getId();
        Assert.assertEquals(id, idCampagneTest);
    }
    
    /**
     * Test de la methode update de l'objet Campagne_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testUpdate() throws SQLException{
        Campagne_DAO daoTest = new Campagne_DAO(connBdd);
        daoTest.create(campagneTest);
        int idCampagneTest = daoTest.getLastInsertId();
        Utilisateur uti2 =new Utilisateur(0,"loginTest2","mdpTest2","adm","","");
        Campagne campagne = new Campagne(0, "campagneTeste", new MyDate(2022,06,25), new MyDate(2022,06,30), new MyTime(12,30), "Quotidien", "objetTest", "messageTest", listes, uti2);
        Assert.assertEquals(true, daoTest.update(campagne));
    }
    
    /**
     * Test de la methode remove de l'objet Campagne_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testRemove() throws SQLException{
        Campagne_DAO daoTest = new Campagne_DAO(connBdd);
        daoTest.create(campagneTest);
        int idCampagneTest = daoTest.getLastInsertId();
        Assert.assertEquals(true, daoTest.delete(daoTest.selectById(idCampagneTest)));
    }
    
    /**
     * Méthode qui rollback la requête
     * @throws SQLException
     */
    @After
    public void tearDown() throws SQLException {
        connBdd.rollback();
    }
}