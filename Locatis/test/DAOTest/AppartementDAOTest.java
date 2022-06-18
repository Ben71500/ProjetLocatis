package DAOTest;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import Locatis.Appartement;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class AppartementDAOTest {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private Appartement appartTest = new Appartement("12", "rue des Test", "TestVille", "71100", 2, 10);
    
    @Before
    public void setUp() throws SQLException{
        connBdd.setAutoCommit(false);
    }
    
    /**
     * Test de la methode create de l'objet Appartement_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testCreate() throws SQLException{
        Appartement_DAO daoTest = new Appartement_DAO(connBdd);
        Assert.assertEquals(true, daoTest.create(appartTest));
    }
    
    /**
     * Test de la methode insertById de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testSelectById() throws SQLException{
        Appartement_DAO daoTest = new Appartement_DAO(connBdd);
        daoTest.create(appartTest);
        int id = daoTest.getLastInsertId();
        int idAppartementTest = daoTest.selectById(id).getID();
        Assert.assertEquals(id, idAppartementTest);
    }
    
    /**
     * Test de la methode update de l'objet Appartement_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testUpdate() throws SQLException{
        Appartement_DAO daoTest = new Appartement_DAO(connBdd);
        daoTest.create(appartTest);
        int idLogementTest = daoTest.getLastInsertId();
        Appartement logement = new Appartement(idLogementTest, "22", "rue du test", "VilleTest", "71100", 3, 5);
        Assert.assertEquals(true, daoTest.update(logement));
    }
    
    /**
     * Test de la methode remove de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testRemove() throws SQLException{
        Appartement_DAO daoTest = new Appartement_DAO(connBdd);
        daoTest.create(appartTest);
        int idLogementTest = daoTest.getLastInsertId();
        Assert.assertEquals(true, daoTest.delete(daoTest.selectById(idLogementTest)));
    }
    
    @After
    public void tearDown() throws SQLException {
        connBdd.rollback();
    }
}