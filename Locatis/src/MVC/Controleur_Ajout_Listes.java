package MVC;

import Locatis.Utilisateur;
import interfaceGraphique.EmptyFieldException;
import interfaceGraphique.PasDeCaseCocheeException;
import interfaceGraphique.PopupInformation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class Controleur_Ajout_Listes extends KeyAdapter implements ActionListener{
    
    private final Vue_Ajout_Listes laVue;
    private final Modele_Ajout_Listes leModele;
    private Utilisateur userConnecte;

    public Controleur_Ajout_Listes(Vue_Ajout_Listes uneVue, Modele_Ajout_Listes unModele, Utilisateur user) {
        this.userConnecte = user;
        this.laVue = uneVue;
        this.leModele = unModele;
        
        uneVue.ajouterEcouteur("Ajouter", this);
        uneVue.ajouterEcouteur("Selectionner tout", this);
        uneVue.ajouterEcouteur("Tout deselectionner", this);
        uneVue.ajouterEcouteur("Retour", this);
        uneVue.ajouterEcouteur("Egal", this);
        uneVue.ajouterEcouteur("Superieur", this);
        uneVue.ajouterEcouteur("Inferieur", this);
        uneVue.ajouterEcouteur("Tri", this);
        uneVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
        uneVue.getNombreJSpinner().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
                actualiser();
            }
        });
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        uneVue.getTable().getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                unModele.cocher(e.getFirstRow());
            }
        });
    }
    public Vue_Ajout_Listes getVue() {
        return laVue;
    }

    public Modele_Ajout_Listes getModele() {
        return leModele;
    }
    
    public Utilisateur getUser(){
        return this.userConnecte;
    }

    //Méthode de la classe abstraite KeyAdapter
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource().getClass().isInstance(new JComboBox())){
            laVue.afficherPanneauBoutonsRadios();
            //leModele.getAll();
            leModele.trierPar(laVue.getCategorie());
            actualiser();
        }else
        
        if(e.getSource().getClass().isInstance(new JRadioButton())){
            if(laVue.getCategorie().equals("Age"))
                leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
            else
                leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getDate());
            actualiser();
        }else
        
        if(e.getSource().getClass().isInstance(new JTextField())){
            leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadio(), laVue.getNombre());
            actualiser();
        }
        
        else{
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    try{
                        this.laVue.verifierChamps();
                        if(leModele.getListeCasesCochees().isEmpty())
                            throw new PasDeCaseCocheeException();
                        
                        leModele.ajouter(laVue.getNom());
                        PopupInformation popup=new PopupInformation("Liste de diffusion ajoutée.");
                        
                        laVue.reset();
                        leModele.decocherTout();
                        actualiser();
                    }catch (EmptyFieldException ex) {
                        ex.afficherErreur();
                    }catch (PasDeCaseCocheeException ex) {
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
                    actualiser();
                }
                case "TOUT DESELECTIONNER" -> {
                    leModele.decocherTout();
                    actualiser();
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
    
    public void actualiser(){
        laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
        laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                leModele.cocher(e.getFirstRow());
            }
        });
    }   
}