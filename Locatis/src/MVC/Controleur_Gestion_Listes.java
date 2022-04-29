package MVC;

import Locatis.Utilisateur;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.JButton;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Controleur_Gestion_Listes extends KeyAdapter implements ActionListener{
    
    private final Vue_Gestion_Listes laVue;
    private final Modele_Gestion_Listes leModele;
    private Utilisateur userConnecte;

    public Controleur_Gestion_Listes(Vue_Gestion_Listes uneVue, Modele_Gestion_Listes unModele, Utilisateur user) {
        this.userConnecte = user;
        this.laVue = uneVue;
        this.leModele = unModele;
        
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        uneVue.ajouterEcouteurBouton("Ajouter", this);
        uneVue.ajouterEcouteurBouton("Modifier", this);
        uneVue.ajouterEcouteurBouton("Supprimer", this);
        uneVue.ajouterEcouteurBouton("Retour", this);
        uneVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
    }

    public Vue_Gestion_Listes getVue() {
        return laVue;
    }

    public Modele_Gestion_Listes getModele() {
        return leModele;
    }
    
    public Utilisateur getUser(){
        return this.userConnecte;
    }

    //Méthode de la classe abstraite KeyAdapter
    @Override
    public void actionPerformed(ActionEvent e){
        
        
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    laVue.quitter();

                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            /*Controleur_AjoutModif controleur = new Controleur_AjoutModif(userConnecte);                
                            controleur.getVue().afficherVue();*/
                        }
                    });
                }
                case "MODIFIER" -> {
                    /*try{
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
                    }*/
                }
                case "SUPPRIMER" -> {
                    /*try{
                        laVue.verifierSelection();
                        PopupSupprimer popup= new PopupSupprimer();
                        if(popup.getConfirmation()){       
                            leModele.supprimer(laVue.getLigne());
                            laVue.getTableau().removeRow(laVue.getLigne());
                        }
                    }catch(PasDeLignesSelectionneesException ex){
                        ex.afficherErreur();
                    }*/
                }
                case "RETOUR" -> {
                    laVue.quitter();
                    /*SwingUtilities.invokeLater(new Runnable(){
                        public void run(){ 
                            Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(userConnecte),new Modele_Gestion(typeDonnee), userConnecte);
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setBounds(100, 100, 350, 300);
                            controleur.getVue().setVisible(true);
                        }
                    });*/
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
