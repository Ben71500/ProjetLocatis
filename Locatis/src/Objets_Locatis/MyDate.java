package Objets_Locatis;

import java.time.LocalDate;

/**
 * Classe décrivant une date
 * @author Benjamin Mathilde
 */
public class MyDate {
    
    private int annee;
    private int mois;
    private int jour;
    
    /**
     * Constructeur qui transforme un objet Date en objet MyDate
     * @param uneDate : objet Date
     */
    public MyDate(String uneDate){
        this.annee = Integer.parseInt(uneDate.substring(0, 4));
        this.mois = Integer.parseInt(uneDate.substring(5, 7));
        this.jour = Integer.parseInt(uneDate.substring(8, 10));
    }
    
    /**
     * Constructeur qui transforme un objet LocalDate en objet MyDate
     * @param d : Objet LocalDate
     */
    public MyDate(LocalDate d){
        this.jour = d.getDayOfMonth();
        this.annee = d.getYear();
        this.mois = d.getMonthValue();
    }
    
    /**
     * Constructeur de l'objet MyDate 
     * @param uneAnnee : l'annee
     * @param unMois : le mois
     * @param unJour  : le jour
     */
    public MyDate(int uneAnnee, int unMois, int unJour){
        this.annee = uneAnnee;
        this.mois = unMois;
        this.jour = unJour;
    }

    /**
     * 
     * @return int
     */
    public int getAnnee() {
        return annee;
    }

    /**
     *
     * @return int
     */
    public int getMois() {
        return mois;
    }

    /**
     *
     * @return int
     */
    public int getJour() {
        return jour;
    }
    
    /**
     * Méthode qui permet d'avoir une chaine normalisé pour l'insertion en base de données
     * @return String
     */
    public String getDateSQL(){
        return "'"+this.annee+"-"+this.mois+"-"+this.jour+"'";
    }
    
    /**
     * Méthode qui retranscrit l'objet MyDate en date écrite
     * @return String
     */
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