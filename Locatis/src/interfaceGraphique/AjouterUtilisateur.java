package interfaceGraphique;

import DAO.Utilisateurs_DAO;
import DAO.Connexion;
import DAO.ConnectionBDD;
import javax.swing.*;
import Locatis.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AjouterUtilisateur extends JFrame implements ActionListener {
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel("Ajouter un Utilisateur");
    private JLabel login_label = new JLabel ("Login : ");
    private JLabel mdp_label = new JLabel ("Mot de passe : ");
    private JLabel cat_label = new JLabel("Catégorie : ");
    
    private JTextField login = new JTextField();
    private JTextField mdp = new JTextField();
    private JComboBox categorie = new JComboBox();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    
    public AjouterUtilisateur(Utilisateur user) {
        super("Ajouter un utilisateur");
        userConnecte=user;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
          
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_info, BorderLayout.CENTER);
        centre.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_info.setLayout(new GridLayout(3,2));
        panneau_info.add(this.login_label);
        panneau_info.add(this.login);
        panneau_info.add(this.mdp_label);
        panneau_info.add(this.mdp);
        
        this.categorie.addItem("Utilisateur");
        this.categorie.addItem("Gestionnaire");
        this.categorie.addItem("Administrateur");
        panneau_info.add(cat_label);
        panneau_info.add(categorie);
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        this.ajouter.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ajouter){
            String cat = "";
            switch((String) categorie.getSelectedItem()){
                case "Utilisateur" -> cat = "usr";
                case "Gestionnaire" -> cat = "ges";
                case "Administrateur" -> cat = "adm";
                default -> System.out.println("Erreur liée à la catégorie du compte");
            }
            Utilisateur user=new Utilisateur(0 ,login.getText(), mdp.getText(), cat);
            Connection connBdd= ConnectionBDD.getInstance(new Connexion());
            System.out.println(ConnectionBDD.isInstanceOf(new Connexion()));
            Utilisateurs_DAO nouvelUtilisateur=new Utilisateurs_DAO(connBdd);
            nouvelUtilisateur.create(user);
        }
        if(e.getSource()==retour){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    MenuPrincipal fenetre=new MenuPrincipal(userConnecte);
                    fenetre.setBounds(100, 100, 350, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
    }
}
