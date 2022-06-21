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

/**
 * Classe de l'objet Controleur_dialog_locataire implementant ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_dialog_locataire implements ActionListener{
    
    private Utilisateur userConnecte;
    private Vue_dialog_locataire laVue;
    private Modele_dialog_locataire leModele;
    
    /**
     * Constructeur de l'objet Controleur_dialog_locataire
     * @param loca : Locataire selectionné
     * @param user : Utiliseur en cours d'utilisation
     * @param laVue : vue du controleur
     * @param leModele : modele du controleur
     */
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
    
    /**
     *
     * @return Vue_dialog_locataire
     */
    public Vue_dialog_locataire getLaVue() {
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
            }
        }
    }
}