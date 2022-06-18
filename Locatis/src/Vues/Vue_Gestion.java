package Vues;

import Exceptions.PasDeLignesSelectionneesException;
import Objets_Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.table.*;

public class Vue_Gestion extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    //private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    private JPanel panneau_recherches = new JPanel();
    
    private JLabel titre;
    private JLabel rechercher_label = new JLabel("Rechercher : ");
    
    private JTextField recherche = new JTextField();
    
    private DefaultTableModel tableau;
    private JTable table;
    private TableRowSorter<TableModel> sort;
    
    private JRadioButton buttonRadioAppart = new JRadioButton("Appartement", true);
    private JRadioButton buttonRadioMaison = new JRadioButton("Maison");
    
    private ButtonGroup group = new ButtonGroup();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton supprimer = new JButton("Supprimer");
    private JButton retour = new JButton("Retour");
    private JButton inserer = new JButton("Insere");
    
    private String donnees;
    private Utilisateur utilisateur;

    public Vue_Gestion(String lesDonnees, Utilisateur user) {
        super("Gestion des "+lesDonnees+"s");
        this.donnees = lesDonnees;
        this.utilisateur = user;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        
        //Titre
        titre = new JLabel("Les "+donnees+"s");
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        //centre.add(new JScrollPane(this.panneau_info), BorderLayout.CENTER);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_recherches.setLayout(new GridLayout(1,2));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        
        //Ajout des différents boutons
        if(this.utilisateur.getCat().equals("uti2")){
            panneau_boutons.setLayout(new GridLayout(1,2));
            panneau_boutons.add(modifier);
            panneau_boutons.add(retour);
        }
        else{
            panneau_boutons.setLayout(new GridLayout(1,4));
            panneau_boutons.add(ajouter);
            panneau_boutons.add(modifier);
            panneau_boutons.add(supprimer);
            panneau_boutons.add(retour);
        }
        
        if(lesDonnees.equals("locataire")){
            panneau_boutons.add(inserer);
        }
        
        if (lesDonnees.equals("maison")){
            buttonRadioAppart.setSelected(false);
            buttonRadioMaison.setSelected(true);
        }
        
        if(lesDonnees.equals("appartement") || lesDonnees.equals("maison")){
            panneau_boutons.add(buttonRadioAppart);
            panneau_boutons.add(buttonRadioMaison);
            group.add(buttonRadioAppart);
            group.add(buttonRadioMaison);
            panneau_boutons.add(inserer);
        }
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    
    public String getDonnees() {
        return donnees;
    }
    
    public DefaultTableModel getTableau() {
        return tableau;
    }
    
    public int getLigne(){
        return this.table.getSelectedRow();
    }
    
    public JRadioButton getButtonRadioAppart(){
        return buttonRadioAppart;
    }
    
    public JRadioButton getButtonRadioMaison(){
        return buttonRadioMaison;
    }
    
    public JTextField getRecherche(){
        return this.recherche;
    }
    
    public TableRowSorter<TableModel> getSort(){
        return this.sort;
    }

    public JTable getTable() {
        return table;
    }

    public void definirTableau(String[][] donnees, String[] entetes) {
        
        this.tableau = new DefaultTableModel(donnees, entetes)
        {
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        sort = new TableRowSorter<>(this.tableau);
        //Ajout du tableau des locataires
        this.table = new JTable(tableau);
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        //Dimension preferredSize = new Dimension(1000,1000);
        //table.setSize(900, 400);
        /*table.sizeColumnsToFit(500);
        table.setPreferredSize(1000);
        table.get*/
        //table.setPreferredSize(preferredSize);
        //panneau_info.setPreferredSize(preferredSize);
        //panneau_info.add(new JScrollPane(table), BorderLayout.CENTER);
        centre.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
    
    public void changerTableau(String[][] donnees, String[] entetes){
        centre.removeAll();
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        definirTableau(donnees, entetes);
        this.validate();
    }

    /**
     * Ajouter un écouteur à un bouton désigné par son nom
     *
     * @param nomBouton le nom du bouton sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        JButton bouton;
        bouton = switch (nomBouton.toUpperCase()) {
            case "AJOUTER" ->
                bouton = ajouter;
            case "MODIFIER" ->
                bouton = modifier;
            case "SUPPRIMER" ->
                bouton = supprimer;
            case "RETOUR" ->
                bouton = retour;
            case "INSERE" ->
                bouton = inserer;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
        if(donnees.equals("appartement") || donnees.equals("maison")){
            JRadioButton boutonRadio;
            boutonRadio = switch (nomBouton.toUpperCase()) {
                case "APPARTEMENT" ->
                    boutonRadio = buttonRadioAppart;
                case "MAISON" ->
                    boutonRadio = buttonRadioMaison;
                default ->
                    null;
            };
            if (boutonRadio != null) {
                boutonRadio.addActionListener(listener);
            }
        }   
    }

    public void quitter() {
        //System.exit(0);
        this.dispose();
    }
    
    public void verifierSelection() throws PasDeLignesSelectionneesException{
        if(this.table.getSelectedRowCount()==0){
            if(this.donnees.equals("campagne"))
                throw new PasDeLignesSelectionneesException("une "+this.donnees);
            else
                throw new PasDeLignesSelectionneesException("un "+this.donnees);
        }
    }
}