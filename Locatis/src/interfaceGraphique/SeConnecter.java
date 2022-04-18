package interfaceGraphique;

import DAO.Utilisateurs_DAO;
import DAO.Connexion;
import DAO.ConnectionBDD;
import javax.swing.*;
import Locatis.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SeConnecter extends JFrame implements ActionListener{
    
    private JPanel panneau = new JPanel();
    private JButton connexion = new JButton("Connexion");
    private JPanel bas = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel haut = new JPanel();
    private JLabel message = new JLabel("Connectez-vous !");
    private JTextField login = new JTextField();
    private JLabel login_label = new JLabel("Login : ");
    private JLabel mdp_label = new JLabel("Mot de Passe : ");
    private JLabel mdp_oublie = new JLabel("Mot de Passe oublié");
    private JPasswordField motDePasse = new JPasswordField();
    
    public SeConnecter() {
        super("Se Connecter");
        //this.setBounds(100, 100, 350, 200);
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        message.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.message);
        
        GridLayout g=new GridLayout(2,2);
        g.setHgap(10);
        g.setVgap(10);
        centre.setLayout(g);
        centre.add(this.login_label);
        centre.add(this.login);
        centre.add(this.mdp_label);
        centre.add(this.motDePasse);
        
        bas.setLayout(new GridLayout(1,2));
        bas.add(this.connexion);
        mdp_oublie.setForeground(new java.awt.Color(51, 51, 255));
        bas.add(this.mdp_oublie);
        
        /*this.texte.addFocusListener(new FocusNuller());
        this.texte2.addFocusListener(new FocusNuller());*/
        
        this.connexion.addActionListener(this);
        this.login.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    seConnecter();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.motDePasse.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    seConnecter();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.getContentPane().add(this.panneau);
        //this.getRootPane().setDefaultButton(boutonQuitter);
        this.pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==connexion){
            seConnecter();
        }
    }
    
    public void seConnecter(){
        String mdp=new String(motDePasse.getPassword());
        if(!login.getText().equals("") && !mdp.equals("")){

            Utilisateur user=trouverUser(login.getText(), mdp);
            if(user!=null){
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        MenuPrincipal fenetre=new MenuPrincipal(user);
                        fenetre.setBounds(100, 100, 350, 350);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            }
            else
                System.out.println("Erreur d'authentification");
        }
    }
    
    public Utilisateur trouverUser(String login, String mdp){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        
        Utilisateurs_DAO user = new Utilisateurs_DAO(connBdd);
        try{
            Utilisateur leUser=user.selectByName(login);
            if(leUser.getMotDePasse().equals(mdp))
                return leUser;
        }
        catch(Exception e){}
        return null;
    }
    
}
