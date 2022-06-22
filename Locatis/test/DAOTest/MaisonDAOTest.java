/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Maison_DAO;
import Objets_Locatis.Appartement;
import Objets_Locatis.Maison;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe test de l'objet Maison_DAO
 * @author Benjamin Mathilde
 */
public class MaisonDAOTest {
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private Maison maisonTest = new Maison("12", "rue des Test", "TestVille", "71100");
    Maison_DAO daoTest = new Maison_DAO(connBdd);
    
    /**
     * Méthode qui désactive l'autocommit
     * @throws SQLException
     */
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
        Assert.assertEquals(true, daoTest.create(maisonTest));
    }
    
    /**
     * Test de la methode insertById de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testSelectById() throws SQLException{
        daoTest.create(maisonTest);
        int id = daoTest.getLastInsertId();
        int idMaisonTest = daoTest.selectById(id).getID();
        Assert.assertEquals(id, idMaisonTest);
    }
    
    /**
     * Test de la methode update de l'objet Appartement_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testUpdate() throws SQLException{
        daoTest.create(maisonTest);
        int idLogementTest = daoTest.getLastInsertId();
        Maison logement = new Maison(idLogementTest, "22", "rue du testModif", "VilleTestModif", "71100");
        Assert.assertEquals(true, daoTest.update(logement));
    }
    
    /**
     * Test de la methode remove de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testRemove() throws SQLException{
        daoTest.create(maisonTest);
        int idLogementTest = daoTest.getLastInsertId();
        Assert.assertEquals(true, daoTest.delete(daoTest.selectById(idLogementTest)));
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
