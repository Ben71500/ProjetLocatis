package Locatis;

public class MyTime {
    
    private int heure;
    private int minute;

    public MyTime(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }
    
    public String getTimeSQL(){
        return "'"+this.heure+":"+this.minute+":00'";
    }
}