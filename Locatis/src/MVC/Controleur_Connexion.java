package MVC;

import Locatis.Utilisateur;
import interfaceGraphique.EmptyFieldException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Controleur_Connexion implements ActionListener {

    private final Vue_Connexion laVue;
    private final Modele_Connexion leModele;

    public Controleur_Connexion(Vue_Connexion uneVue, Modele_Connexion unModele) {
        this.laVue = uneVue;
        this.leModele = unModele;
        
        uneVue.ajouterEcouteurBouton("Connexion", this);
        uneVue.getLogin().addKeyListener(toucheEntree());
        uneVue.getMotDePasse().addKeyListener(toucheEntree());
    }

    public Vue_Connexion getVue() {
        return laVue;
    }

    public Modele_Connexion getModele() {
        return leModele;
    }

    //Méthode de la classe abstraite KeyAdapter
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        if(source.getText().toUpperCase().equals("CONNEXION")){
            seConnecter();
        }
    }
    
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