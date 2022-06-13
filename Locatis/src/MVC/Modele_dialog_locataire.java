/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MVC;

import DAO.*;
import Locatis.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benja
 */
public class Modele_dialog_locataire {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    public ArrayList<Campagne> getListeCampagne(int idLocataire){
        ListeDeDiffusion_DAO listDao = new ListeDeDiffusion_DAO(connBdd);
        ArrayList<Integer> listIdListDiff = new ArrayList<>();
        listIdListDiff = (ArrayList<Integer>) listDao.searchListLocataireByIdLocataire(idLocataire);
        Campagne_DAO cmpDao = new Campagne_DAO(connBdd);
        return (ArrayList <Campagne>) cmpDao.getIdCampagneByListeDeDiffusionBy(listIdListDiff);
    }
}
