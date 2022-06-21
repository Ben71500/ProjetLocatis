package Controleurs;

import Modeles.Modele_Connexion;
import Vues.Vue_Connexion;
import Vues.Vue_Menu;
import Objets_Locatis.Utilisateur;
import Exceptions.EmptyFieldException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
 * Classe de l'interface de connexion implementant l'inteface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_Connexion implements ActionListener {

    private final Vue_Connexion laVue;
    private final Modele_Connexion leModele;

    /**
     * Constructeur de l'objet Controleur_Connexions
     * @param uneVue : vue du controleur
     * @param unModele : modele du controleur
     */
    public Controleur_Connexion(Vue_Connexion uneVue, Modele_Connexion unModele) {
        this.laVue = uneVue;
        this.leModele = unModele;
        
        uneVue.ajouterEcouteurBouton("Connexion", this);
        uneVue.getLogin().addKeyListener(toucheEntree());
        uneVue.getMotDePasse().addKeyListener(toucheEntree());
    }

    /**
     *
     * @return Vue_Connexion
     */
    public Vue_Connexion getVue() {
        return laVue;
    }

    /**
     *
     * @return Modele_Connexion
     */
    public Modele_Connexion getModele() {
        return leModele;
    }

    /**
     * Méthode de la classe abstraite KeyAdapter
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if(source.getText().toUpperCase().equals("CONNEXION")){
            seConnecter();
        }
    }
    
    /**
     * Méthode de la classe abstraite KeyListener
     * @return
     */
    public KeyListener toucheEntree(){
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    seConnecter();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {
            }
        };
    }
    
    /**
     * Méthode qui vérifie la saisie des champs et change l'inteface à l'utilisateur
     */
    public void seConnecter(){
        try{
            this.laVue.verifierChamps();
            Utilisateur userConnecte = this.leModele.trouverUser(this.laVue.getLoginTexte(), this.laVue.getMotDePasseTexte(), this.laVue.getMessageErreur());
            if(userConnecte != null){
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(userConnecte), userConnecte);
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(800,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
        }catch (EmptyFieldException ex) {
            ex.afficherErreur();
        }
    }
}