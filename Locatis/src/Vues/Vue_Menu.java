package Vues;

import Objets_Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import Graphique.Panneau;
import Graphique.Bouton;

/**
 * Classe dérivée de JFrame qui décrit la vue permettant d'afficher le menu principal
 * @author Benjamin Mathilde
 */
public class Vue_Menu extends JFrame {
    
    private ImageIcon logo = new ImageIcon("../logo.jpg");

    private Panneau panneau = new Panneau();
    private Panneau haut = new Panneau();
    private Panneau centre = new Panneau();
    private Panneau panneau_boutons = new Panneau();
    
    private JLabel titre = new JLabel();
    
    private Bouton gestionListes = new Bouton("Gestion des listes de diffusion");
    private Bouton gestionLocataires = new Bouton("Gestion des locataires");
    private Bouton gestionUtilisateurs = new Bouton("Gestion des utilisateurs");
    private Bouton gestionBatiments = new Bouton("Gestion des batiments");
    private Bouton gestionCampagnes = new Bouton("Gestion des campagnes");
    private Bouton statistiques = new Bouton("Voir les statistiques");
    private Bouton associer = new Bouton("Associer un logement");
    
    private Bouton deconnexion = new Bouton("Se déconnecter");
    private Bouton quitter = new Bouton("Quitter");
    
    private Utilisateur userConnecte;

    /**
     * Constructeur de la vue
     * @param user : l'utilisateur qui est connecté
     */
    public Vue_Menu(Utilisateur user) {
        super("Menu Principal");
        this.setIconImage(logo.getImage());
        this.userConnecte = user;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        //Titre
        titre.setText("Bonjour "+userConnecte.getLogin()+" !");
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        //Choix du menu correspondant à la catégorie de l'utilisateur
        switch(this.userConnecte.getCat()){
            case "adm" -> menuAdministrateurs();
            case "ges1", "ges2", "ges3" -> menuGestionnaires();
            case "uti1" -> menuUtilisateurs1();
            case "uti2" -> menuUtilisateurs2();
        }
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(deconnexion);
        panneau_boutons.add(quitter);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    /**
     * Méthode qui permet d'ajouter des écouteurs à un bouton
     * @param nomBouton : nom du bouton
     * @param listener : écouteur
     */
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        Bouton bouton;
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
            case "ASSOCIER" ->
                bouton = associer;
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

    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
    
    /**
     * Méthode qui ajoute les boutons en fonction des droits
     * des utilisateurs dont la catégorie est "Utilisateur 1"
     */
    public void menuUtilisateurs1(){
        centre.setLayout(new GridLayout(1,1));
        centre.add(this.statistiques);
    }
    
    /**
     * Méthode qui ajoute les boutons en fonction des droits
     * des utilisateurs dont la catégorie est "Utilisateur 2"
     */
    public void menuUtilisateurs2(){
        centre.setLayout(new GridLayout(2,1));
        centre.add(this.gestionCampagnes);
        centre.add(this.statistiques);
    }
    
    /**
     * Méthode qui ajoute les boutons en fonction des droits
     * des utilisateurs dont la catégorie est "Gestionnaire" (1,2 et 3)
     */
    public void menuGestionnaires(){
        centre.setLayout(new GridLayout(4,1));
        centre.add(this.gestionCampagnes);
        centre.add(this.gestionUtilisateurs);
        centre.add(this.gestionListes);
        centre.add(this.statistiques);
    }
    
    /**
     * Méthode qui ajoute les boutons en fonction des droits
     * des utilisateurs dont la catégorie est "Administrateur"
     */
    public void menuAdministrateurs(){
        centre.setLayout(new GridLayout(7,1));
        centre.add(this.gestionLocataires);
        centre.add(this.gestionUtilisateurs);
        centre.add(this.gestionBatiments);
        centre.add(this.associer);
        centre.add(this.gestionListes);
        centre.add(this.gestionCampagnes);
        centre.add(this.statistiques);
    }
}