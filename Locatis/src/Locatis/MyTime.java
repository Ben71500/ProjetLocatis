package Locatis;

public class MyTime {
    
    private int heure;
    private int minute;

    public MyTime(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }
    
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