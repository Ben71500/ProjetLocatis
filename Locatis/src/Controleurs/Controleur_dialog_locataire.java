/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleurs;

import Modeles.Modele_dialog_locataire;
import Vues.Vue_dialog_locataire;
import Objets_Locatis.Campagne;
import Objets_Locatis.Locataire;
import Objets_Locatis.Utilisateur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author benja
 */
public class Controleur_dialog_locataire implements ActionListener{
    private Utilisateur userConnecte;
    private Vue_dialog_locataire laVue;
    private Modele_dialog_locataire leModele;
    
    
    public Controleur_dialog_locataire(Locataire loca, Utilisateur user, Vue_dialog_locataire laVue, Modele_dialog_locataire leModele){
        this.laVue = laVue;
        this.leModele = leModele;
        this.userConnecte = user;
        ArrayList<Campagne> listeCampagne = leModele.getListeCampagne(loca.getId());
        String contenueLabel= "";
        for(int i = 0; i < listeCampagne.size(); i++){
            contenueLabel += listeCampagne.get(i).getTitre()+" a pour objet : "+listeCampagne.get(i).getObjetMail()+" commence le : "+listeCampagne.get(i).getDateDebut().getDateEcrite()+" et finis le : "+listeCampagne.get(i).getDateFin().getDateEcrite()+" à été lancé par : "+listeCampagne.get(i).getUtilisateur().getLogin()+"\n";
        }
        this.laVue.getLabel().setText(contenueLabel);
        this.laVue.ajouterEcouteurBouton("retour", this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        switch (source.getText().toUpperCase()) {
            case "RETOUR" -> {
                laVue.quitter();
                
            }
        }
    }

    public Utilisateur getUserConnecte() {
        return userConnecte;
    }

    public void setUserConnecte(Utilisateur userConnecte) {
        this.userConnecte = userConnecte;
    }

    public Vue_dialog_locataire getLaVue() {
        return laVue;
    }

    public void setLaVue(Vue_dialog_locataire laVue) {
        this.laVue = laVue;
    }

    public Modele_dialog_locataire getLeModele() {
        return leModele;
    }

    public void setLeModele(Modele_dialog_locataire leModele) {
        this.leModele = leModele;
    }
    
    
}
