package Objets_Locatis;

public class Batiment {
    
    private int ID;
    private String numeroRue;
    private String nomRue;
    private String ville;
    private String codePostal;
    
    public Batiment(int id, String numeroRue, String nomRue, String ville, String codePostal){
        this.ID=id;
        this.numeroRue = numeroRue;
        this.nomRue = nomRue;
        this.ville=ville;
        this.codePostal = codePostal;
    } 
    
    public Batiment(String numeroRue, String nomRue, String ville, String codePostal){
        this.ID = 0;
        this.numeroRue = numeroRue;
        this.nomRue = nomRue;
        this.ville=ville;
        this.codePostal = codePostal;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNumeroRue() {
        return numeroRue;
    }

    public void setNumeroRue(String numeroRue) {
        this.numeroRue = numeroRue;
    }

    public String getNomRue() {
        return nomRue;
    }

    public void setNomRue(String nomRue) {
        this.nomRue = nomRue;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    
    
    /*@Override
    public String toString(){
        return this.getID()+" : "+this.getAdresse();
    }*/
    
    @Override
    public String toString(){
        return this.getNumeroRue()+" "+this.getNomRue()+" "+this.getVille()+" "+this.getCodePostal();
    }
}