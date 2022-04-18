/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Locatis;

/**
 *
 * @author Benjamin
 */
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

    public void setID_locataire(int ID_locataire) {
        this.ID_locataire = ID_locataire;
    }

    public int getID_batiment() {
        return ID_batiment;
    }

    public void setID_batiment(int ID_batiment) {
        this.ID_batiment = ID_batiment;
    }
    
    
}
