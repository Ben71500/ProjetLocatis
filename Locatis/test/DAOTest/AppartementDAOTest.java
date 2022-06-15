/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import Locatis.Appartement;
import Locatis.MyDate;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author benja
 */
public class AppartementDAOTest {
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    /**
     * Test de la methode create de l'objet Appartement_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testCreate() throws SQLException{
        Appartement_DAO daoTest = new Appartement_DAO(connBdd);
        Appartement AppartementTest = new Appartement("12", "rue des Test", "TestVille", "71100", 2, 10);
        Assert.assertEquals(true, daoTest.create(AppartementTest));
        try{    
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }
    
    /**
     * Test de la methode update de l'objet Appartement_DAO
     * @throws SQLException 
     */
    /*@Test(timeout=1000)
    public void testUpdate() throws SQLException{
        Appartement_DAO daoTest = new Appartement_DAO(connBdd);
        int idLogementTest = daoTest.selectByName(adresse).getId();
        Locataire locataireModifierTest = new Locataire(idLocataireTest, "NomTestModif", "PrenomTestModif", new MyDate(2002, 8, 16), "testModifier@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.update(locataireModifierTest));
        try{    
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }*/
    
    /**
     * Test de la methode remove de l'objet Locataire_DAO
     * @throws SQLException 
     */
    /*@Test(timeout=1000)
    public void testRemove() throws SQLException{
        Locataire_DAO daoTest = new Locataire_DAO(connBdd);
        int idLocataireTest = daoTest.selectByName("NomTestModif").getId();
        Locataire locataireModifierSupprimerTest = new Locataire(idLocataireTest, "NomTestModif", "PrenomTestModif", new MyDate(2002, 8, 16), "testModifier@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.delete(locataireModifierSupprimerTest));
    }*/
}
