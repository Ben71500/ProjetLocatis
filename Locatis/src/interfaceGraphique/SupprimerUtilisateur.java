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

public class SupprimerUtilisateur extends JFrame implements ActionListener {
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel("Supprimer un Utilisateur");
    private JLabel label = new JLabel("ID : ");
    
    private JComboBox ID = new JComboBox();
    
    private JButton supprimer = new JButton("Supprimer");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    
    public SupprimerUtilisateur(Utilisateur user) {
        super("Supprimer un utilisateur");
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
        
        panneau_info.setLayout(new GridLayout(1,2));
        
        panneau_info.add(label);
        listeId();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(supprimer);
        panneau_boutons.add(retour);
        this.supprimer.addActionListener(this);
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
    
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==supprimer){
            if(confirmerSuppression()){
                Utilisateur utilisateurSupprimer = (Utilisateur) ID.getSelectedItem();          
                Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                Utilisateurs_DAO nouvelUtilisateur=new Utilisateurs_DAO(connBdd);
                nouvelUtilisateur.delete(utilisateurSupprimer);
                ID.removeItem(ID.getSelectedItem());
            }
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
    
    public boolean confirmerSuppression(){
        JFrame popup = new JFrame();
        int choix = JOptionPane.showConfirmDialog(popup,"Confirmer la suppression ?","Supprimer", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        popup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return choix==0;
    }
}
