/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locatis;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author benja
 */
public class MyDate {
    private int annee;
    private int mois;
    private int jour;
    
    public MyDate(String uneDate){
        this.annee = Integer.parseInt(uneDate.substring(0, 4));
        this.mois = Integer.parseInt(uneDate.substring(5, 7));
        this.jour = Integer.parseInt(uneDate.substring(8, 10));
    }
    
    public MyDate(Date d){
        if(d != null){
            this.jour = d.toLocalDate().getDayOfMonth();
            this.annee = d.toLocalDate().getYear();
            this.mois = d.toLocalDate().getMonthValue();
        }
        else{
            LocalDate todaysDate = LocalDate.now();
            this.jour = todaysDate.getDayOfMonth();
            this.annee = todaysDate.getYear();
            this.mois = todaysDate.getMonthValue();
        }
    }
    
    public MyDate(LocalDate d){
        this.jour = d.getDayOfMonth();
        this.annee = d.getYear();
        this.mois = d.getMonthValue();
    }
    
    public MyDate(int uneAnnee, int unMois, int unJour){
        this.annee = uneAnnee;
        this.mois = unMois;
        this.jour = unJour;
    }
    
    public String getDateSQL(){
        return "'"+this.annee+"-"+this.mois+"-"+this.jour+"'";
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }

    public int getMois() {
        return mois;
    }

    public void setMois(int mois) {
        this.mois = mois;
    }

    public int getJour() {
        return jour;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }
    
    public String getDateEcrite(){
        String date = jour + " ";
        switch(mois){
            case 1 -> date+="janvier";
            case 2 -> date+="février";
            case 3 -> date+="mars";
            case 4 -> date+="avril";
            case 5 -> date+="mai";
            case 6 -> date+="juin";
            case 7 -> date+="juillet";
            case 8 -> date+="août";
            case 9 -> date+="septembre";
            case 10 -> date+="octobre";
            case 11 -> date+="novembre";
            case 12 -> date+="décembre";
        }
        date+= " "+annee;
        return date;
    }
    
    public boolean verifierDate(){
        if(this.getJour() == 31){
            if(this.getMois() != 0 || this.getMois() != 2 || this.getMois() != 4 || this.getMois() != 6 || this.getMois() != 7 || this.getMois() != 9 || this.getMois() != 11){
                return true;
            }
            else{
                if(this.getMois() == 3){
                    
                }
            }
        }
        return true;
    }
}