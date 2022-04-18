package interfaceGraphique;

import javax.swing.*;
import Locatis.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.border.Border;

public class MenuPrincipal extends JFrame implements ActionListener {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_utilisateurs = new JPanel();
    private JPanel panneau_locataires = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    
    private JButton ajouter_user = new JButton("Ajouter");
    private JButton modifier_user = new JButton("Modifier");
    private JButton supprimer_user = new JButton("Supprimer");
    private JButton gerer_loc = new JButton("Gérer les locataires");
    private JButton deconnexion = new JButton("Se déconnecter");
    private JButton quitter = new JButton("Quitter");
    
    private Utilisateur userConnecte;
    
    public MenuPrincipal(Utilisateur user){
        super("Menu Principal");
        userConnecte=user;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        this.titre.setText("Bonjour "+user.getLogin()+" !");
        this.titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new GridLayout(1,2));
        centre.add(this.panneau_utilisateurs);
        
        panneau_utilisateurs.setLayout(new GridLayout(3,1));
        Border bordure = BorderFactory.createTitledBorder("Utilisateurs");
        panneau_utilisateurs.setBorder(bordure);
        panneau_utilisateurs.add(this.ajouter_user);
        this.ajouter_user.addActionListener(this);
        panneau_utilisateurs.add(this.modifier_user);
        this.modifier_user.addActionListener(this);
        panneau_utilisateurs.add(this.supprimer_user);
        this.supprimer_user.addActionListener(this);
        
        if(!user.getCat().equals("adm"))
            panneau_utilisateurs.setVisible(false);
        
        
        centre.add(this.panneau_locataires);
        Border bordure2 = BorderFactory.createTitledBorder("Locataires");
        panneau_locataires.setBorder(bordure2);
        panneau_locataires.add(this.gerer_loc);
        this.gerer_loc.addActionListener(this);
        if(!user.getCat().equals("adm"))
            panneau_locataires.setVisible(false);
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(deconnexion);
        panneau_boutons.add(quitter);
        this.deconnexion.addActionListener(this);
        this.quitter.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ajouter_user){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    AjouterUtilisateur fenetre=new AjouterUtilisateur(userConnecte);
                    fenetre.setBounds(100, 100, 350, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==modifier_user){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    ModifierUtilisateur fenetre=new ModifierUtilisateur(userConnecte);
                    fenetre.setBounds(100, 100, 350, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==supprimer_user){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    SupprimerUtilisateur fenetre=new SupprimerUtilisateur(userConnecte);
                    fenetre.setBounds(100, 100, 350, 200);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==gerer_loc){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    GestionLocataires fenetre=new GestionLocataires(userConnecte);
                    fenetre.setBounds(100, 100, 550, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==deconnexion){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    SeConnecter fenetre=new SeConnecter();
                    fenetre.setBounds(100, 100, 350, 200);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==quitter){
            this.dispose();
            System.exit(0);
        }
    }
    
}
