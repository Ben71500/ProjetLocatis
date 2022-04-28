package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Controleur_Gestion extends KeyAdapter implements ActionListener {

    private final Vue_Gestion laVue;
    private final Modele_Gestion leModele;
    private Utilisateur userConnecte;
    private String typeDonnee;

    public Controleur_Gestion(Vue_Gestion uneVue, Modele_Gestion unModele, Utilisateur user, String donnee) {
        this.userConnecte = user;
        this.typeDonnee = donnee;
        this.laVue = uneVue;
        this.leModele = unModele;
        this.leModele.initialiser();
        //laVue.setTable(leModele.getTable());
        
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        uneVue.ajouterEcouteurBouton("Ajouter", this);
        uneVue.ajouterEcouteurBouton("Modifier", this);
        uneVue.ajouterEcouteurBouton("Supprimer", this);
        uneVue.ajouterEcouteurBouton("Retour", this);
        if(donnee.equals("appartement")){
            uneVue.ajouterEcouteurBouton("Appartement", this);
            uneVue.ajouterEcouteurBouton("Maison", this);
        }
        if(donnee.equals("maison")){
            uneVue.ajouterEcouteurBouton("Appartement", this);
            uneVue.ajouterEcouteurBouton("Maison", this);
        }
        uneVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
    }

    public Vue_Gestion getVue() {
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
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.laVue.getButtonRadioAppart() || e.getSource() == this.laVue.getButtonRadioMaison()){
            if(this.laVue.getButtonRadioAppart().isSelected()){
                this.typeDonnee = "appartement";
                leModele.setDonnees(this.typeDonnee);
                leModele.initialiser();
                laVue.setDonnees("appartement");
                laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
                
            }
            else if(this.laVue.getButtonRadioMaison().isSelected()){
                    this.typeDonnee = "maison";
                    leModele.setDonnees(this.typeDonnee);
                    leModele.initialiser();
                    laVue.setDonnees("maison");
                    laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
            }
        }
        else
        {
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    laVue.quitter();

                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            Controleur_AjoutModif controleur = new Controleur_AjoutModif(userConnecte, typeDonnee);                
                            controleur.getVue().afficherVue();
                        }
                    });
                }
                case "MODIFIER" -> {
                    try{
                        laVue.verifierSelection();
                        laVue.quitter();
                        SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run(){
                                Controleur_AjoutModif controleur = new Controleur_AjoutModif(userConnecte, typeDonnee, leModele.getSelection(laVue.getLigne()));                
                                controleur.getVue().afficherVue();
                            }
                        });
                    }catch(PasDeLignesSelectionneesException ex){
                        ex.afficherErreur();
                    }
                }
                case "SUPPRIMER" -> {
                    try{
                        laVue.verifierSelection();
                        PopupSupprimer popup= new PopupSupprimer();
                        if(popup.getConfirmation()){       
                            leModele.supprimer(laVue.getLigne());
                            laVue.getTableau().removeRow(laVue.getLigne());
                        }
                    }catch(PasDeLignesSelectionneesException ex){
                        ex.afficherErreur();
                    }
                }
                case "RETOUR" -> {
                    laVue.quitter();
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){ 
                            Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(userConnecte),new Modele_Gestion(typeDonnee), userConnecte);
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setBounds(100, 100, 350, 300);
                            controleur.getVue().setVisible(true);
                        }
                    });
                }
            }
        }
    }
    
    public DocumentListener effectuerRecherche(){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String laRecherche = laVue.getRecherche().getText();
                if (laRecherche.trim().length() == 0){    
                    laVue.getSort().setRowFilter(null);
                }else{
                    laVue.getSort().setRowFilter(RowFilter.regexFilter("(?i)" + laRecherche));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String laRecherche = laVue.getRecherche().getText();
                if (laRecherche.trim().length() == 0)
                    laVue.getSort().setRowFilter(null);
                else
                    laVue.getSort().setRowFilter(RowFilter.regexFilter("(?i)" + laRecherche));
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
    }
}