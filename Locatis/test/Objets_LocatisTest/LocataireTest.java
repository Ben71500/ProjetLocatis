package Objets_LocatisTest;

import Objets_Locatis.Locataire;
import Objets_Locatis.MyDate;
import org.junit.Test;
import org.junit.Assert;

/**
 * Classe test de l'objet Locataire
 * @author Benjamin Mathilde
 */
public class LocataireTest {
    
    /**
     * Test de la méthode getAge de l'objet Locataire
     * @throws Exception 
     */
    @Test
    public void testGetAge() throws Exception{
        Locataire locaireTest = new Locataire(0, "NomTest", "PrenomTest", new MyDate(2002, 8, 16), "test@gmail.com", "0771773740");
        Assert.assertEquals(19, locaireTest.getAge());
    }
}