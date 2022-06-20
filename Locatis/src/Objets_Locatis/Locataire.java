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
     *
     * @param id
     * @param nom
     * @param prenom
     * @param dateNaissance
     * @param mail
     * @param telephone
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
     * @return
     */
    @Override
    public String getMail(){
        return this.mail;
    }
    
    /**
     *
     * @return
     */
    public String getTelephone(){
        return this.telephone;
    }

    /**
     *
     * @return
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     *
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     *
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     *
     * @param prenom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     *
     * @return
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

    /**
     *
     * @return
     */
    public MyDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    /**
     *
     * @param dateDeNaissance
     */
    public void setDateDeNaissance(MyDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
    
    @Override
    public String toString(){
        return this.getId()+" - "+this.getPrenom()+" "+this.getNom();
    }
}