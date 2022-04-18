package MVC;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vue_Connexion extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel bas = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    
    private JLabel titre = new JLabel("Connectez-vous !");
    
    private JLabel login_label = new JLabel("Login : ");
    private JLabel mdp_label = new JLabel("Mot de Passe : ");
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
        centre.setLayout(gridlayout);
        centre.add(this.login_label);
        centre.add(this.login);
        centre.add(this.mdp_label);
        centre.add(this.motDePasse);
        
        bas.setLayout(new GridLayout(1,2));
        bas.add(this.connexion);
        mdp_oublie.setForeground(new java.awt.Color(51, 51, 255));
        bas.add(this.mdp_oublie);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    /**
     * Ajouter un écouteur à un bouton désigné par son nom
     *
     * @param nomBouton le nom du bouton sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        JButton bouton;
        bouton = switch (nomBouton.toUpperCase()) {
            case "CONNEXION" ->
                bouton = connexion;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
    }

    public void quitter() {
        //System.exit(0);
        this.dispose();
    }
}