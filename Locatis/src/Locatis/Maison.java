package Locatis;

public class Maison extends Batiment{
    
    public Maison(int id, String adresse){
        super(id, adresse);
    }
    
    public Maison(String adresse){
        super(adresse);
    }
}