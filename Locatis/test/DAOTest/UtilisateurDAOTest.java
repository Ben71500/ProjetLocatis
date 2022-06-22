/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import DAO.Utilisateurs_DAO;
import Objets_Locatis.Locataire;
import Objets_Locatis.MyDate;
import Objets_Locatis.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Classe de Test de l'objet Utilisateur_DAO
 * @author Benjamin Mathilde
 */
public class UtilisateurDAOTest {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private Utilisateur utilisateurTest = new Utilisateur(0, "testLogin", "mdpTest", "uti", "test@gmail.com", "testMailMdp");
    private Utilisateurs_DAO daoTest = new Utilisateurs_DAO(connBdd);
    
    /**
     * Méthode qui désactive l'autocommit
     * @throws SQLException 
     */
    @Before
    public void setUp() throws SQLException{
        connBdd.setAutoCommit(false);
    }
    
    /**
     * Test de la methode create de l'objet Utilisateur_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void createTest() throws SQLException{
        Assert.assertEquals(true, daoTest.create(utilisateurTest));
    }

    /**
     * Test de la methode insertById de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test (timeout=1000)
    public void testSelectById() throws SQLException{
        daoTest.create(utilisateurTest);
        int id = daoTest.getLastInsertId();
        int idUtilisateur = daoTest.selectById(id).getId();
        Assert.assertEquals(id, idUtilisateur);
    }
    
    /**
     * Test de la methode update de l'objet Utilisateur_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void updateTest() throws SQLException{
        daoTest.create(utilisateurTest);
        int idUti = daoTest.getLastInsertId();
        Utilisateur utilisateurTestModif = new Utilisateur(idUti, "testModifLogin", "mdpTest", "uti", "testModif@gmail.com", "testMailMdp");
        Assert.assertEquals(true, daoTest.update(utilisateurTest));
    }
    
    /**
     * Test de la methode delete de l'objet Utilisateur_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void deleteTest() throws SQLException{
        daoTest.create(utilisateurTest);
        int idUti = daoTest.getLastInsertId();
        Utilisateur utilisateurASupprimer = new Utilisateur(idUti, "testModifLogin", "mdpTest", "uti", "testModif@gmail.com", "testMailMdp");
        Assert.assertEquals(true, daoTest.delete(utilisateurTest));
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
