package MVC;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controleur_Connexion implements ActionListener {

    private final Vue_Connexion laVue;
    private final Modele_Connexion leModele;

    public Controleur_Connexion(Vue_Connexion uneVue, Modele_Connexion unModele) {
        this.laVue = uneVue;
        this.leModele = unModele;
        
        uneVue.ajouterEcouteurBouton("Connexion", this);
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
        if(source.equals("CONNEXION")){
            laVue.quitter();
            SwingUtilities.invokeLater(new Runnable(){
                @Override
                public void run(){
                    /*Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(adm),new Modele_Gestion("locataires"), adm);
                    controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    controleur.getVue().setSize(800,500);
                    controleur.getVue().setVisible(true);*/
                }
            });
        }
    }
}