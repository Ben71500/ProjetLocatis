/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceGraphique;

import DAO.Appartement_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Habiter_DAO;
import DAO.Locataire_DAO;
import DAO.Maison_DAO;
import Locatis.Appartement;
import Locatis.Batiment;
import Locatis.Habiter;
import Locatis.Locataire;
import Locatis.Maison;
import Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.BorderFactory;
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
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    private JPanel panneau_recherches = new JPanel();
    
    private JLabel titre = new JLabel("Les utilisateurs");
    private JLabel rechercher_label = new JLabel("Rechercher : ");
    
    private JTextField recherche = new JTextField();
    
    private ArrayList<Locataire> liste = new ArrayList();
    private DefaultTableModel tableau;
    private JTable table;
    private TableRowSorter<TableModel> sort;
    
    private JButton ajouter = new JButton("Ajouter un nouveau batiment");
    private JButton modifier = new JButton("Modifier");
    private JButton supprimer = new JButton("Supprimer");
    private JButton retour = new JButton("Retour");
    
    private JRadioButton buttonRadio = new JRadioButton();
    
    private Utilisateur userConnecte;
    
    public GestionBatiment(Utilisateur user){
        
        super("Gestion des batiments");
        userConnecte=user;
        
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
        this.recherche.getDocument().addDocumentListener(effectuerRecherche());
        
        //Récupération des batiments dans une ArrayList
        Connection connBdd= ConnectionBDD.getInstance(new Connexion());
        /*Appartement_DAO lesAppartements= new Appartement_DAO(connBdd);
        Maison_DAO lesMaisons= new Maison_DAO(connBdd);
        liste=(ArrayList<Batiment>)lesAppartements.getAll();*/
        //On convertit cette ArrayList en tableau à deux dimensions
        String[][] unTableau=devenirTableau(liste);
        //On ajoute un entête au tableau précedemment créé
        String[] entetes = {"Type","Adresse", "Numéro étage", "Numéro appartement"};
        tableau= new DefaultTableModel(unTableau, entetes);
        sort = new TableRowSorter<>(tableau);
        //Ajout du tableau des locataires
        table = new JTable(tableau);
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        panneau_info.add(new JScrollPane(table), BorderLayout.CENTER);
        
        //Ajout des différents boutons
        panneau_boutons.setLayout(new GridLayout(1,4));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(modifier);
        panneau_boutons.add(supprimer);
        panneau_boutons.add(retour);
        panneau_boutons.add(buttonRadio);
        
        this.ajouter.addActionListener(this);
        this.modifier.addActionListener(this);
        this.supprimer.addActionListener(this);
        this.retour.addActionListener(this);
        this.buttonRadio.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
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
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    ModifierLocataire fenetre=new ModifierLocataire(userConnecte);
                    fenetre.setBounds(100, 100, 350, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==modifier){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    ModifierLocataire fenetre=new ModifierLocataire(userConnecte, liste.get(table.getSelectedRow()));
                    fenetre.setBounds(100, 100, 350, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
        if(e.getSource()==supprimer){
            PopupSupprimer popup= new PopupSupprimer();
            if(popup.getConfirmation()){
                Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                Locataire_DAO locDao=new Locataire_DAO(connBdd);
                locDao.delete(liste.get(table.getSelectedRow()));
                tableau.removeRow(table.getSelectedRow());
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
        if(e.getSource()==buttonRadio.get)
    }
    
    public String[][] devenirTableau(ArrayList<Locataire> liste){
        String[][] unTableau = new String[liste.size()][7];
        for(int i=0; i<liste.size();i++){
            unTableau [i][0]=liste.get(i).getId()+"";
            unTableau [i][1]=liste.get(i).getNom();
            unTableau [i][2]=liste.get(i).getPrenom();
            unTableau [i][3]=liste.get(i).getAge()+"";
            unTableau [i][4]=liste.get(i).getAnciennete().getDateSQL();
            unTableau [i][5]=liste.get(i).getMail();
            unTableau [i][6]=liste.get(i).getTelephone();
        }
        return unTableau;
    }
}
