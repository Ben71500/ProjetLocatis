package interfaceGraphique;

import DAO.Appartement_DAO;
import DAO.Locataire_DAO;
import DAO.Connexion;
import DAO.ConnectionBDD;
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

public class ModifierAppartement extends JFrame implements ActionListener {
    
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
    private JLabel numeroEtage = new JLabel ("Numéro d'étage : ");
    private JLabel numeroAppart = new JLabel("Numéro d'appartement : ");
    
    private JTextField numeroRue = new JTextField();
    private JTextField nom_rue = new JTextField();
    private JTextField codePostal = new JTextField();
    private JTextField ville = new JTextField();
    private JTextField numEtage = new JTextField();
    private JTextField numApart = new JTextField();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    private Appartement unAppartement;
    
    public ModifierAppartement(Utilisateur user) {
        super("Ajouter un appartement");
        userConnecte=user;
        titre.setText("Ajouter un appartement");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        this.ajouter.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public ModifierAppartement(Utilisateur user, Appartement appart) {
        super("Modifier un appartement");
        userConnecte=user;
        titre.setText("Modifier un appartement");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        this.modifier.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.unAppartement=appart;
        numeroRue.setText(unAppartement.getNumeroRue());
        nom_rue.setText(unAppartement.getNomRue());
        codePostal.setText(unAppartement.getCodePostal());
        ville.setText(unAppartement.getVille());
        numEtage.setText(unAppartement.getEtage()+"");
        numApart.setText(unAppartement.getApart()+"");
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
        panneau_info.add(this.numeroEtage);
        panneau_info.add(this.numEtage);
        panneau_info.add(this.numeroAppart);
        panneau_info.add(this.numApart);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            
            if(e.getSource()==ajouter){
                try{
                    verifierChamps();
                    unAppartement=new Appartement(0 ,numeroRue.getText(), nom_rue.getText(), codePostal.getText(), ville.getText(), Integer.parseInt(numApart.getText()), Integer.parseInt(numEtage.getText()));
                    Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                    Appartement_DAO nouvelleAppartement = new Appartement_DAO(connBdd);
                    nouvelleAppartement.create(unAppartement);
                    PopupInformation popup=new PopupInformation("L'appartement au nom de rue : "+unAppartement.getNomRue()+" au numéro d'étage : "+unAppartement.getEtage()+" et au numéro d'appartement : "+unAppartement.getApart()+ "a été ajouté.");
                    reset();
                }catch (EmptyFieldException ex) {}
            }
        
        if(e.getSource()==modifier){
            try {
                verifierChamps();
                unAppartement = new Appartement(unAppartement.getID() ,numeroRue.getText(), nom_rue.getText(), codePostal.getText(), ville.getText(), Integer.parseInt(numApart.getText()), Integer.parseInt(numEtage.getText()));
                Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                Appartement_DAO nouvelleAppartement = new Appartement_DAO(connBdd);
                nouvelleAppartement.update(unAppartement);
                PopupInformation popup=new PopupInformation("L'appartement au nom de rue : "+unAppartement.getNomRue()+" au numéro d'étage : "+unAppartement.getEtage()+" et au numéro d'appartement : "+unAppartement.getApart()+ "a été modifié.");
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
        this.numEtage.setText("");
        this.numApart.setText("");
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
        if(this.numEtage.getText().equals("")){
            throw new EmptyFieldException("un numéro d'étage");
        }
        if(this.numApart.getText().equals("")){
            throw new EmptyFieldException("un numéro d'appart");
        }
    }
}