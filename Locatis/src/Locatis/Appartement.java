package Locatis;

public class Appartement extends Batiment{
    
    private int etage;
    private int apart;
    
    public Appartement(int id, String adresse, int numEtage, int numApart){
        super(id,adresse);
        this.etage=numEtage;
        this.apart=numApart;
    }

    public int getEtage() {
        return etage;
    }

    public void setEtage(int etage) {
        this.etage = etage;
    }

    public int getApart() {
        return apart;
    }

    public void setApart(int apart) {
        this.apart = apart;
    }
    
    @Override
    public String toString(){
        return this.getID()+" : "+this.getAdresse()+" Etage : "+this.getEtage()+" Appartement : "+this.getApart();
    }
}
