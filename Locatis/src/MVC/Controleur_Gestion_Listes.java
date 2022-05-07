package MVC;

import Locatis.Campagne;
import Locatis.Utilisateur;
import interfaceGraphique.EmptyFieldException;
import interfaceGraphique.PopupInformation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Controleur_Gestion_Listes extends KeyAdapter implements ActionListener{
    
    private final Vue_Gestion_Listes laVue;
    private final Modele_Gestion_Listes leModele;
    private Utilisateur userConnecte;

    public Controleur_Gestion_Listes(Vue_Gestion_Listes uneVue, Modele_Gestion_Listes unModele, Utilisateur user) {
        this.userConnecte = user;
        this.laVue = uneVue;
        this.leModele = unModele;
        
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        uneVue.ajouterEcouteur("Ajouter", this);
        uneVue.ajouterEcouteur("Selectionner tout", this);
        uneVue.ajouterEcouteur("Tout deselectionner", this);
        uneVue.ajouterEcouteur("Retour", this);
        uneVue.ajouterEcouteur("Egal", this);
        uneVue.ajouterEcouteur("Superieur", this);
        uneVue.ajouterEcouteur("Inferieur", this);
        uneVue.ajouterEcouteur("Tri", this);
        uneVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
        uneVue.getNombreJTextField().getDocument().addDocumentListener(effectuerTri());
        uneVue.getTable().getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                unModele.cocher(e.getFirstRow());
            }
        });
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
        
        /*if(e.getSource().getClass().isInstance(new )){
            laVue.afficherPanneauBoutonsRadios();
            //leModele.getAll();
            leModele.trierPar(laVue.getCategorie());
            laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
        }else*/
        if(e.getSource().getClass().isInstance(new JComboBox())){
            laVue.afficherPanneauBoutonsRadios();
            //leModele.getAll();
            leModele.trierPar(laVue.getCategorie());
            laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
            laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    leModele.cocher(e.getFirstRow());
                }
            });
        }else
        
        if(e.getSource().getClass().isInstance(new JRadioButton())){
            if(laVue.getNombre()==-1)
                leModele.trierPar(laVue.getCategorie());
            else
                leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
            laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
            laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    leModele.cocher(e.getFirstRow());
                }
            });
        }else
        
        if(e.getSource().getClass().isInstance(new JTextField())){
            
            leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
            laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
            laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                public void tableChanged(TableModelEvent e) {
                    leModele.cocher(e.getFirstRow());
                }
            });
        }
        
        else{
        
        
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    try{
                        this.laVue.verifierChamps();
                        PopupInformation popup2;
                        if(leModele.getListeCasesCochees().isEmpty())
                            popup2=new PopupInformation("Sélectionner Locataire");
                        else
                            leModele.ajouter(laVue.getNom());
                        
                        PopupInformation popup=new PopupInformation("Liste de diffusion ajoutée.");
                        //laVue.reset();

                    }catch (EmptyFieldException ex) {
                        ex.afficherErreur();
                    }
                    /*SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            /*Controleur_AjoutModif controleur = new Controleur_AjoutModif(userConnecte);                
                            controleur.getVue().afficherVue();
                        }
                    });*/
                }
                case "SELECTIONNER TOUT" -> {
                    leModele.cocherTout();
                    laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
                    laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                        public void tableChanged(TableModelEvent e) {
                            leModele.cocher(e.getFirstRow());
                        }
                    });
                }
                case "TOUT DESELECTIONNER" -> {
                    leModele.decocherTout();
                    laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
                    laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                        public void tableChanged(TableModelEvent e) {
                            leModele.cocher(e.getFirstRow());
                        }
                    });
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
    }
    
    public DocumentListener effectuerTri(){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                
                if(laVue.getNombre()==-1)
                leModele.trierPar(laVue.getCategorie());
                else
                    leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
                laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
                laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                    public void tableChanged(TableModelEvent e) {
                        leModele.cocher(e.getFirstRow());
                    }
                });
                laVue.getNombreJTextField().requestFocus();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(laVue.getNombre()==-1)
                leModele.trierPar(laVue.getCategorie());
                else
                    leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
                laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
                laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
                    public void tableChanged(TableModelEvent e) {
                        leModele.cocher(e.getFirstRow());
                    }
                });
                laVue.getNombreJTextField().requestFocus();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
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
