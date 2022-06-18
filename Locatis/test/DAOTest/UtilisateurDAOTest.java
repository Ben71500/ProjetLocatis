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
import Locatis.Locataire;
import Locatis.MyDate;
import Locatis.Utilisateur;
import java.sql.Connection;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author benja
 */
public class UtilisateurDAOTest {
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    /**
     * Test de la methode create de l'objet Utilisateur_DAO
     * @throws SQLException 
     */
    @Test
    public void createTest() throws SQLException{
        Utilisateurs_DAO daoTest = new Utilisateurs_DAO(connBdd);
        Utilisateur utilisateurTest = new Utilisateur(0, "testLogin", "mdpTest", "uti", "test@gmail.com", "testMailMdp");
        Assert.assertEquals(true, daoTest.create(utilisateurTest));
        try{
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }

    /**
     * Test de la methode update de l'objet Utilisateur_DAO
     * @throws SQLException 
     */
    @Test
    public void updateTest() throws SQLException{
        Utilisateurs_DAO daoTest = new Utilisateurs_DAO(connBdd);
        int idUti = daoTest.selectByName("testLogin").getId();
        Utilisateur utilisateurTest = new Utilisateur(idUti, "testModifLogin", "mdpModifTest", "uti", "testModif@gmail.com", "testMailModifMdp");
        Assert.assertEquals(true, daoTest.update(utilisateurTest));
        try{    
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }
    
    /**
     * Test de la methode delete de l'objet Utilisateur_DAO
     * @throws SQLException 
     */
    @Test
    public void deleteTest() throws SQLException{
        Utilisateurs_DAO daoTest = new Utilisateurs_DAO(connBdd);
        int idUti = daoTest.selectByName("testLogin").getId();
        Utilisateur utilisateurTest = new Utilisateur(idUti, "testModifLogin", "mdpModifTest", "uti", "testModif@gmail.com", "testMailModifMdp");
        Assert.assertEquals(true, daoTest.delete(utilisateurTest));
        try{
            Thread.sleep(500);
        }catch(InterruptedException ex){
            
        }
    }
}
