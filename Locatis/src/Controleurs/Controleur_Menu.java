package Controleurs;

import Modeles.Modele_Association;
import Modeles.Modele_Connexion;
import Modeles.Modele_Statistique;
import Vues.Vue_Association;
import Vues.Vue_Connexion;
import Vues.Vue_Menu;
import Vues.Vue_Statistique;
import Objets_Locatis.Utilisateur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe Controleur_menu implementant l'interface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_Menu implements ActionListener {

    private final Vue_Menu laVue;
    private Utilisateur userConnecte;

    /**
     * Constructeur de l'objet Controleur_Menu
     * @param uneVue : vue du controleur
     * @param user : Utilisateur en cours d'utilisation
     */
    public Controleur_Menu(Vue_Menu uneVue, Utilisateur user) {
        this.userConnecte = user;
        this.laVue = uneVue;
        
        uneVue.ajouterEcouteurBouton("Listes", this);
        uneVue.ajouterEcouteurBouton("Locataires", this);
        uneVue.ajouterEcouteurBouton("Utilisateurs", this);
        uneVue.ajouterEcouteurBouton("Batiments", this);
        uneVue.ajouterEcouteurBouton("Campagnes", this);
        uneVue.ajouterEcouteurBouton("Associer", this);
        uneVue.ajouterEcouteurBouton("Statistiques", this);
        uneVue.ajouterEcouteurBouton("Deconnexion", this);
        uneVue.ajouterEcouteurBouton("Quitter", this);
    }

    /**
     *
     * @return Vue_Menu
     */
    public Vue_Menu getVue() {
        return laVue;
    }

    /**
     * Méthode de la classe abstraite KeyAdapter
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (source.getText().toUpperCase()) {
            case "GESTION DES LISTES DE DIFFUSION" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "liste");
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "GESTION DES LOCATAIRES" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "locataire");
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "GESTION DES UTILISATEURS" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "utilisateur");
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "GESTION DES BATIMENTS" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "appartement");                
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "GESTION DES CAMPAGNES" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "campagne");                
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "ASSOCIER UN LOGEMENT" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        Controleur_Association controleur = new Controleur_Association(new Vue_Association(), new Modele_Association(), userConnecte);
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "VOIR LES STATISTIQUES" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        Controleur_Statistique controleur = new Controleur_Statistique(new Vue_Statistique(), new Modele_Statistique(), userConnecte);
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "SE DÉCONNECTER" -> {
                laVue.quitter();
                Controleur_Connexion controleur = new Controleur_Connexion(new Vue_Connexion(), new Modele_Connexion());
                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                controleur.getVue().setSize(500,300);
                controleur.getVue().setVisible(true);
            }
            case "QUITTER" -> {
                laVue.quitter();
            }
        }
    }
}