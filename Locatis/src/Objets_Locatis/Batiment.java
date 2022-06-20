package Objets_Locatis;

/**
 * Classe abstraite décrivant les logements.
 * Il existe deux types de batiments : les maisons et les appartements.
 * @author Benjamin Mathilde
 */
public abstract class Batiment {
    
    private int ID;
    private String numeroRue;
    private String nomRue;
    private String ville;
    private String codePostal;
    
    /**
     * Constrcuteur d'un batiment
     * @param id : id du batiment (entier)
     * @param numeroRue : numéro dans la rue (chaine de caractères)
     * @param nomRue : nom de la rue (chaine de caractères)
     * @param ville : ville du batiment (chaine de caractères)
     * @param codePostal : code postal du batiment (chaine de caractères)
     */
    public Batiment(int id, String numeroRue, String nomRue, String ville, String codePostal){
        this.ID=id;
        this.numeroRue = numeroRue;
        this.nomRue = nomRue;
        this.ville=ville;
        this.codePostal = codePostal;
    } 
    
    /**
     * Constrcuteur d'un batiment sans id
     * @param numeroRue : numéro dans la rue (chaine de caractères)
     * @param nomRue : nom de la rue (chaine de caractères)
     * @param ville : ville du batiment (chaine de caractères)
     * @param codePostal : code postal du batiment (chaine de caractères)
     */
    public Batiment(String numeroRue, String nomRue, String ville, String codePostal){
        this.ID = 0;
        this.numeroRue = numeroRue;
        this.nomRue = nomRue;
        this.ville=ville;
        this.codePostal = codePostal;
    }

    /**
     *
     * @return l'id du batiment (entier)
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @return le numéro dans la rue (chaine de caractères)
     */
    public String getNumeroRue() {
        return numeroRue;
    }

    /**
     *
     * @return le nom de la rue (chaine de caractères)
     */
    public String getNomRue() {
        return nomRue;
    }

    /**
     *
     * @return le nom de la ville du batiment (chaine de caractères)
     */
    public String getVille() {
        return ville;
    }

    /**
     *
     * @return le code postal de la ville du batiment (chaine de caractères)
     */
    public String getCodePostal() {
        return codePostal;
    }

    @Override
    public String toString(){
        return this.numeroRue+" "+this.nomRue+" "+this.ville+" "+this.codePostal;
    }
}