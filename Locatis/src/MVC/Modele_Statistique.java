/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import DAO.Appartement_DAO;
import DAO.Campagne_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.DAO;
import DAO.ListeDeDiffusion_DAO;
import DAO.Locataire_DAO;
import DAO.Maison_DAO;
import DAO.Message_DAO;
import DAO.Utilisateurs_DAO;
import java.sql.Connection;

public class Modele_Statistique {
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private DAO dao;
    
    public Modele_Statistique() {
        
    }
}
