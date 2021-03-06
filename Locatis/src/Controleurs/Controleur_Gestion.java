package Controleurs;

import Popups.PopupSupprimer;
import Exceptions.PasDeLignesSelectionneesException;
import Modeles.Modele_Gestion;
import Modeles.Modele_dialog_locataire;
import Vues.Vue_Gestion;
import Vues.Vue_Menu;
import Vues.Vue_dialog_locataire;
import Objets_Locatis.Appartement;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Maison;
import Objets_Locatis.Utilisateur;
import Objets_Locatis.Locataire;
import Objets_Locatis.MyDate;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.*;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 * Classe de l'interface gestion des objets dérivant de KeyAdapter et implémentant ActionListener et MouseListener
 * @author Benjamin Mathilde
 */
public class Controleur_Gestion extends KeyAdapter implements ActionListener, MouseListener{

    private final Vue_Gestion laVue;
    private final Modele_Gestion leModele;
    private Utilisateur userConnecte;
    private String typeDonnee;

    /**
     * Constructeur de l'objet Controleur_Gestion
     * @param user : Utilisateur en cours d'utilisation
     * @param donnee : les données
     */
    public Controleur_Gestion(Utilisateur user, String donnee) {
        this.userConnecte = user;
        this.typeDonnee = donnee;
        this.laVue = new Vue_Gestion(typeDonnee, userConnecte);
        this.leModele = new Modele_Gestion(typeDonnee, userConnecte);
        this.leModele.initialiser();
        
        laVue.definirTableau(leModele.getTableau(),leModele.getEntetes());
        laVue.ajouterEcouteur("Ajouter", this);
        laVue.ajouterEcouteur("Modifier", this);
        laVue.ajouterEcouteur("Supprimer", this);
        laVue.ajouterEcouteur("Retour", this);
        if(donnee.equals("locataire")){
            laVue.ajouterEcouteur("Insere", this);
            this.laVue.getTable().addMouseListener(this);
        }
        if(donnee.equals("appartement")){
            laVue.ajouterEcouteur("Appartement", this);
            laVue.ajouterEcouteur("Maison", this);
            laVue.ajouterEcouteur("Insere", this);
        }
        if(donnee.equals("maison")){
            laVue.ajouterEcouteur("Appartement", this);
            laVue.ajouterEcouteur("Maison", this);
            laVue.ajouterEcouteur("Insere", this);
        }
        laVue.getRecherche().getDocument().addDocumentListener(effectuerRecherche());
    }

    /**
     *
     * @return Vue_Gestion
     */
    public Vue_Gestion getVue() {
        return laVue;
    }

    /**
     * Méthode de la classe abstraite ActionListener
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.laVue.getButtonRadioAppart() || e.getSource() == this.laVue.getButtonRadioMaison()){
            if(this.laVue.getButtonRadioAppart().isSelected()){
                this.typeDonnee = "appartement";
                leModele.setDonnees(this.typeDonnee);
                leModele.initialiser();
                laVue.setDonnees("appartement");
                laVue.setTitre("Les appartements");
                laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
            }
            else if(this.laVue.getButtonRadioMaison().isSelected()){
                    this.typeDonnee = "maison";
                    leModele.setDonnees(this.typeDonnee);
                    laVue.setDonnees("maison");
                    leModele.initialiser();
                    laVue.setTitre("Les maisons");
                    laVue.changerTableau(leModele.getTableau(),leModele.getEntetes());
            }
        }
        else{
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    laVue.quitter();

                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){
                            if(typeDonnee.equals("liste")){
                                Controleur_Ajout_Listes controleur= new Controleur_Ajout_Listes(userConnecte);
                                controleur.getVue().afficherVue();
                            }else{
                                Controleur_AjoutModif controleur = new Controleur_AjoutModif(userConnecte, typeDonnee);                
                                controleur.getVue().afficherVue();
                            }
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
                                if(typeDonnee.equals("liste")){
                                    Controleur_Ajout_Listes controleur= new Controleur_Ajout_Listes(userConnecte, (ListeDeDiffusion) leModele.getSelection(laVue.getLigne()));
                                    controleur.getVue().afficherVue();
                                }else{
                                    Controleur_AjoutModif controleur = new Controleur_AjoutModif(userConnecte, typeDonnee, leModele.getSelection(laVue.getLigne()));                
                                    controleur.getVue().afficherVue();
                                }
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
                            Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(userConnecte), userConnecte);
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
                            int height = 600;
                            int width = 500;
                            controleur.getVue().setBounds((tailleEcran.width-width)/2, (tailleEcran.height-height)/2, width, height);
                            controleur.getVue().setVisible(true);
                        }
                    });
                }
                case "INSERE" -> {
                    FileNameExtensionFilter filtre = new FileNameExtensionFilter("CSV files (*csv)", "csv");
                    JFileChooser choose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                    choose.setFileFilter(filtre);
                    int res = choose.showOpenDialog(null);
                    File file = new File("C:/");
                    if (res == JFileChooser.APPROVE_OPTION) {
                      file = choose.getSelectedFile();
                      System.out.println(file.getAbsolutePath());
                    }else{
                       break;
                    }
                    try{
                        Scanner fichierCSV = new Scanner(file);
                        if(typeDonnee.equals("locataire")){
                            ArrayList<Locataire> listeLoca = new ArrayList<>();
                            while (fichierCSV.hasNextLine()){
                                String ligne = fichierCSV.nextLine();
                                String[] ligneSeparer = ligne.split(",");
                                Locataire loca = new Locataire(0, ligneSeparer[0], ligneSeparer[1], new MyDate(ligneSeparer[2]), ligneSeparer[3], ligneSeparer[4]);
                                listeLoca.add(loca);
                            }
                            leModele.insererViaCSV(listeLoca);
                        }
                        else if(typeDonnee.equals("maison")){
                            ArrayList<Maison> listeMaison = new ArrayList<>();
                            while (fichierCSV.hasNextLine()){
                                String ligne = fichierCSV.nextLine();
                                String[] ligneSeparer = ligne.split(",");
                                Maison maison = new Maison(ligneSeparer[0], ligneSeparer[1], ligneSeparer[2], ligneSeparer[3]);
                                listeMaison.add(maison);
                            }
                            leModele.insererViaCSV(listeMaison);
                        }
                        else{
                            ArrayList<Appartement> listeAppartement = new ArrayList<>();
                            System.out.println(fichierCSV.hasNextLine());
                            while (fichierCSV.hasNextLine()){
                                String ligne = fichierCSV.nextLine();
                                String[] ligneSeparer = ligne.split(",");
                                Appartement appartement = new Appartement(ligneSeparer[0], ligneSeparer[1], ligneSeparer[2], ligneSeparer[3], Integer.parseInt(ligneSeparer[4]), Integer.parseInt(ligneSeparer[5]));
                                listeAppartement.add(appartement);
                            }
                            leModele.insererViaCSV(listeAppartement);
                        }
                    }catch(Exception ex){
                        System.out.println("erreur");
                        System.out.println(ex.getMessage());
                    }
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Méthode de la classe abstraite MouseListener
     * @param e 
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Locataire loca = (Locataire) leModele.getSelection(laVue.getLigne());
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    Controleur_dialog_locataire controleur = new Controleur_dialog_locataire(loca, userConnecte, new Vue_dialog_locataire(userConnecte), new Modele_dialog_locataire());
                    controleur.getLaVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    //Centrage de la fenetre sur l'écran
                    Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
                    int height = 400;
                    int width = 800;
                    controleur.getLaVue().setBounds((tailleEcran.width-width)/2, (tailleEcran.height-height)/2, width, height);
                    controleur.getLaVue().setVisible(true);
                }
            });
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}