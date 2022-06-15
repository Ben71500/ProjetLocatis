/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOTest;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import Locatis.Locataire;
import Locatis.MyDate;
import java.sql.Connection;
import org.junit.Test;
import org.junit.Assert;
import java.sql.SQLException;

/**
 *
 * @author benja
 */
public class LocataireDAOTest {
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    /**
     * Test de la methode create de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testCreate() throws SQLException{
        Locataire_DAO daoTest = new Locataire_DAO(connBdd);
        Locataire locataireTest = new Locataire(0, "NomTest", "PrenomTest", new MyDate(2002, 8, 16), "test@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.create(locataireTest));
        try{    
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }
    
    /**
     * Test de la methode update de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testUpdate() throws SQLException{
        Locataire_DAO daoTest = new Locataire_DAO(connBdd);
        int idLocataireTest = daoTest.selectByName("NomTest").getId();
        Locataire locataireModifierTest = new Locataire(idLocataireTest, "NomTestModif", "PrenomTestModif", new MyDate(2002, 8, 16), "testModifier@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.update(locataireModifierTest));
        try{    
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }
    
    /**
     * Test de la methode remove de l'objet Locataire_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testRemove() throws SQLException{
        Locataire_DAO daoTest = new Locataire_DAO(connBdd);
        int idLocataireTest = daoTest.selectByName("NomTestModif").getId();
        Locataire locataireModifierSupprimerTest = new Locataire(idLocataireTest, "NomTestModif", "PrenomTestModif", new MyDate(2002, 8, 16), "testModifier@gmail.com", "0771773740");
        Assert.assertEquals(true, daoTest.delete(locataireModifierSupprimerTest));
    }
}
