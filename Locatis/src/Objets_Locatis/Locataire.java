package Objets_Locatis;

import java.time.LocalDate;

/**
 * Classe dérivée de Personne décrivant un locataire.
 * @author Benjamin Mathilde
 */
public class Locataire implements Personne{
    
    private int id;
    private String nom;
    private String prenom;
    private MyDate dateDeNaissance;
    private String mail;
    private String telephone;

    /**
     * Constructeur d'un locataire.
     * @param id : id du locataire (entier)
     * @param nom : nom du locataire (chaîne de caractères)
     * @param prenom : prenom du locataire (chaîne de caractères)
     * @param dateNaissance : date de naissance du locataire (date)
     * @param mail : adresse mail du locataire (chaîne de caractères)
     * @param telephone : numéro de téléphone du locataire (chaîne de caractères)
     */
    public Locataire(int id, String nom, String prenom, MyDate dateNaissance, String mail, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateNaissance;
        this.mail = mail;
        this.telephone = telephone;
    }
    
    /**
     *
     * @return l'id du locataire
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     *
     * @return le nom du locataire
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @return le prénom du locataire
     */
    public String getPrenom() {
        return prenom;
    }
    
    /**
     *
     * @return la date de naissance du locataire
     */
    public MyDate getDateDeNaissance() {
        return dateDeNaissance;
    }
    
    /**
     *
     * @return l'adresse mail du locataire
     */
    @Override
    public String getMail(){
        return this.mail;
    }
    
    /**
     *
     * @return le numéro de téléphone du locataire
     */
    public String getTelephone(){
        return this.telephone;
    }
    
    /**
     * Méthode qui calcule l'age du locataire à partir de sa date de naissance
     * @return l'age du locataire
     */
    public int getAge() {
        MyDate aujourdhui = new MyDate(LocalDate.now());
        if(aujourdhui.getAnnee() < dateDeNaissance.getAnnee())
            return -1;
        else{
            if(aujourdhui.getMois() > dateDeNaissance.getMois()){
                return aujourdhui.getAnnee()-dateDeNaissance.getAnnee();
            }else{
                if(aujourdhui.getMois() < dateDeNaissance.getMois()){
                    return aujourdhui.getAnnee()-dateDeNaissance.getAnnee()-1;
                }else{
                    if(aujourdhui.getJour() >= dateDeNaissance.getJour())
                        return aujourdhui.getAnnee()-dateDeNaissance.getAnnee();
                    else
                        return aujourdhui.getAnnee()-dateDeNaissance.getAnnee()-1;
                }
            }
        }
    }

    @Override
    public String toString(){
        return this.getId()+" - "+this.getPrenom()+" "+this.getNom();
    }
}