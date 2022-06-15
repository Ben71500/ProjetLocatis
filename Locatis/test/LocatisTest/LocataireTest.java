/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LocatisTest;

import Locatis.Locataire;
import Locatis.MyDate;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author benja
 */
public class LocataireTest {
    
    /**
     * Test de la methode getAge de l'objet Locataire
     * @throws Exception 
     */
    @Test
    public void testGetAge() throws Exception{
        Locataire locaireTest = new Locataire(0, "NomTest", "PrenomTest", new MyDate(2002, 8, 16), "test@gmail.com", "0771773740");
        Assert.assertEquals(19, locaireTest.getAge());
    }
    
    
}
