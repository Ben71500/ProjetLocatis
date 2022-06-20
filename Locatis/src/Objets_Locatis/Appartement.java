package Objets_Locatis;

/**
 * Classe dérivée de Batiment décrivant un appartement.
 * @author Benjamin Mathilde
 */
public class Appartement extends Batiment{
    
    private int etage;
    private int apart;
    
    /**
     * Constructeur d'un appartement
     * @param id : id de l'appartement (entier)
     * @param numeroRue : numéro de la rue (chaine de caractères)
     * @param nomRue : nom de la rue (chaine de caractères)
     * @param ville : ville de l'appartement (chaine de caractères)
     * @param codePostal : code postal de l'appartement (chaine de caractères)
     * @param numEtage : numéro d'étage de l'appartement (entier)
     * @param numApart : numéro d'appartement (entier)
     */
    public Appartement(int id, String numeroRue, String nomRue, String ville, String codePostal, int numEtage, int numApart){
        super(id,numeroRue,nomRue,ville,codePostal);
        this.etage=numEtage;
        this.apart=numApart;
    }
    
    /**
     * Constructeur d'un appartement sans id : utilisé pour l'insertion de données via CSV
     * @param numeroRue : numéro de la rue (chaine de caractères)
     * @param nomRue : nom de la rue (chaine de caractères)
     * @param ville : ville de l'appartement (chaine de caractères)
     * @param codePostal : code postal de l'appartement (chaine de caractères)
     * @param numEtage : numéro d'étage de l'appartement (entier)
     * @param numApart : numéro d'appartement (entier)
     */
    public Appartement(String numeroRue, String nomRue, String ville, String codePostal, int numEtage, int numApart){
        super(numeroRue,nomRue,ville,codePostal);
        this.etage=numEtage;
        this.apart=numApart;
    }

    /**
     *
     * @return un entier représentant le numéro de l'étage
     */
    public int getEtage() {
        return etage;
    }

    /**
     *
     * @return un entier représentant le numéro de l'appartement
     */
    public int getApart() {
        return apart;
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