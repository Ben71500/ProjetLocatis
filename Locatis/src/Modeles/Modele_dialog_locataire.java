package Modeles;

import Objets_Locatis.Campagne;
import DAO.*;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Classe du modèle de l'interface permettant de visualiser les campagnes dont un locataire fait partie
 * @author Benjamin Mathilde
 */
public class Modele_dialog_locataire {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    /**
     * Constructeur du modèle
     * @param idLocataire : id du locataire recherché
     * @return la liste des campagnes dont le locataire fait partie
     */
    public ArrayList<Campagne> getListeCampagne(int idLocataire){
        ListeDeDiffusion_DAO listDao = new ListeDeDiffusion_DAO(connBdd);
        ArrayList<Integer> listIdListDiff = new ArrayList<>();
        listIdListDiff = (ArrayList<Integer>) listDao.searchListLocataireByIdLocataire(idLocataire);
        Campagne_DAO cmpDao = new Campagne_DAO(connBdd);
        return (ArrayList <Campagne>) cmpDao.getIdCampagneByListeDeDiffusionBy(listIdListDiff);
    }
}