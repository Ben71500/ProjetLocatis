/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceGraphique;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Maison_DAO;
import Locatis.Appartement;
import Locatis.Maison;
import Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author benja
 */
public class GestionBatiment extends JFrame implements ActionListener{
    
    private String[] enteteAppartement = {"ID","Adresse", "Numéro étage", "Numéro appartement"};
    private String[] enteteMaison = {"ID","Adresse"};
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    private JPanel panneau_recherches = new JPanel();
    
    private JLabel titre = new JLabel("Les Logements");
    private JLabel rechercher_label = new JLabel("Rechercher : ");
    
    private JTextField recherche = new JTextField();
    
    private ArrayList<Appartement> listeAppartement = new ArrayList();
    private ArrayList<Maison> listeMaison = new ArrayList();
    private DefaultTableModel tableau;
    private JTable table;
    private TableRowSorter<TableModel> sort;
    
    private JButton ajouter = new JButton("Ajouter un nouveau logement");
    private JButton modifier = new JButton("Modifier");
    private JButton supprimer = new JButton("Supprimer");
    private JButton retour = new JButton("Retour");
    
    private JRadioButton buttonRadioAppart = new JRadioButton("Appartement", true);
    private JRadioButton buttonRadioMaison = new JRadioButton("Maison");
    
    private ButtonGroup group = new ButtonGroup();
    
    private Utilisateur userConnecte;
    
    public GestionBatiment(Utilisateur user){
        
        super("Gestion des batiments");
        userConnecte=user;
        panneau_boutons.setLayout(new GridLayout(1,4));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(modifier);
        panneau_boutons.add(supprimer);
        panneau_boutons.add(retour);
        panneau_boutons.add(buttonRadioAppart);
        panneau_boutons.add(buttonRadioMaison);
        
        this.ajouter.addActionListener(this);
        this.modifier.addActionListener(this);
        this.supprimer.addActionListener(this);
        this.retour.addActionListener(this);
        this.buttonRadioAppart.addActionListener(this);
        this.buttonRadioMaison.addActionListener(this);
        afficherAppartement();
    }
    
    public DocumentListener effectuerRecherche(){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String laRecherche = recherche.getText();
                if (laRecherche.trim().length() == 0){    
                    sort.setRowFilter(null);
                }else{
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + laRecherche));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String laRecherche = recherche.getText();
                if (laRecherche.trim().length() == 0)
                    sort.setRowFilter(null);
                else
                    sort.setRowFilter(RowFilter.regexFilter("(?i)" + laRecherche));
            }
            @Override
            public void changedUpdate(DocumentEvent e) {}
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==ajouter){
            if(buttonRadioAppart.isSelected()){ 
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        ModifierAppartement fenetre=new ModifierAppartement(userConnecte);
                        fenetre.setBounds(200, 200, 350, 300);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            }
            if(buttonRadioMaison.isSelected()){
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        ModifierMaison fenetre=new ModifierMaison(userConnecte);
                        fenetre.setBounds(100, 100, 350, 300);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            }
        }
        if(e.getSource()==modifier){
            try {
                verifierSelection();
            } catch (PasDeLignesSelectionneesException ex) {}
            if(buttonRadioAppart.isSelected()){
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        ModifierAppartement fenetre=new ModifierAppartement(userConnecte, listeAppartement.get(table.getSelectedRow()));
                        fenetre.setBounds(100, 100, 350, 300);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            }
            if(buttonRadioMaison.isSelected()){
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        ModifierMaison fenetre=new ModifierMaison(userConnecte, listeMaison.get(table.getSelectedRow()));
                        fenetre.setBounds(100, 100, 350, 300);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            }
        }
        if(e.getSource()==supprimer){
            if(buttonRadioAppart.isSelected()){
                System.out.println("supp app");
                PopupSupprimer popup= new PopupSupprimer();
                if(popup.getConfirmation()){
                    Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                    Appartement_DAO appartementDao=new Appartement_DAO(connBdd);
                    appartementDao.delete(listeAppartement.get(table.getSelectedRow()));
                    tableau.removeRow(table.getSelectedRow());
                }
            }
            if(buttonRadioMaison.isSelected()){
                PopupSupprimer popup= new PopupSupprimer();
                if(popup.getConfirmation()){
                    Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                    Maison_DAO maisonDao=new Maison_DAO(connBdd);
                    maisonDao.delete(listeMaison.get(table.getSelectedRow()));
                    tableau.removeRow(table.getSelectedRow());
                }
            }
        }
        if(e.getSource()==retour){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    MenuPrincipal fenetre=new MenuPrincipal(userConnecte);
                    fenetre.setBounds(100, 100, 350, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource() == buttonRadioAppart){
            supprimerAffichage();
            afficherAppartement();
        }
        if(e.getSource() == buttonRadioMaison){
            supprimerAffichage();
            affichageMaison();
        }
    }
    
    /*public static <T extends DAO> ArrayList<T> getListe(ArrayList<T> liste){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        T objDao = new T(connBdd);
        liste=(ArrayList<T>)T.getAll();
        return liste;
    }*/
    
    public static ArrayList<Maison> getListeMaison(){
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        Maison_DAO lesMaisons = new Maison_DAO(connBdd);
        ArrayList<Maison> listeMaison = (ArrayList<Maison>)lesMaisons.getAll(); 
        return listeMaison;
    }
    
    public ArrayList<Appartement> getListeAppartement(){
        //System.out.println("ok app");
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        Appartement_DAO lesAppartements = new Appartement_DAO(connBdd);
        this.listeAppartement = (ArrayList<Appartement>)lesAppartements.getAll(); 
        return listeAppartement;
    }
    
    public String[][] devenirTableauMaison(ArrayList<Maison> liste){
        String[][] unTableau = new String[liste.size()][5];
        for(int i=0; i<liste.size();i++){
            unTableau [i][0]=liste.get(i).getID()+"";
            unTableau [i][1]=liste.get(i).getNumeroRue();
            unTableau [i][2]=liste.get(i).getNomRue();
            unTableau [i][3]=liste.get(i).getCodePostal();
            unTableau [i][4]=liste.get(i).getVille();
        }
        return unTableau;
    }
    
    public String[][] devenirTableauAppartement(ArrayList<Appartement> liste){
        String[][] unTableau = new String[liste.size()][7];
        for(int i=0; i<liste.size();i++){
            unTableau [i][0]=liste.get(i).getID()+"";
            unTableau [i][1]=liste.get(i).getNumeroRue();
            unTableau [i][2]=liste.get(i).getNomRue();
            unTableau [i][3]=liste.get(i).getCodePostal();
            unTableau [i][4]=liste.get(i).getVille();
            unTableau [i][5]=""+liste.get(i).getEtage();
            unTableau [i][6]=liste.get(i).getApart()+"";
        }
        return unTableau;
    }
    
    public void supprimerAffichage(){
        panneau.removeAll();
        panneau_info.removeAll();
        panneau_recherches.removeAll();
        haut.removeAll();
        centre.removeAll();
    }
    
    public void afficherAppartement(){
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        
        //Titre
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        centre.add(new JScrollPane(this.panneau_info), BorderLayout.CENTER);
        centre.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_recherches.setLayout(new GridLayout(1,2));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        group.add(buttonRadioAppart);
        group.add(buttonRadioMaison);
        
        this.recherche.getDocument().addDocumentListener(effectuerRecherche());
        
        listeAppartement = getListeAppartement();
        String[][] unTableau=devenirTableauAppartement(listeAppartement);
        //On ajoute un entête au tableau précedemment créé
        tableau= new DefaultTableModel(unTableau, enteteAppartement);
        sort = new TableRowSorter<>(tableau);
        //Ajout du tableau des locataires
        table = new JTable(tableau);
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        panneau_info.add(new JScrollPane(table), BorderLayout.CENTER);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public void affichageMaison(){
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        
        //Titre
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        centre.add(new JScrollPane(this.panneau_info), BorderLayout.CENTER);
        centre.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_recherches.setLayout(new GridLayout(1,2));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        group.add(buttonRadioAppart);
        group.add(buttonRadioMaison);
        
        this.recherche.getDocument().addDocumentListener(effectuerRecherche());
        
        listeMaison = getListeMaison();
        String[][] unTableau=devenirTableauMaison(listeMaison);
        tableau= new DefaultTableModel(unTableau, enteteMaison);
        sort = new TableRowSorter<>(tableau);
        //Ajout du tableau des locataires
        table = new JTable(tableau);
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        panneau_info.add(new JScrollPane(table), BorderLayout.CENTER);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    public void verifierSelection() throws PasDeLignesSelectionneesException{
        if(this.table.getSelectedRowCount()==0){
            throw new PasDeLignesSelectionneesException("un logement");
        }
    }
}
