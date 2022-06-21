package Objets_Locatis;

/**
 * Class abstraite Personne
 * @author Benjamin Mathilde
 */
public interface Personne {
    
    /**
     *
     * @return int : l'id de la personne
     */
    public int getId();

    /**
     *
     * @return String : l'adresse mail de la personne
     */
    public String getMail();
}