package Vues;

import Exceptions.EmptyFieldException;
import Graphique.Bouton;
import Graphique.Panneau;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe dérivée de JFrame qui décrit la vue permettant de se connecter
 * @author Benjamin Mathilde
 */
public class Vue_Connexion extends JFrame {

    private Panneau panneau = new Panneau();
    private Panneau haut = new Panneau();
    private Panneau centre = new Panneau();
    private Panneau bas = new Panneau();
    private Panneau panneau_infos = new Panneau();
    
    private JLabel titre = new JLabel("Connectez-vous !");
    private JLabel login_label = new JLabel("Login : ");
    private JLabel mdp_label = new JLabel("Mot de Passe : ");
    private JLabel messageErreur = new JLabel();
    
    private JTextField login = new JTextField();
    private JPasswordField motDePasse = new JPasswordField();
    
    private Bouton connexion = new Bouton("Connexion");
    
    private ImageIcon logo = new ImageIcon("../logo.jpg");
    
    /**
     * Modèle de la vue
     */
    public Vue_Connexion() {
        super("Se Connecter");
        this.setIconImage(logo.getImage());
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        GridLayout gridlayout=new GridLayout(2,2);
        gridlayout.setHgap(10);
        gridlayout.setVgap(50);
        panneau_infos.setLayout(gridlayout);
        panneau_infos.add(this.login_label);
        panneau_infos.add(this.login);
        panneau_infos.add(this.mdp_label);
        panneau_infos.add(this.motDePasse);
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_infos, BorderLayout.CENTER);
        centre.add(this.messageErreur, BorderLayout.SOUTH);
        
        bas.add(this.connexion);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    /**
     *
     * @return le JTextField du login
     */
    public JTextField getLogin() {
        return login;
    }

    /**
     *
     * @return JPasswordField
     */
    public JPasswordField getMotDePasse() {
        return motDePasse;
    }

    /**
     *
     * @return le login
     */
    public String getLoginTexte() {
        return login.getText();
    }

    /**
     *
     * @return le mot de passe
     */
    public String getMotDePasseTexte() {
        return new String(motDePasse.getPassword());
    }

    /**
     *
     * @return le JLabel pouvant afficher un message d'erreur
     */
    public JLabel getMessageErreur() {
        return messageErreur;
    }

    /**
     * Méthode qui permet d'ajouter un écouteur à un bouton désigné par son nom
     * @param nomBouton le nom du bouton sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        Bouton bouton = switch (nomBouton.toUpperCase()) {
            case "CONNEXION" ->
                bouton = connexion;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
    }
    
    /**
     * Méthode qui vérifie que les champs sont bien saisis
     * @throws EmptyFieldException : exception levée si un champs n'est pas saisi
     */
    public void verifierChamps() throws EmptyFieldException{
        if(this.login.getText().equals("")){
            throw new EmptyFieldException("un login");
        }else
        if(new String(motDePasse.getPassword()).equals("")){
            throw new EmptyFieldException("un mot de passe");
        }
    }

    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
}