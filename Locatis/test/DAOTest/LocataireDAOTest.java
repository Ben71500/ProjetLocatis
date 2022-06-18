package DAOTest;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import Objets_Locatis.Locataire;
import Objets_Locatis.MyDate;
import java.sql.Connection;
import org.junit.Test;
import org.junit.Assert;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;

public class LocataireDAOTest {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private Locataire locataireTest = new Locataire(0, "NomTest", "PrenomTest", new MyDate(2002, 8, 16), "test@gmail.com", "0771773740");
    private Locataire_DAO daoTest = new Locataire_DAO(connBdd);
    
    /**
     * 
     * @throws SQLException 
     */
    @Before
    public void setUp() throws SQLException{
        connBdd.setAutoCommit(false);
    }
    
    /**
     * Test de la methode create de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testCreate() throws SQLException{
        Assert.assertEquals(true, daoTest.create(locataireTest));
    }
    
    /**
     * Test de la methode insertById de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test (timeout=1000)
    public void testSelectById() throws SQLException{
        daoTest.create(locataireTest);
        int id = daoTest.selectByName("NomTest").getId();
        int idLocataireTest = daoTest.selectById(id).getId();
        Assert.assertEquals(id, idLocataireTest);
    }
    
    /**
     * Test de la methode update de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testUpdate() throws SQLException{
        daoTest.create(locataireTest);
        int idLocataireTest = daoTest.selectByName("NomTest").getId();
        Locataire locataireModifierTest = new Locataire(idLocataireTest, "NomTestModif", "PrenomTestModif", new MyDate(2002, 8, 16), "testModifier@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.update(locataireModifierTest));
    }
    
    /**
     * Test de la methode remove de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testRemove() throws SQLException{
        daoTest.create(locataireTest);
        int idLocataireTest = daoTest.selectByName("NomTest").getId();
        Locataire locataireModifierSupprimerTest = new Locataire(idLocataireTest, "NomTestModif", "PrenomTestModif", new MyDate(2002, 8, 16), "testModifier@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.delete(locataireModifierSupprimerTest));
    }
    
    /**
     * 
     * @throws SQLException 
     */
    @After
    public void tearDown() throws SQLException {
        connBdd.rollback();
    }
}
