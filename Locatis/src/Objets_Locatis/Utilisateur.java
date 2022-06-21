package Objets_Locatis;

/**
 * Classe qui représente un Utilisateur dérivée de Personne 
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
     * @param unId : un id
     * @param unLogin : un login
     * @param mdp : un mot de passe
     * @param cat : une catégorie
     * @param email : un email
     * @param password : un password
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
     * @param id
     */
    public void setId(int id) {
        this.id = id;
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
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
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

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @param motDePasse
     */
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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
     * @param cat
     */
    public void setCat(String cat) {
        this.cat = cat;
    }
    
    /**
     * Méthode qui affiche en chaîne de caractére un Utilisateur
     * @return 
     */
    public String toString(){
        return this.getId()+" : "+this.getLogin();
    }
}