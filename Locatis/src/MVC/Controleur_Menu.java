package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controleur_Menu implements ActionListener {

    private final Vue_Menu laVue;
    private final Modele_Gestion leModele;
    private Utilisateur userConnecte;

    public Controleur_Menu(Vue_Menu uneVue, Modele_Gestion unModele, Utilisateur user) {
        this.userConnecte = user;
        this.laVue = uneVue;
        this.leModele = unModele;
        
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

    public Vue_Menu getVue() {
        return laVue;
    }

    public Modele_Gestion getModele() {
        return leModele;
    }
    
    public Utilisateur getUser(){
        return this.userConnecte;
    }

    //Méthode de la classe abstraite KeyAdapter
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (source.getText().toUpperCase()) {
            case "GESTION DES LISTES DE DIFFUSION" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        //Controleur_Ajout_Listes controleur = new Controleur_Ajout_Listes(new Vue_Ajout_Listes(), new Modele_Ajout_Listes(), userConnecte);
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("liste"), new Modele_Gestion("liste"), userConnecte, "liste");
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("locataire"), new Modele_Gestion("locataire"), userConnecte, "locataire");
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("utilisateur"), new Modele_Gestion("utilisateur"), userConnecte, "utilisateur");
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("appartement"), new Modele_Gestion("appartement"), userConnecte, "appartement");                
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("campagne"), new Modele_Gestion("campagne"), userConnecte, "campagne");                
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