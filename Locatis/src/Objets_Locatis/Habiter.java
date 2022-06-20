package Objets_Locatis;

public class Habiter{
    
    private int ID_locataire;
    private int ID_batiment;
    
    public Habiter (int unIdLocataire, int unIdBatiment){
        ID_locataire = unIdLocataire;
        ID_batiment = unIdBatiment;
    }

    public int getID_locataire() {
        return ID_locataire;
    }

    public int getID_batiment() {
        return ID_batiment;
    }
}