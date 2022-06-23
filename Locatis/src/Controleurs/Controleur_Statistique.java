package Controleurs;

import Vues.Vue_Menu;
import Vues.Vue_Statistique;
import Objets_Locatis.Utilisateur;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Classe de l'objet Conroleur_Statistique implementant l'interface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_Statistique implements ActionListener{

    private final Vue_Statistique laVue;
    private Utilisateur userConnecte;

    /**
     * Constructeur de l'objet Controleur_Statistique
     * @param uneVue : vue du controleur
     * @param user : Utilisateur en cours d'utilisation
     */
    public Controleur_Statistique(Vue_Statistique uneVue, Utilisateur user) {
        laVue = uneVue;
        userConnecte = user;
        this.laVue.getGraphiqueDesCampagnesTerminer().setData("campagne");
        this.laVue.getGraphiqueParLogementOccuper().setData("logement");
        this.laVue.getGraphiqueParTrancheAge().setData("age");
        this.laVue.getCentre().add(this.laVue.getGraphiqueParLogementOccuper().createDemoPanel());
        this.laVue.getCentre().add(this.laVue.getGraphiqueParTrancheAge().createDemoPanel());
        this.laVue.getCentre().add(this.laVue.getGraphiqueDesCampagnesTerminer().createDemoPanel());
        this.laVue.pack();
        this.laVue.ajouterEcouteur("retour", this);
    }
    
    /**
     *
     * @return Vue_Statistique
     */
    public Vue_Statistique getVue() {
        return laVue;
    }

    /**
     * Méthode de la classe abstraite ActionListener
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (source.getText().toUpperCase()) {
            case "RETOUR" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    @Override
                    public void run(){
                        Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(userConnecte), userConnecte);
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
                        int height = 600;
                        int width = 500;
                        controleur.getVue().setBounds((tailleEcran.width-width)/2, (tailleEcran.height-height)/2, width, height);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
        }
    }
}