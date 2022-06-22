package Vues;

import Exceptions.PasDeLignesSelectionneesException;
import Objets_Locatis.Utilisateur;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.table.*;

/**
 * Classe de l'objet Vue_Gestion, l'objet fait l'affichage de la gestion des locataires, utilisateurs, maisons, appartements, campagnes et listes de diffusion
 * @author Benjamin Mathilde
 */
public class Vue_Gestion extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
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

    /**
     * Constructeur de l'objet Vue_Gestion
     * @param lesDonnees : type des données
     * @param user : utilisateur connecté
     */
    public Vue_Gestion(String lesDonnees, Utilisateur user) {
        super("Gestion des "+lesDonnees+"s");
        this.donnees = lesDonnees;
        this.utilisateur = user;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        //Titre
        titre = new JLabel("Les "+donnees+"s");
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        
        panneau_recherches.setLayout(new GridLayout(1,2));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        
        //Ajout des différents boutons en fonction de la catégorie de l'utilisateur
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

    /**
     * 
     * @param titre
     */
    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    /**
     *
     * @param donnees
     */
    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    /**
     *
     * @return String
     */
    public String getDonnees() {
        return donnees;
    }
    
    /**
     *
     * @return DefaultTableModel
     */
    public DefaultTableModel getTableau() {
        return tableau;
    }
    
    /**
     *
     * @return int
     */
    public int getLigne(){
        return this.table.getSelectedRow();
    }
    
    /**
     *
     * @return JRadioButton
     */
    public JRadioButton getButtonRadioAppart(){
        return buttonRadioAppart;
    }
    
    /**
     *
     * @return JRadioButton
     */
    public JRadioButton getButtonRadioMaison(){
        return buttonRadioMaison;
    }
    
    /**
     *
     * @return JTextField
     */
    public JTextField getRecherche(){
        return this.recherche;
    }
    
    /**
     *
     * @return TableRowSorter<TableModel>
     */
    public TableRowSorter<TableModel> getSort(){
        return this.sort;
    }

    /**
     *
     * @return JTable
     */
    public JTable getTable() {
        return table;
    }

    /**
     * Méthode qui permet de créer un nouveau tableau de données
     * @param donnees : nouvelles données
     * @param entetes : tableau des entetes
     */
    public void definirTableau(String[][] donnees, String[] entetes) {
        this.tableau = new DefaultTableModel(donnees, entetes){
            //permet de faire en sorte qu'on ne puisse pas modifier le contenu de la JTable
            @Override
            public boolean isCellEditable(int row, int column) {
               return false;
            }
        };
        
        sort = new TableRowSorter<>(this.tableau);
        //Ajout du nouveau tableau
        this.table = new JTable(tableau);
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        centre.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
    
    /**
     * Méthode qui permet d'effacer le tableau et d'en ajouter un nouveau
     * @param donnees : nouvelles données
     * @param entetes : tableau des entetes
     */
    public void changerTableau(String[][] donnees, String[] entetes){
        centre.removeAll();
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        definirTableau(donnees, entetes);
        this.validate();
    }

    /**
     * Méthode qui permet d'ajouter un écouteur à un composant désigné par son nom
     * @param nomElement le nom du composant sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    public void ajouterEcouteur(String nomElement, ActionListener listener) {
        switch (nomElement.toUpperCase()) {
            case "AJOUTER" ->
                ajouter.addActionListener(listener);
            case "MODIFIER" ->
                modifier.addActionListener(listener);
            case "SUPPRIMER" ->
                supprimer.addActionListener(listener);
            case "RETOUR" ->
                retour.addActionListener(listener);
            case "INSERE" ->
                inserer.addActionListener(listener);
            case "APPARTEMENT" ->
                buttonRadioAppart.addActionListener(listener);
            case "MAISON" ->
                buttonRadioMaison.addActionListener(listener);
        }
    }

    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
    
    /**
     * Méthode qui permet de vérifier que tous les champs sont bien saisis
     * @throws PasDeLignesSelectionneesException : exception levée si aucune ligne n'est sélectionnée
     */
    public void verifierSelection() throws PasDeLignesSelectionneesException{
        if(this.table.getSelectedRowCount()==0){
            if(this.donnees.equals("campagne"))
                throw new PasDeLignesSelectionneesException("une "+this.donnees);
            else
                throw new PasDeLignesSelectionneesException("un "+this.donnees);
        }
    }
}