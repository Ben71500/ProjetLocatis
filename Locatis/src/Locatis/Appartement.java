package Locatis;

public class Appartement extends Batiment{
    
    private int etage;
    private int apart;
    
    public Appartement(int id, String numeroRue, String nomRue, String ville, String codePostal, int numEtage, int numApart){
        super(id,numeroRue,nomRue,ville,codePostal);
        this.etage=numEtage;
        this.apart=numApart;
    }
    
    public Appartement(String numeroRue, String nomRue, String ville, String codePostal, int numEtage, int numApart){
        super(numeroRue,nomRue,ville,codePostal);
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
        chaine += super.toString();
        return chaine;
    }
}
