package Objets_Locatis;

import java.sql.Connection;
import java.time.LocalDate;
import DAO.*;
import DAO.ConnectionBDD;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de surveiller les campagnes en cours et d'envoyer les mails correspondants
 * @author Benjamin Mathilde
 */
public class SurveillanceCampagne extends Thread{
    
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());

    /**
     * Constructeur de SurveillanceCampagne
     * @param connection
     */
    public void SurveillanceCampagne(Connection connection){
        this.connBdd = connection;
    }

    /**
     * Méthode avec une boucle infinie qui parcourt l'ensemble des campagnes nécéssiatnt l'envois d'emails
     */
    public void surveillance_campagne(){
        while(true){
            Campagne_DAO campagne = new Campagne_DAO(this.connBdd);
            List<Campagne> listeCampagne = campagne.getAllSurveillance();
            for(int i = 0; i < listeCampagne.size(); i++){
                envoyer(listeCampagne.get(i));
            }
        }
    }
    
    /**
     * Méthode qui envoie les emails d'une campagne selon l'heure
     * @param cmp : campagne
     */
    public void envoyer(Campagne cmp){
        LocalDate date = LocalDate.of(cmp.getDateProchainMail().getAnnee(), cmp.getDateProchainMail().getMois(), cmp.getDateProchainMail().getJour());
        if(cmp.getDateDebut().getJour() == date.getDayOfMonth()){
            LocalTime time = LocalTime.of(cmp.getHeure().getHeure(), cmp.getHeure().getMinute());
            if(time.getHour() < LocalTime.now().getHour() || (cmp.getHeure().getHeure() <= LocalTime.now().getHour() && cmp.getHeure().getMinute() <= LocalTime.now().getMinute())){
                Campagne_DAO dao = new Campagne_DAO(connBdd);
                //on récupère la liste des adresses emails des destinataires
                Recevoir_DAO recevoir = new Recevoir_DAO(connBdd);
                ArrayList<String> listeEmail = recevoir.getListeEmails(cmp.getId());
                //on envoie les mails à la liste des adresses mails
                Mailer mail = new Mailer();
                mail.sendEmail(cmp.getUtilisateur().getMail(), cmp.getUtilisateur().getPassword(), cmp.getObjetMail(), cmp.getMessageMail(), listeEmail);
                //on vérifie si la campagne est terminée ou pas
                LocalDate dateFin = LocalDate.of(cmp.getDateFin().getAnnee(), cmp.getDateFin().getMois(), cmp.getDateFin().getJour());
                if(date.isAfter(dateFin)){
                    cmp.setTerminee(1);
                }
                //on met à jour la date du prochain mail à envoyer
                switch(cmp.getFrequence()){
                    case "Une seule fois","Quotidien" -> date = date.plusDays(1);
                    case "Hebdomadaire" -> date = date.plusDays(7);
                    case "Mensuel" -> date = date.plusMonths(1);
                    case "Annuel" -> date = date.plusYears(1);
                }
                cmp.setDateProchainMail(new MyDate(date));
                //on met à jour la campagne dans la base de données
                dao.updateEtatCampagne(cmp);
            }
        }
    }
    
    /**
     * Méthode qui lance la surveillance des campagnes pour l'envoi d'emails
     */
    @Override
    public void run(){
        SurveillanceCampagne surveillance = new SurveillanceCampagne();
        surveillance.surveillance_campagne();
    }
}