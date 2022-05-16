package Locatis;

public class Utilisateur {
    
    private int id;
    private String login;
    private String motDePasse;
    private String cat;
    private String email;
    private String password;
    
    public Utilisateur(int unId, String unLogin, String mdp, String cat, String email, String password){
        this.id=unId;
        this.login=unLogin;
        this.motDePasse=mdp;
        this.cat=cat;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
    
    public String toString(){
        return this.getId()+" : "+this.getLogin();
    }
}