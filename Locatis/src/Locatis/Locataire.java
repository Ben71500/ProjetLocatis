package Locatis;

import java.time.LocalDate;

public class Locataire implements Personne{
    
    private int id;
    private String nom;
    private String prenom;
    private MyDate dateDeNaissance;
    private String mail;
    private String telephone;

    public Locataire(int id, String nom, String prenom, MyDate dateNaissance, String mail, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateDeNaissance = dateNaissance;
        this.mail = mail;
        this.telephone = telephone;
    }
    
    @Override
    public String getMail(){
        return this.mail;
    }
    
    public String getTelephone(){
        return this.telephone;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

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

    public MyDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(MyDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
    
    @Override
    public String toString(){
        return this.getId()+" - "+this.getPrenom()+" "+this.getNom();
    }
}