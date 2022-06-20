package Objets_Locatis;

/**
 * Classe dérivée de Batiment décrivant un appartement.
 * @author Benjamin Mathilde
 */
public class Maison extends Batiment{
    
    /**
     * Constructeur d'une maison
     * @param id : id de la maison (entier)
     * @param numeroRue : numéro de la rue (chaine de caractères)
     * @param nomRue : nom de la rue (chaine de caractères)
     * @param ville : ville de la maison (chaine de caractères)
     * @param codePostal : code postal de la maison (chaine de caractères)
     */
    public Maison(int id, String numeroRue, String nomRue, String ville, String codePostal){
        super(id, numeroRue, nomRue, ville, codePostal);
    }
    
    /**
     * Constructeur d'une maison sans id : utilisé pour l'insertion de données via CSV
     * @param numeroRue : numéro de la rue (chaine de caractères)
     * @param nomRue : nom de la rue (chaine de caractères)
     * @param ville : ville de la maison (chaine de caractères)
     * @param codePostal : code postal de la maison (chaine de caractères)
     */
    public Maison(String numeroRue, String nomRue, String ville, String codePostal){
        super(numeroRue, nomRue, ville, codePostal);
    }
}