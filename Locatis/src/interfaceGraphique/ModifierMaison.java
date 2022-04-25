package interfaceGraphique;

import DAO.*;
import javax.swing.*;
import Locatis.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModifierMaison extends JFrame implements ActionListener {
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel adresse_label = new JLabel ("Adresse : ");
    
    private JTextField adresse = new JTextField();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    private Maison uneMaison;
    
    public ModifierMaison(Utilisateur user) {
        super("Ajouter une maison");
        userConnecte=user;
        titre.setText("Ajouter une maison");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        this.ajouter.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public ModifierMaison(Utilisateur user, Maison maison) {
        super("Modifier une maison");
        userConnecte=user;
        titre.setText("Modifier une maison");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        this.modifier.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.uneMaison=maison;
        adresse.setText(uneMaison.getAdresse());
    }
    
    public void initialisation(){
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
          
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_info, BorderLayout.CENTER);
        centre.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_info.setLayout(new GridLayout(6,2));
        panneau_info.add(this.adresse_label);
        panneau_info.add(this.adresse);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            
            if(e.getSource()==ajouter){
                try{
                    verifierChamps();
                    uneMaison=new Maison(0 ,adresse.getText());
                    Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                    Maison_DAO nouvelleMaison = new Maison_DAO(connBdd);
                    nouvelleMaison.create(uneMaison);
                    PopupInformation popup=new PopupInformation("La maison à l'adresse : "+uneMaison.getAdresse()+" a été ajouté.");
                    reset();
                }catch (EmptyFieldException ex) {}
            }
        
        if(e.getSource()==modifier){
            try {
                verifierChamps();
                uneMaison = new Maison(uneMaison.getID() ,adresse.getText());
                System.out.println(adresse.getText());
                Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                Maison_DAO nouvelleMaison = new Maison_DAO(connBdd);
                nouvelleMaison.update(uneMaison);
                PopupInformation popup=new PopupInformation("La maison à l'adresse : "+uneMaison.getAdresse()+" a été modifier.");
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        GestionBatiment fenetre=new GestionBatiment(userConnecte);
                        fenetre.setBounds(100, 100, 550, 300);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            } catch (EmptyFieldException ex) {}
            
        }
        if(e.getSource()==retour){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    GestionBatiment fenetre=new GestionBatiment(userConnecte);
                    fenetre.setBounds(100, 100, 550, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
    }
    
    public void reset(){
        this.adresse.setText("");
    }
    
    public void verifierChamps() throws EmptyFieldException{
        if(this.adresse.getText().equals("")){
            throw new EmptyFieldException("une adresse");
        }
    }
}