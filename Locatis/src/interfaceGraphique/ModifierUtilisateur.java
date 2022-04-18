package interfaceGraphique;

import DAO.Utilisateurs_DAO;
import DAO.Connexion;
import DAO.ConnectionBDD;
import Locatis.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.*;
import java.util.*;

public class ModifierUtilisateur extends JFrame implements ActionListener{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel("Modifier un Utilisateur");
    private JLabel id_label = new JLabel("ID : ");
    private JLabel login_label = new JLabel ("Login : ");
    private JLabel mdp_label = new JLabel ("Mot de passe : ");
    private JLabel cat_label = new JLabel("Catégorie : ");
    
    private JComboBox ID = new JComboBox();
    private JTextField login = new JTextField();
    private JTextField mdp = new JTextField();
    private JComboBox categorie = new JComboBox();
    
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    
    public ModifierUtilisateur(Utilisateur user) {
        super("Modifier un utilisateur");
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
        
        panneau_info.setLayout(new GridLayout(4,2));
        
        panneau_info.add(id_label);
        listeId();
        this.ID.addActionListener(this);
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
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        this.modifier.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    public void listeId(){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        Utilisateurs_DAO nouvelUtilisateur=new Utilisateurs_DAO(connBdd);
        ArrayList<Utilisateur> listeUti = new ArrayList<>(nouvelUtilisateur.getAll());
        for(int i = 0; i < listeUti.size(); i++){
            this.ID.addItem(listeUti.get(i));
        }
        panneau_info.add(ID);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ID){
            /*Connection connBdd= ConnectionBDD.getInstance(new Connexion());
            Utilisateurs_DAO user_dao=new Utilisateurs_DAO(connBdd);
            Utilisateur user = user_dao.selectById(WIDTH)*/
            Utilisateur user = (Utilisateur) ID.getSelectedItem(); 
            login.setText(user.getLogin());
            mdp.setText(user.getMotDePasse());
            switch(user.getCat()){
                case "usr" -> categorie.setSelectedIndex(0);
                case "ges" ->categorie.setSelectedIndex(1);
                case "adm" -> categorie.setSelectedIndex(2);
            }
        }
        if(e.getSource()==modifier){
            String cat = "";
            switch((String) categorie.getSelectedItem()){
                case "Utilisateur" -> cat = "usr";
                case "Gestionnaire" -> cat = "ges";
                case "Administrateur" -> cat = "adm";
                default -> System.out.println("Erreur liée à la catégorie du compte");
            }
            /*String id=(String) ID.getSelectedItem().toString();
            id=id.substring(0,id.indexOf(':')-1);
            Utilisateur user=new Utilisateur(Integer.parseInt(id) ,login.getText(), mdp.getText(), cat);*/
            Utilisateur userChoisi = (Utilisateur) ID.getSelectedItem();
            Utilisateur user=new Utilisateur(userChoisi.getId() ,login.getText(), mdp.getText(), cat);
            Connection connBdd= ConnectionBDD.getInstance(new Connexion());
            Utilisateurs_DAO nouvelUtilisateur=new Utilisateurs_DAO(connBdd);
            nouvelUtilisateur.update(user);
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
