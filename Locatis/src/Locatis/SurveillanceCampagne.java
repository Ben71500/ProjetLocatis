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
import DAO.*;
import java.util.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
                Campagne_DAO campagne = new Campagne_DAO(this.connection);
                List<Campagne> listeCampagne = campagne.getAllSurveillance();
                for(int i = 0; i < listeCampagne.size(); i++){
                    switch(listeCampagne.get(i).getFrequence()){
                        case "une seule fois" : break;
                        case "Quotident" : envoieQuotidient(listeCampagne.get(i)); break;
                        case "Hebdomadaire" : envoieHebdomadaire(listeCampagne.get(i)); break;
                        case "Mensuel" : envoieMensuel(listeCampagne.get(i)); break;
                        case "Annuel" :  envoieAnnuel(listeCampagne.get(i)); break;
                    }
                }
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }
    
    public void envoieQuotidient(Campagne cmp){
        LocalDate date = LocalDate.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
        date.plusDays(1);
        if(cmp.getDateDebut().getJour() == date.getDayOfMonth()){
            LocalTime time = LocalTime.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
            if(time.getHour() < LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                try{
                    Campagne_DAO dao = new Campagne_DAO(connection);
                    ListeDeDiffusion liste = dao.getListeDeDiffusionByIdCampagne(cmp.getId());
                    ArrayList<String> listeEmail = new ArrayList<>();
                    for(int j = 0; j < liste.getListe().size(); j++){
                        listeEmail.add(liste.getListe().get(j).getMail());
                    }
                    cmp.setListeEmail(listeEmail);
                    Mailer mail = new Mailer();
                    mail.sendEmail(cmp.getUtilisateur().getEmail(), cmp.getUtilisateur().getPassword(), cmp.getTitre(), "coucou", cmp.getListeEmail());
                    
                    cmp.setDateProchainMail(new MyDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    
                    LocalDate dateFin = LocalDate.of(cmp.getDateFin().getAnnee(), cmp.getDateFin().getMois(), cmp.getDateFin().getJour());
                    if(date.isAfter(dateFin)){
                        cmp.setTerminer(1);
                    }
                    dao.update(cmp);
                    
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public void envoieHebdomadaire(Campagne cmp){
        LocalDate date = LocalDate.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
        date.plusDays(7);
        if(cmp.getDateDebut().getJour() == date.getDayOfMonth()){
            LocalTime time = LocalTime.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
            if(time.getHour() < LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                try{
                    Campagne_DAO dao = new Campagne_DAO(connection);
                    ListeDeDiffusion liste = dao.getListeDeDiffusionByIdCampagne(cmp.getId());
                    ArrayList<String> listeEmail = new ArrayList<>();
                    for(int j = 0; j < liste.getListe().size(); j++){
                        listeEmail.add(liste.getListe().get(j).getMail());
                    }
                    cmp.setListeEmail(listeEmail);
                    Mailer mail = new Mailer();
                    mail.sendEmail(cmp.getUtilisateur().getEmail(), cmp.getUtilisateur().getPassword(), cmp.getTitre(), "coucou", cmp.getListeEmail());
                    
                    cmp.setDateProchainMail(new MyDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    
                    LocalDate dateFin = LocalDate.of(cmp.getDateFin().getAnnee(), cmp.getDateFin().getMois(), cmp.getDateFin().getJour());
                    if(date.isAfter(dateFin)){
                        cmp.setTerminer(1);
                    }
                    dao.update(cmp);
                    
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public void envoieMensuel(Campagne cmp){
        LocalDate date = LocalDate.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
        date.plusMonths(1);
        if(cmp.getDateDebut().getJour() == date.getDayOfMonth()){
            LocalTime time = LocalTime.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
            if(time.getHour() < LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                try{
                    Campagne_DAO dao = new Campagne_DAO(connection);
                    ListeDeDiffusion liste = dao.getListeDeDiffusionByIdCampagne(cmp.getId());
                    ArrayList<String> listeEmail = new ArrayList<>();
                    for(int j = 0; j < liste.getListe().size(); j++){
                        listeEmail.add(liste.getListe().get(j).getMail());
                    }
                    cmp.setListeEmail(listeEmail);
                    Mailer mail = new Mailer();
                    mail.sendEmail(cmp.getUtilisateur().getEmail(), cmp.getUtilisateur().getPassword(), cmp.getTitre(), "coucou", cmp.getListeEmail());
                    
                    cmp.setDateProchainMail(new MyDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    
                    LocalDate dateFin = LocalDate.of(cmp.getDateFin().getAnnee(), cmp.getDateFin().getMois(), cmp.getDateFin().getJour());
                    if(date.isAfter(dateFin)){
                        cmp.setTerminer(1);
                    }
                    dao.update(cmp);
                    
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
    
    public void envoieAnnuel(Campagne cmp){
        LocalDate date = LocalDate.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
        date.plusYears(1);
        if(cmp.getDateDebut().getJour() == date.getDayOfMonth()){
            LocalTime time = LocalTime.of(cmp.getDateDebut().getAnnee(), cmp.getDateDebut().getMois(), cmp.getDateDebut().getJour());
            if(time.getHour() < LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                try{
                    Campagne_DAO dao = new Campagne_DAO(connection);
                    ListeDeDiffusion liste = dao.getListeDeDiffusionByIdCampagne(cmp.getId());
                    ArrayList<String> listeEmail = new ArrayList<>();
                    for(int j = 0; j < liste.getListe().size(); j++){
                        listeEmail.add(liste.getListe().get(j).getMail());
                    }
                    cmp.setListeEmail(listeEmail);
                    Mailer mail = new Mailer();
                    mail.sendEmail(cmp.getUtilisateur().getEmail(), cmp.getUtilisateur().getPassword(), cmp.getTitre(), "coucou", cmp.getListeEmail());
                    
                    cmp.setDateProchainMail(new MyDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    
                    LocalDate dateFin = LocalDate.of(cmp.getDateFin().getAnnee(), cmp.getDateFin().getMois(), cmp.getDateFin().getJour());
                    if(date.isAfter(dateFin)){
                        cmp.setTerminer(1);
                    }
                    dao.update(cmp);
                    
                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}

