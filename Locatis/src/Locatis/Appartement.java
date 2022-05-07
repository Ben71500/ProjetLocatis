package Locatis;

public class Appartement extends Batiment{
    
    private int etage;
    private int apart;
    
    public Appartement(int id, String adresse, int numEtage, int numApart){
        super(id,adresse);
        this.etage=numEtage;
        this.apart=numApart;
    }
    
    public Appartement(String adresse, int numEtage, int numApart){
        super(adresse);
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
        String chaine = "Appartement "+this.getApart();
        if(this.getEtage()==0)
            chaine += ", rez-de-chaussée, ";
        if(this.getEtage()==1)
            chaine += ", 1er étage, ";
        else
            chaine += ", "+this.getEtage()+" ème étage, ";
        chaine += this.getAdresse();
        return chaine;
    }
}
