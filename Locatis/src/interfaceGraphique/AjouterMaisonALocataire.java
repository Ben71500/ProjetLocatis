/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaceGraphique;

import DAO.Maison_DAO;
import DAO.Locataire_DAO;
import DAO.Connexion;
import DAO.ConnectionBDD;
import DAO.Habiter_DAO;
import DAO.Appartement_DAO;
import Locatis.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Benjamin
 */
public class AjouterMaisonALocataire extends JFrame implements ActionListener{
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel("Ajouter batiment a un locataire");
    private JLabel batiment_label = new JLabel ("Batiment : ");
    private JLabel locataire_label = new JLabel ("Locataire : ");
   
    private JComboBox batiment = new JComboBox();
    private JComboBox locataire = new JComboBox();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    
    public AjouterMaisonALocataire(Utilisateur user) {
        
        super("Ajouter batiment a un locataire");
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
        panneau_info.add(this.batiment_label);
        remplirBatiment();
        panneau_info.add(this.batiment);
        panneau_info.add(this.locataire_label);
        remplirLocataire();
        panneau_info.add(this.locataire);
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        this.ajouter.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    public void remplirBatiment(){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        Appartement_DAO appart =new Appartement_DAO(connBdd);
        ArrayList<Appartement> listeAppartements = new ArrayList<>(appart.getAll());
        for(int i = 0; i < listeAppartements.size(); i++){
            this.batiment.addItem(listeAppartements.get(i));
        }
        Maison_DAO maison =new Maison_DAO(connBdd);
        ArrayList<Maison> listeMaison = new ArrayList<>(maison.getAll());
        for(int i = 0; i < listeMaison.size(); i++){
            this.batiment.addItem(listeMaison.get(i));
        }
        panneau_info.add(this.batiment);
    }
    
    public void remplirLocataire(){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        Locataire_DAO locataire =new Locataire_DAO(connBdd);
        ArrayList<Locataire> listeLoca = new ArrayList<>(locataire.getAll());
        for(int i = 0; i < listeLoca.size(); i++){
            this.locataire.addItem(listeLoca.get(i));
        }
        panneau_info.add(this.locataire);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ajouter){
            Batiment batimentSelection = (Batiment) batiment.getSelectedItem();
            Locataire locataireSelection = (Locataire) locataire.getSelectedItem();
            Connection connBdd= ConnectionBDD.getInstance(new Connexion());
            System.out.println(ConnectionBDD.isInstanceOf(new Connexion()));
            Habiter_DAO nouvelUtilisateur=new Habiter_DAO(connBdd);
            Habiter habitation = new Habiter(locataireSelection.getId(), batimentSelection.getID());
            nouvelUtilisateur.create(habitation);
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
