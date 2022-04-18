package Locatis;

public class Batiment {
    
    private int ID;
    private String adresse;
    
    public Batiment(int idBat, String adresseBat){
        this.ID=idBat;
        this.adresse=adresseBat;
    } 

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
    
    @Override
    public String toString(){
        return this.getID()+" : "+this.getAdresse();
    }
}