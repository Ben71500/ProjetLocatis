package Objets_Locatis;

import java.sql.Connection;
import java.time.LocalDate;
import DAO.*;
import DAO.ConnectionBDD;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author benja
 */
public class SurveillanceCampagne {
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    public void SurveillanceCampagne(Connection connection){
        this.connBdd = connection;
    }
    public void surveillance_campagne(){
        while(true){
            /*try
            {*/
                Campagne_DAO campagne = new Campagne_DAO(this.connBdd);
                List<Campagne> listeCampagne = campagne.getAllSurveillance();
                for(int i = 0; i < listeCampagne.size(); i++){
                    switch(listeCampagne.get(i).getFrequence()){
                        /*case "une seule fois" -> {}
                        case "Quotidien" -> envoieQuotidient(listeCampagne.get(i));
                        case "Hebdomadaire" -> envoieHebdomadaire(listeCampagne.get(i));
                        case "Mensuel" -> envoieMensuel(listeCampagne.get(i));
                        case "Annuel" -> envoieAnnuel(listeCampagne.get(i));*/
                        case "une seule fois" -> {}
                        case "Quotidien" -> envoyer(listeCampagne.get(i));
                        case "Hebdomadaire" -> envoyer(listeCampagne.get(i));
                        case "Mensuel" -> envoyer(listeCampagne.get(i));
                        case "Annuel" -> envoyer(listeCampagne.get(i));
                    }
                }
            /*}catch(Exception ex){
                System.out.println("1 "+ex.getMessage());
            }*/
        }
    }
    
    public void envoyer(Campagne cmp){
        LocalDate date = LocalDate.of(cmp.getDateProchainMail().getAnnee(), cmp.getDateProchainMail().getMois(), cmp.getDateProchainMail().getJour());
        if(cmp.getDateDebut().getJour() == date.getDayOfMonth()){
            LocalTime time = LocalTime.of(cmp.getHeure().getHeure(), cmp.getHeure().getMinute());
            if(time.getHour() < LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                //try{
                    Campagne_DAO dao = new Campagne_DAO(connBdd);
                    Recevoir_DAO recevoir = new Recevoir_DAO(connBdd);
                    ArrayList<String> listeEmail = recevoir.getListeEmails(cmp.getId());
                    Mailer mail = new Mailer();
                    mail.sendEmail(cmp.getUtilisateur().getMail(), cmp.getUtilisateur().getPassword(), cmp.getTitre(), cmp.getObjetMail(), listeEmail);
                    
                    cmp.setDateProchainMail(new MyDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
                    
                    LocalDate dateFin = LocalDate.of(cmp.getDateFin().getAnnee(), cmp.getDateFin().getMois(), cmp.getDateFin().getJour());
                    if(date.isAfter(dateFin)){
                        cmp.setTerminee(1);
                    }
                    switch(cmp.getFrequence()){
                        case "Quotidien" -> date = date.plusDays(1);
                        case "Hebdomadaire" -> date = date.plusDays(7);
                        case "Mensuel" -> date = date.plusMonths(1);
                        case "Annuel" -> date = date.plusYears(1);
                    }
                    cmp.setDateProchainMail(new MyDate(date));
                    dao.update(cmp);
                    
                /*}catch(Exception ex){
                    System.out.println("2 "+ex.getMessage());
                }*/
            }
        }
    }
    
    public static void main(String arg[]){
        SurveillanceCampagne surveillance = new SurveillanceCampagne();
        surveillance.surveillance_campagne();
    }
}