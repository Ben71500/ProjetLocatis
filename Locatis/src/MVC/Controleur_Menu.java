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
        uneVue.ajouterEcouteurBouton("Statistiques", this);
        uneVue.ajouterEcouteurBouton("Déconnexion", this);
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
                        Controleur_Gestion_Listes controleur = new Controleur_Gestion_Listes(new Vue_Gestion_Listes(), new Modele_Gestion_Listes(), userConnecte);
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("locataire"), new Modele_Gestion("locataires"), userConnecte, "locataire");
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("utilisateur"), new Modele_Gestion("utilisateurs"), userConnecte, "utilisateur");
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("appartement"), new Modele_Gestion("appartements"), userConnecte, "appartement");                
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
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion("campagne"), new Modele_Gestion("campagnes"), userConnecte, "campagne");                
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
            case "STATISTIQUES" -> {
                laVue.quitter();
            }
            case "DECONNEXION" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        SeConnecter fenetre=new SeConnecter();
                        fenetre.setBounds(100, 100, 350, 200);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            }
            case "QUITTER" -> {
                laVue.quitter();
            }
        }
    }
}