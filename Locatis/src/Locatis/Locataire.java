package Locatis;

import java.util.*;

public class Locataire {
    
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private MyDate anciennete;
    private String mail;
    private String telephone;
    private Batiment logement;

    public Locataire(int id, String nom, String prenom, int age, MyDate dateAnciennete, String mail, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.anciennete = dateAnciennete;
        this.mail = mail;
        this.telephone = telephone;
    }
    
    public Locataire(int id, String nom, String prenom, int age, MyDate dateAnciennete, String mail, String telephone, Batiment unLogement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.anciennete = dateAnciennete;
        this.mail = mail;
        this.telephone = telephone;
        this.logement = unLogement;
    }
    
    public String getMail(){
        return this.mail;
    }
    
    public String getTelephone(){
        return this.telephone;
    }

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
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MyDate getAnciennete() {
        return anciennete;
    }

    public void setAnciennete(MyDate anciennete) {
        this.anciennete = anciennete;
    }

    public Batiment getLogement() {
        return logement;
    }

    public void setLogement(Batiment logement) {
        this.logement = logement;
    }
    
    @Override
    public String toString(){
        return this.getId()+" - "+this.getPrenom()+" "+this.getNom();
    }
}