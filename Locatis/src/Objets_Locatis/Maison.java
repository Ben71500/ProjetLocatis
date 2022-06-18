package Objets_Locatis;

public class Maison extends Batiment{
    
    public Maison(int id, String numeroRue, String nomRue, String ville, String codePostal){
        super(id, numeroRue, nomRue, ville, codePostal);
    }
    
    public Maison(String numeroRue, String nomRue, String ville, String codePostal){
        super(numeroRue, nomRue, ville, codePostal);
    }
}