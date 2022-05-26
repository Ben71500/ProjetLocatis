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
    private JLabel numeroRue_label = new JLabel ("Numero de Rue : ");
    private JLabel nomRue_label = new JLabel ("Nom de la rue : ");
    private JLabel codePostal_label = new JLabel ("Code Postal : ");
    private JLabel ville_label = new JLabel ("Ville  : ");
    
    
    private JTextField numeroRue = new JTextField();
    private JTextField nom_rue = new JTextField();
    private JTextField codePostal = new JTextField();
    private JTextField ville = new JTextField();
    
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
        numeroRue.setText(uneMaison.getNumeroRue());
        nom_rue.setText(uneMaison.getNomRue());
        codePostal.setText(uneMaison.getCodePostal());
        ville.setText(uneMaison.getVille());
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
        panneau_info.add(this.numeroRue_label);
        panneau_info.add(this.numeroRue);
        panneau_info.add(this.nomRue_label);
        panneau_info.add(this.nom_rue);
        panneau_info.add(this.codePostal_label);
        panneau_info.add(this.codePostal);
        panneau_info.add(this.ville_label);
        panneau_info.add(this.ville);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            
            if(e.getSource()==ajouter){
                try{
                    verifierChamps();
                    uneMaison=new Maison(0 ,numeroRue.getText(), nom_rue.getText(), codePostal.getText(), ville.getText());
                    Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                    Maison_DAO nouvelleMaison = new Maison_DAO(connBdd);
                    nouvelleMaison.create(uneMaison);
                    PopupInformation popup=new PopupInformation("La maison au nom de rue : "+uneMaison.getNomRue()+" a été ajouté.");
                    reset();
                }catch (EmptyFieldException ex) {}
            }
        
        if(e.getSource()==modifier){
            try {
                verifierChamps();
                uneMaison = new Maison(uneMaison.getID() ,numeroRue.getText(), nom_rue.getText(), codePostal.getText(), ville.getText());
                Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                Maison_DAO nouvelleMaison = new Maison_DAO(connBdd);
                nouvelleMaison.update(uneMaison);
                PopupInformation popup=new PopupInformation("La maison au nom de rue : "+uneMaison.getNomRue()+" a été modifier.");
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
        this.numeroRue.setText("");
        this.nom_rue.setText("");
        this.codePostal.setText("");
        this.ville.setText("");
    }
    
    public void verifierChamps() throws EmptyFieldException{
        if(this.numeroRue.getText().equals("")){
            throw new EmptyFieldException("un numero de rue");
        }
        if(this.nom_rue.getText().equals("")){
            throw new EmptyFieldException("un nom de rue");
        }
        if(this.codePostal.getText().equals("")){
            throw new EmptyFieldException("un code postal");
        }
        if(this.ville.getText().equals("")){
            throw new EmptyFieldException("une ville");
        }
    }
}