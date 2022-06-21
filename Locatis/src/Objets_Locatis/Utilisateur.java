package Objets_Locatis;

/**
 * Classe qui représente un Utilisateur dérivé de Personne 
 * @author Benjamin Mathilde
 */
public class Utilisateur implements Personne{
    
    private int id;
    private String login;
    private String motDePasse;
    private String cat;
    private String email;
    private String password;
    
    /**
     * Constructeur de l'objet Utilisateur
     * @param unId : l'id de l'utilisateur
     * @param unLogin : le login permettant de se cconnecter au logiciel
     * @param mdp : le mot de passe permettant de se cconnecter au logiciel
     * @param cat : la catégorie de l'utilisateur
     * @param email : l'email qui servira à envoyer des mails
     * @param password : le password de l'adresse email pour envoyer des mails
     */
    public Utilisateur(int unId, String unLogin, String mdp, String cat, String email, String password){
        this.id=unId;
        this.login=unLogin;
        this.motDePasse=mdp;
        this.cat=cat;
        this.email = email;
        this.password = password;
    }

    /**
     *
     * @return int
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     *
     * @return String
     */
    public String getLogin() {
        return login;
    }
    
    /**
     *
     * @return String
     */
    public String getMotDePasse() {
        return motDePasse;
    }
    
    /**
     *
     * @return String
     */
    public String getCat() {
        return cat;
    }
    
    /**
     *
     * @return String
     */
    @Override
    public String getMail(){
        return email;
    }

    /**
     *
     * @return String
     */
    public String getPassword() {
        return password;
    }

    @Override
    public String toString(){
        return this.getId()+" : "+this.getLogin();
    }
}