/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locatis;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.sql.Time;
import java.time.LocalTime;

/**
 *
 * @author benja
 */
public class SurveillanceCampagne {
    
    protected Connection connection;
    public void SurveillanceCampagne(Connection connection){
        this.connection = connection;
    }
    public void surveillance_campagne(){
        while(true){
            try
            {
                Statement statement = this.connection.createStatement();
                ResultSet res = statement.executeQuery("SELECT * FROM campagne LEFT JOIN utilisateur ON campagne.ID_utilisateur = utilisateur.ID_utilisateurwhere Date_Fin < NOW()");
                res.next();
                MyDate dateDebut = new MyDate(res.getDate("Date_Debut"));
                Utilisateur utilisateur = new Utilisateur(res.getInt("ID_Utilisateur"), res.getString("login"), res.getString("Mdp"), res.getString("CAT"), res.getString("Email"), res.getString("Password"));
                Campagne campagne = new Campagne(res.getInt("ID_campagne"), res.getString("Titre_campagne"),dateDebut, new MyDate(res.getDate("Date_Fin")), new MyTime(res.getTime("Heure")) ,res.getString("frequence"), utilisateur);
                switch(campagne.getFrequence()){
                    case "une seule fois" : break;
                    case "Quotident" : envoieQuotidient(campagne); break;
                    case "Hebdomadaire" : envoieHebdomadaire(campagne); break;
                    case "Mensuel" : envoieMensuel(campagne); break;
                    case "Annuel" :  envoieAnnuel(campagne); break;
                }
            }catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void envoieQuotidient(Campagne cmp){
        if(cmp.getDateDebut().getJour() == LocalDate.now().getDayOfMonth()){
            if(cmp.getHeure().getHeure() <= LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                try{ 
                    Statement statement = this.connection.createStatement();
                    ResultSet res = statement.executeQuery("SELECT  FROM campagne LEFT JOIN utilisateur ON campagne.ID_utilisateur = utilisateur.ID_utilisateurwhere Date_Fin < NOW()");
                    
                    Mailer mail = new Mailer();
                    mail.sendEmail(cmp.getUtilisateur().getEmail(), cmp.getUtilisateur().getPassword(), cmp.getTitre(), "coucou", cmp.getListeEmail());
                }catch(SQLException ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public void envoieHebdomadaire(Campagne cmp){
        
    }
    
    public void envoieMensuel(Campagne cmp){
        
    }
    
    public void envoieAnnuel(Campagne cmp){
        
    }
}

