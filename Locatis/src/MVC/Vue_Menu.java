package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.table.*;

public class Vue_Menu extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    
    private JLabel titre = new JLabel();
    
    private JButton gestionListes = new JButton("Gestion des listes de diffusion");
    private JButton gestionLocataires = new JButton("Gestion des locataires");
    private JButton gestionUtilisateurs = new JButton("Gestion des utilisateurs");
    private JButton gestionBatiments = new JButton("Gestion des batiments");
    private JButton gestionCampagnes = new JButton("Gestion des campagnes");
    private JButton statistiques = new JButton("Voir les statistiques");
    private JButton importer = new JButton("Importer des données");
    
    private JButton deconnexion = new JButton("Se déconnecter");
    private JButton quitter = new JButton("Quitter");
    
    private Utilisateur userConnecte;

    public Vue_Menu(Utilisateur user) {
        super("Menu Principal");
        this.userConnecte = user;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        //Titre
        titre.setText("Bonjour "+userConnecte.getLogin()+" !");
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        //Ajout des différents boutons
        centre.setLayout(new GridLayout(7,1));
        centre.add(this.gestionListes);
        centre.add(this.gestionLocataires);
        centre.add(this.gestionUtilisateurs);
        centre.add(this.gestionBatiments);
        centre.add(this.gestionCampagnes);
        centre.add(this.statistiques);
        centre.add(this.importer);
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(deconnexion);
        panneau_boutons.add(quitter);
        
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
            case "LISTES" ->
                bouton = gestionListes;
            case "LOCATAIRES" ->
                bouton = gestionLocataires;
            case "UTILISATEURS" ->
                bouton = gestionUtilisateurs;
            case "BATIMENTS" ->
                bouton = gestionBatiments;
            case "CAMPAGNES" ->
                bouton = gestionCampagnes;
            case "STATISTIQUES" ->
                bouton = statistiques;
            case "IMPORTER" ->
                bouton = importer;
            case "DECONNEXION" ->
                bouton = deconnexion;
            case "QUITTER" ->
                bouton = quitter;
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