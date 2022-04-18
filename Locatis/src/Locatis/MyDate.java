/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Locatis;

/**
 *
 * @author benja
 */
public class MyDate {
    private int annee;
    private int mois;
    private int jour;
    
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
}