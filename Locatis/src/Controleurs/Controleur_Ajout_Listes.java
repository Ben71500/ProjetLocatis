package Controleurs;

import Modeles.Modele_Ajout_Listes;
import Vues.Vue_Ajout_Listes;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Utilisateur;
import Exceptions.EmptyFieldException;
import Exceptions.PasDeCaseCocheeException;
import Popups.PopupInformation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

/**
 * Classe controleur de l'interface Ajout Listes implementant l'inteface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_Ajout_Listes extends KeyAdapter implements ActionListener{
    
    private final Vue_Ajout_Listes laVue;
    private final Modele_Ajout_Listes leModele;
    private Utilisateur userConnecte;

    /**
     * Constructeur de l'objet Controleur_Ajout_Listes pour la partie Ajout d'une liste
     * @param user : Utilisateur en cours d'utilisation
     */
    public Controleur_Ajout_Listes(Utilisateur user) {
        this.userConnecte = user;
        this.laVue = new Vue_Ajout_Listes("locataire");
        this.leModele = new Modele_Ajout_Listes("locataire", userConnecte);
        
        laVue.ajouterEcouteur("Ajouter", this);
        laVue.ajouterEcouteur("Selectionner tout", this);
        laVue.ajouterEcouteur("Tout deselectionner", this);
        laVue.ajouterEcouteur("Retour", this);
        laVue.ajouterEcouteur("Locataires", this);
        laVue.ajouterEcouteur("Utilisateurs", this);
        laVue.ajouterEcouteur("Egal", this);
        laVue.ajouterEcouteur("Superieur", this);
        laVue.ajouterEcouteur("Inferieur", this);
        laVue.ajouterEcouteur("Tri", this);
        laVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
        laVue.getNombreJSpinner().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                trierTableau();
            }
        });
        laVue.getDate().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                trierTableau();
            }   
        });
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                leModele.cocher(e.getFirstRow());
            }
        });
    }
    
    /**
     * Constructeur de l'objet Controleur_Ajout_Listes pour la partie modification
     * @param user : Utilsateur en cours d'utilisation
     * @param listeDiffusion : liste de diffusion a modifier
     */
    public Controleur_Ajout_Listes(Utilisateur user, ListeDeDiffusion listeDiffusion) {
        this.userConnecte = user;
        this.laVue = new Vue_Ajout_Listes(listeDiffusion.getTypeListe(), listeDiffusion);
        this.leModele = new Modele_Ajout_Listes(listeDiffusion.getTypeListe(), userConnecte, listeDiffusion.getListeId());
        laVue.ajouterEcouteur("Ajouter", this);
        laVue.ajouterEcouteur("Modifier", this);
        laVue.ajouterEcouteur("Selectionner tout", this);
        laVue.ajouterEcouteur("Tout deselectionner", this);
        laVue.ajouterEcouteur("Retour", this);
        laVue.ajouterEcouteur("Locataires", this);
        laVue.ajouterEcouteur("Utilisateurs", this);
        laVue.ajouterEcouteur("Egal", this);
        laVue.ajouterEcouteur("Superieur", this);
        laVue.ajouterEcouteur("Inferieur", this);
        laVue.ajouterEcouteur("Tri", this);
        laVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
        laVue.getNombreJSpinner().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                trierTableau();
            }
        });
        laVue.getDate().addPropertyChangeListener("date", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                trierTableau();
            }   
        });
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                leModele.cocher(e.getFirstRow());
            }
        });
    }
    
    /**
     *
     * @return Vue_Ajout_Listes
     */
    public Vue_Ajout_Listes getVue() {
        return laVue;
    }

    /**
     * Méthode de la classe abstraite KeyAdapter
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e){
        
        if(e.getSource().getClass().isInstance(new JRadioButton())){
            
            if(e.getSource() == this.laVue.getButtonRadioLocataires()){
                leModele.setDonnees("locataire");
                laVue.setDonnees("locataire");
                leModele.decocherTout();
                laVue.remplirComboBox();
                leModele.choisirModele();
                actualiser();
            }
            else if(e.getSource() == this.laVue.getButtonRadioUtilisateurs()){
                    leModele.setDonnees("utilisateur");
                    laVue.setDonnees("utilisateur");
                    leModele.decocherTout();
                    laVue.remplirComboBox();
                    leModele.choisirModele();
                    actualiser();
                    
                }else{
                    trierTableau();
                }
        }else
        if(e.getSource() == this.laVue.getTri()){
            trierTableau();
        }else{
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
                }
                case "MODIFIER" -> {
                    try{
                        this.laVue.verifierChamps();
                        if(leModele.getListeCasesCochees().isEmpty())
                            throw new PasDeCaseCocheeException();
                        
                        leModele.modifier(laVue.getObjetModifie());
                        PopupInformation popup=new PopupInformation("Liste de diffusion modifiée.");
                        laVue.quitter();
                        SwingUtilities.invokeLater(new Runnable(){
                            public void run(){
                                Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "liste");               
                                controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                controleur.getVue().setSize(500,500);
                                controleur.getVue().setVisible(true);
                            }
                        });
                    }catch (EmptyFieldException ex) {
                        ex.afficherErreur();
                    }catch (PasDeCaseCocheeException ex) {
                        ex.afficherErreur();
                    }
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
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            Controleur_Gestion controleur = new Controleur_Gestion(userConnecte, "liste");                
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setSize(500,500);
                            controleur.getVue().setVisible(true);
                        }
                    });
                }
            }
        }
    }
    
    /**
     * Méthode qui effectue une recherche dans le tableau pour trouver les objets 
     * contenant une similitude avec la chaine saisie dans le label de la vue du controleur
     * @return DocumentListener
     */
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
    
    /**
     * Méthode qui actualise l'interface des listes de diffusion
     */
    public void actualiser(){
        
        laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
        
        laVue.getTable().getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                leModele.cocher(e.getFirstRow());
            }
        });
        laVue.getRecherche().setText("");
    }
    
    /**
     * Méthode qui trie le tableau contenant les locataires avec diffèrents critères ( Age / Date de naissance)
     */
    public void trierTableau(){
        laVue.afficherPanneauBoutonsRadios();
        switch (laVue.getCategorie()) {
            case "Age" -> leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadioSigne(), laVue.getNombre());
            case "DateDeNaissance" -> leModele.getTri(laVue.getCategorie(), laVue.getBoutonRadioSigne(), laVue.getDateSaisie());
            default -> leModele.trierPar(laVue.getCategorie());
        }
        actualiser();
    }
}