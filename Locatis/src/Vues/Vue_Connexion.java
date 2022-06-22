package Vues;

import Exceptions.EmptyFieldException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vue_Connexion extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel bas = new JPanel();
    private JPanel panneau_infos = new JPanel();
    
    private JLabel titre = new JLabel("Connectez-vous !");
    private JLabel login_label = new JLabel("Login : ");
    private JLabel mdp_label = new JLabel("Mot de Passe : ");
    private JLabel messageErreur = new JLabel();
    private JLabel mdp_oublie = new JLabel("Mot de Passe oublié");
    
    private JTextField login = new JTextField();
    private JPasswordField motDePasse = new JPasswordField();
    
    private JButton connexion = new JButton("Connexion");
    
    public Vue_Connexion() {
        super("Se Connecter");
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        GridLayout gridlayout=new GridLayout(2,2);
        gridlayout.setHgap(10);
        gridlayout.setVgap(10);
        panneau_infos.setLayout(gridlayout);
        panneau_infos.add(this.login_label);
        panneau_infos.add(this.login);
        panneau_infos.add(this.mdp_label);
        panneau_infos.add(this.motDePasse);
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_infos, BorderLayout.CENTER);
        centre.add(this.messageErreur, BorderLayout.SOUTH);
        
        bas.setLayout(new GridLayout(1,2));
        bas.add(this.connexion);
        mdp_oublie.setForeground(new java.awt.Color(51, 51, 255));
        bas.add(this.mdp_oublie);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    public JTextField getLogin() {
        return login;
    }

    public JPasswordField getMotDePasse() {
        return motDePasse;
    }

    public String getLoginTexte() {
        return login.getText();
    }

    public String getMotDePasseTexte() {
        return new String(motDePasse.getPassword());
    }

    public JLabel getMessageErreur() {
        return messageErreur;
    }

    /**
     * Ajouter un écouteur à un bouton désigné par son nom
     *
     * @param nomBouton le nom du bouton sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        JButton bouton = switch (nomBouton.toUpperCase()) {
            case "CONNEXION" ->
                bouton = connexion;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
    }
    
    public void verifierChamps() throws EmptyFieldException{
        if(this.login.getText().equals("")){
            throw new EmptyFieldException("un login");
        }else
        if(new String(motDePasse.getPassword()).equals("")){
            throw new EmptyFieldException("un mot de passe");
        }
    }

    public void quitter() {
        this.dispose();
    }
}