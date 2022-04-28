package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Controleur_AjoutModif implements ActionListener{
    
    //private final Vue_AjoutModif_Locataires laVue;
    private Vue_AjoutModif laVue;
    private Modele_AjoutModif leModele;
    private Utilisateur userConnecte;
    private String typeDonnee;

    public Controleur_AjoutModif(Utilisateur user, String donnee){
        this.userConnecte = user;
        this.typeDonnee = donnee;
        this.leModele = new Modele_AjoutModif(donnee+"s");
        switch(donnee){
            case "locataire" -> this.laVue = new Vue_AjoutModif_Locataires();
            case "utilisateur" -> this.laVue = new Vue_AjoutModif_Utilisateurs();
            case "campagne" -> this.laVue = new Vue_AjoutModif_Campagne();
            case "maison" -> this.laVue = new Vue_AjoutModif_Maison();
            case "appartement" -> this.laVue = new Vue_AjoutModif_Appartement();
            default -> this.laVue = null;
        }
        
        laVue.ajouterEcouteurBouton("Ajouter", this);
        laVue.ajouterEcouteurBouton("Modifier", this);
        laVue.ajouterEcouteurBouton("Retour", this);
    }
    
        public Controleur_AjoutModif(Utilisateur user, String donnee, Object obj){
        this.userConnecte = user;
        this.typeDonnee = donnee;
        this.leModele = new Modele_AjoutModif(donnee+"s");
        switch(donnee){
            case "locataire" -> this.laVue = new Vue_AjoutModif_Locataires((Locataire) obj);
            case "utilisateur" -> this.laVue = new Vue_AjoutModif_Utilisateurs((Utilisateur) obj);
            case "campagne" -> this.laVue = new Vue_AjoutModif_Campagne((Campagne) obj);
            case "maison" -> this.laVue = new Vue_AjoutModif_Maison((Maison) obj);
            case "appartement" -> this.laVue = new Vue_AjoutModif_Appartement((Appartement) obj);
            default -> this.laVue = null;
        }
        
        laVue.ajouterEcouteurBouton("Ajouter", this);
        laVue.ajouterEcouteurBouton("Modifier", this);
        laVue.ajouterEcouteurBouton("Retour", this);
    }

    public Vue_AjoutModif getVue() {
        return laVue;
    }

    public Modele_AjoutModif getModele() {
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
            case "AJOUTER" -> {
                try{
                    this.laVue.verifierChamps();
                    switch(this.typeDonnee){
                        case "locataire" -> {
                            Locataire leLocataire = (Locataire) laVue.getNouvelObjet();
                            leModele.ajouterLocataire(leLocataire);
                            PopupInformation popup=new PopupInformation("Le locataire "+leLocataire.getNom()+" "+leLocataire.getPrenom()+" a été ajouté.");
                        }
                        case "utilisateur" -> {
                            Utilisateur nouvelUtilisateur = (Utilisateur) laVue.getNouvelObjet();
                            leModele.ajouterUtilisateur(nouvelUtilisateur);
                            PopupInformation popup=new PopupInformation("L'utilisateur "+nouvelUtilisateur.getLogin()+" a été ajouté.");
                        }
                        case "message" -> {
                            Message leMessage = (Message) laVue.getNouvelObjet();
                            leModele.ajouterMessage(leMessage);
                            PopupInformation popup=new PopupInformation("Le message a bien été ajouté.");
                        }
                        case "campagne" -> {
                            Campagne laCampagne = (Campagne) laVue.getNouvelObjet();
                            laCampagne.setUtilisateur(userConnecte);
                            leModele.ajouterCampagne(laCampagne);
                            PopupInformation popup=new PopupInformation("La campagne a bien été ajoutée.");
                        }
                        case "appartement" -> {
                            Appartement unAppartement = (Appartement) laVue.getNouvelObjet();
                            leModele.ajouterAppartement(unAppartement);
                            PopupInformation popup=new PopupInformation("L'appartement a bien été ajoutée.");
                        }
                        case "maison" -> {
                            Maison uneMaison = (Maison) laVue.getNouvelObjet();
                            leModele.ajouterMaison(uneMaison);
                            PopupInformation popup=new PopupInformation("La maison a bien été ajoutée.");
                        }
                    }
                    laVue.reset();
                }catch (EmptyFieldException ex) {
                    ex.afficherErreur();
                }catch (PasDeLignesSelectionneesException ex){
                    ex.afficherErreur();
                }
            }
            case "MODIFIER" -> {
                try {
                    this.laVue.verifierChamps();
                    switch(this.typeDonnee){
                        case "locataire" -> {
                            Locataire leLocataire = (Locataire) laVue.getObjetModifie();
                            leModele.modifierLocataire(leLocataire);
                            PopupInformation popup=new PopupInformation("Le locataire "+leLocataire.getNom()+" "+leLocataire.getPrenom()+" a bien été modifié.");
                        }
                        case "utilisateur" -> {
                            Utilisateur nouvelUtilisateur = (Utilisateur) laVue.getObjetModifie();
                            leModele.modifierUtilisateur(nouvelUtilisateur);
                            PopupInformation popup=new PopupInformation("L'utilisateur "+nouvelUtilisateur.getLogin()+" a bien été modifié.");
                        }
                        case "message" -> {
                            Message leMessage = (Message) laVue.getObjetModifie();
                            leModele.modifierMessage(leMessage);
                            PopupInformation popup=new PopupInformation("Le message a bien été modifié.");
                        }
                        case "campagne" -> {
                            Campagne laCampagne = (Campagne) laVue.getObjetModifie();
                            laCampagne.setUtilisateur(userConnecte);
                            leModele.modifierCampagne(laCampagne);
                            PopupInformation popup=new PopupInformation("La campagne a bien été modifiée.");
                        }
                        case "maison" -> {
                            Maison laMaison = (Maison) laVue.getObjetModifie();
                            leModele.modifierMaison(laMaison);
                            PopupInformation popup=new PopupInformation("La maison a bien été modifiée.");
                        }
                        case "appartement" -> {
                            Appartement unAppartement = (Appartement) laVue.getObjetModifie();
                            leModele.modifierAppartement(unAppartement);
                            PopupInformation popup=new PopupInformation("L'appartement a bien été modifiée.");
                        }
                    }
                    laVue.quitter();
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion(typeDonnee),new Modele_Gestion(typeDonnee+"s"), userConnecte, typeDonnee);               
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setSize(500,500);
                            controleur.getVue().setVisible(true);
                        }
                    });
                } catch (EmptyFieldException ex) {
                    ex.afficherErreur();
                }catch (PasDeLignesSelectionneesException ex){
                    ex.afficherErreur();
                }
            }
            case "RETOUR" -> {
                laVue.quitter();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        Controleur_Gestion controleur = new Controleur_Gestion(new Vue_Gestion(typeDonnee),new Modele_Gestion(typeDonnee+"s"), userConnecte, typeDonnee);                
                        controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        controleur.getVue().setSize(500,500);
                        controleur.getVue().setVisible(true);
                    }
                });
            }
        }
    }
}