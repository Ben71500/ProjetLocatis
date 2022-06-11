package Locatis;

public class Locataire implements Personne{
    
    private int id;
    private String nom;
    private String prenom;
    private int age;
    private MyDate dateDeNaissance;
    private String mail;
    private String telephone;

    public Locataire(int id, String nom, String prenom, int age, MyDate dateNaissance, String mail, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
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
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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