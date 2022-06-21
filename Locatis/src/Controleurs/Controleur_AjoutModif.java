package Controleurs;

import Popups.PopupInformation;
import Exceptions.ValeurIncorrecteException;
import Exceptions.EmptyFieldException;
import Exceptions.PasDeLignesSelectionneesException;
import Modeles.Modele_AjoutModif;
import Vues.Vue_AjoutModif;
import Vues.Vue_AjoutModif_Appartement;
import Vues.Vue_AjoutModif_Campagne;
import Vues.Vue_AjoutModif_Locataires;
import Vues.Vue_AjoutModif_Maison;
import Vues.Vue_AjoutModif_Utilisateurs;
import Objets_Locatis.Appartement;
import Objets_Locatis.Maison;
import Objets_Locatis.Utilisateur;
import Objets_Locatis.Locataire;
import Objets_Locatis.Campagne;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.JComboBox;

/**
 * Class controleur de l'interface Ajout et modification d'element implementant l'inteface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_AjoutModif implements ActionListener{
    
    //private final Vue_AjoutModif_Locataires laVue;
    private Vue_AjoutModif laVue;
    private Modele_AjoutModif leModele;
    private Utilisateur userConnecte;
    private String typeDonnee;

    /**
     * Constructeur de l'objet Controleur_AjoutModif pour la partie Ajouter
     * @param user : Utilisateur en cours d'utilisation
     * @param donnee : les données 
     */
    public Controleur_AjoutModif(Utilisateur user, String donnee){
        this.userConnecte = user;
        this.typeDonnee = donnee;
        this.leModele = new Modele_AjoutModif(typeDonnee);
        switch(donnee){
            case "locataire" -> this.laVue = new Vue_AjoutModif_Locataires();
            case "utilisateur" -> this.laVue = new Vue_AjoutModif_Utilisateurs(user);
            case "campagne" -> this.laVue = new Vue_AjoutModif_Campagne(leModele.retournerListesDeDiffusion());
            case "maison" -> this.laVue = new Vue_AjoutModif_Maison();
            case "appartement" -> this.laVue = new Vue_AjoutModif_Appartement();
            default -> this.laVue = null;
        }
        
        laVue.ajouterEcouteurBouton("Ajouter", this);
        laVue.ajouterEcouteurBouton("Modifier", this);
        laVue.ajouterEcouteurBouton("Retour", this);
        
        if(donnee.equals("campagne")){
            laVue.ajouterEcouteurBouton("Frequence", this);
        }
    }
    
    /**
     * Constructeur de l'objet Controleur _AjoutModif pour la partie Modifier
     * @param user : Utilisateur en cours d'utilisation
     * @param donnee : Les données 
     * @param obj : Objet à modifier
     */
    public Controleur_AjoutModif(Utilisateur user, String donnee, Object obj){
        this.userConnecte = user;
        this.typeDonnee = donnee;
        this.leModele = new Modele_AjoutModif(donnee);
        switch(donnee){
            case "locataire" -> this.laVue = new Vue_AjoutModif_Locataires((Locataire) obj);
            case "utilisateur" -> this.laVue = new Vue_AjoutModif_Utilisateurs((Utilisateur) obj, user);
            case "campagne" -> this.laVue = new Vue_AjoutModif_Campagne(leModele.retournerListesDeDiffusion(), (Campagne) obj, userConnecte);
            case "maison" -> this.laVue = new Vue_AjoutModif_Maison((Maison) obj);
            case "appartement" -> this.laVue = new Vue_AjoutModif_Appartement((Appartement) obj);
            default -> this.laVue = null;
        }
        
        laVue.ajouterEcouteurBouton("Ajouter", this);
        laVue.ajouterEcouteurBouton("Modifier", this);
        laVue.ajouterEcouteurBouton("Retour", this);
        
        if(donnee.equals("campagne")){
            /*laVue.getFrequence().addActionListener(this);*/
            laVue.ajouterEcouteurBouton("Frequence", this);
        }
    }

    /**
     *
     * @return Vue_AjoutModif
     */
    public Vue_AjoutModif getVue() {
        return laVue;
    }

    /**
     *
     * @return Modele_AjoutModif
     */
    public Modele_AjoutModif getModele() {
        return leModele;
    }
    
    /**
     *
     * @return Utilisateur
     */
    public Utilisateur getUser(){
        return this.userConnecte;
    }

    /**
     * Méthode de la classe abstraite KeyAdapter
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        
        JComboBox cb = new JComboBox();
        if(e.getSource().getClass().isInstance(cb)){
            cb=(JComboBox) e.getSource();
            String newSelection = (String) cb.getSelectedItem();
            this.laVue.afficherVue();
            /*if(cb.getSelectedItem().toString().equals("Une seule fois")){
                System.out.println("ok");
                this.laVue=(Vue_AjoutModif_Campagne)laVue.test();*/
                //laVue.test();
                //Vue_AjoutModif_Campagne vue = (Vue_AjoutModif_Campagne)laVue;
            //}
        }else{
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    try{
                        this.laVue.verifierChamps();
                        if(typeDonnee.equals("campagne")){
                            Campagne laCampagne = (Campagne) laVue.getNouvelObjet();
                            laCampagne.setUtilisateur(userConnecte);
                            leModele.ajouter(laCampagne);
                        }else{
                            leModele.ajouter(laVue.getNouvelObjet());
                        }
                        PopupInformation popup=new PopupInformation(this.typeDonnee+" ajouté.");
                        laVue.reset();
                    }catch (EmptyFieldException ex) {
                        ex.afficherErreur();
                    }catch (PasDeLignesSelectionneesException ex){
                        ex.afficherErreur();
                    }catch (ValeurIncorrecteException ex) {
                        ex.afficherErreur();
                    }
                }
                case "MODIFIER" -> {
                    try {
                        this.laVue.verifierChamps();
                        if(typeDonnee.equals("campagne")){
                            Campagne laCampagne = (Campagne) laVue.getObjetModifie();
                            laCampagne.setUtilisateur(userConnecte);
                            leModele.modifier(laCampagne);
                        }else{
                            leModele.modifier(laVue.getObjetModifie());
                        }
                        PopupInformation popup=new PopupInformation(this.typeDonnee+" modifié.");
                        laVue.quitter();
                        SwingUtilities.invokeLater(new Runnable(){
                            public void run(){
                                Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, typeDonnee);               
                                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                controleur.getVue().setSize(500,500);
                                controleur.getVue().setVisible(true);
                            }
                        });
                    } catch (EmptyFieldException ex) {
                        ex.afficherErreur();
                    }catch (PasDeLignesSelectionneesException ex){
                        ex.afficherErreur();
                    } catch (ValeurIncorrecteException ex) {
                        ex.afficherErreur();
                    }
                }
                case "RETOUR" -> {
                    laVue.quitter();
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, typeDonnee);                
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setSize(500,500);
                            controleur.getVue().setVisible(true);
                        }
                    });
                }
            }
        }
    }
}