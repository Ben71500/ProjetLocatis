package DAOTest;

import DAO.Campagne_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.ListeDeDiffusion_DAO;
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
 * Classe test de l'objet ListeDeDiffusion_DAO
 * @author Benjamin Mathilde
 */
public class ListeDeDiffusionDAOTest {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private ListeDeDiffusion_DAO daoTest = new ListeDeDiffusion_DAO(connBdd);
    private ListeDeDiffusion listeTest;
    
    /**
     * Constructeur de l'objet ListeDeDiffusionDAOTest
     */
    public ListeDeDiffusionDAOTest(){
        ArrayList<Personne> listePersonnes = new ArrayList<>();
        Utilisateur uti =new Utilisateur(1,"loginTest","mdpTest","adm","email","password");
        listePersonnes.add(uti);
        this.listeTest = new ListeDeDiffusion(0,"nomListe", listePersonnes);
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
     * Test de la methode create de l'objet ListeDeDiffusion_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testCreate() throws SQLException{
        Assert.assertEquals(true, daoTest.create(listeTest));
    }
    
    /**
     * Test de la methode insertById de l'objet ListeDeDiffusion_DAO
     * @throws SQLException 
     */
    @Test//(timeout=1000)
    public void testSelectById() throws SQLException{
        daoTest.create(listeTest);
        int id = daoTest.getLastInsertId();
        int idCampagneTest = daoTest.selectById(id).getId();
        Assert.assertEquals(id, idCampagneTest);
    }
    
    /**
     * Test de la methode update de l'objet ListeDeDiffusion_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testUpdate() throws SQLException{
        daoTest.create(listeTest);
        int idListeTest = daoTest.getLastInsertId();
        ArrayList<Personne> listePersonnes2 = new ArrayList<>();
        Utilisateur uti2 =new Utilisateur(1,"loginTest2","mdpTest2","adm","","");
        listePersonnes2.add(uti2);
        ListeDeDiffusion liste = new ListeDeDiffusion(idListeTest,"nomListeModifie", listePersonnes2);
        Assert.assertEquals(true, daoTest.update(liste));
    }
    
    /**
     * Test de la methode remove de l'objet ListeDeDiffusion_DAO
     * @throws SQLException 
     */
    @Test(timeout=1000)
    public void testRemove() throws SQLException{
        daoTest.create(listeTest);
        int idListeTest = daoTest.getLastInsertId();
        Assert.assertEquals(true, daoTest.delete(daoTest.selectById(idListeTest)));
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