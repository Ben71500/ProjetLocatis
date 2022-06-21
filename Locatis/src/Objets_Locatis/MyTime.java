package Objets_Locatis;

import java.sql.Time;

/**
 * Classe décrivant un temps
 * @author Benjamin Mathilde
 */
public class MyTime {
    
    private int heure;
    private int minute;

    /**
     * Constructeur de l'objet MyTime
     * @param heure
     * @param minute
     */
    public MyTime(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }

    /**
     *
     * @return int
     */
    public int getHeure() {
        return heure;
    }

    /**
     *
     * @return int 
     */
    public int getMinute() {
        return minute;
    }
    
    /**
     * Méthode qui normalise un objet MyTime en string pour l'insérer en SQL
     * @return String
     */
    public String getTimeSQL(){
        String s="'";
        if(this.heure<10)
            s+="0";
        s+=this.heure+":";
        if(this.minute<10)
            s+="0";
        s+=this.minute+":00'";
        return s;
    }
}