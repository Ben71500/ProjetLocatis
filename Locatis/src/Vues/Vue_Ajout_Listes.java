package Vues;

import Exceptions.EmptyFieldException;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.MyDate;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.table.*;
import Graphique.Panneau;
import Graphique.Bouton;

/**
 * Classe dérivée de JFrame qui décrit la vue permettant d'ajouter ou de modifier une liste de diffusion
 * @author Benjamin Mathilde
 */
public class Vue_Ajout_Listes extends JFrame {

    private Panneau panneau = new Panneau();
    private Panneau haut = new Panneau();
    private Panneau centre = new Panneau();
    private Panneau panneau_infos = new Panneau();
    private Panneau panneau_boutons = new Panneau();
    private Panneau panneau_recherches = new Panneau();
    private Panneau panneau_boutons_radios = new Panneau();
    private Panneau panneau_premiere_ligne = new Panneau();
    
    private JLabel titre;
    private JLabel nom_label = new JLabel("Nom de la liste de diffusion : ");
    private JLabel rechercher_label = new JLabel("Rechercher : ");
    private JLabel trier_label = new JLabel("Trier par : ");
    
    private JTextField nom = new JTextField();
    private JTextField recherche = new JTextField();
    private JSpinner nombre = new JSpinner();
    private JDateChooser date = new JDateChooser();
    
    private JComboBox tri = new JComboBox();
    
    private DefaultTableModel tableau;
    private JTable table;
    private TableRowSorter<TableModel> sort;
    
    private JRadioButton buttonRadioLocataires = new JRadioButton("Locataires");
    private JRadioButton buttonRadioUtilisateurs = new JRadioButton("Utilisateurs");
    private JRadioButton buttonRadioEgal = new JRadioButton("=", true);
    private JRadioButton buttonRadioSuperieur = new JRadioButton(">");
    private JRadioButton buttonRadioInferieur = new JRadioButton("<");
    
    private ButtonGroup group_donnees= new ButtonGroup();
    private ButtonGroup group_signe = new ButtonGroup();
    
    private Bouton ajouter = new Bouton("Ajouter");
    private Bouton modifier = new Bouton("Modifier");
    private Bouton selectionnerTout = new Bouton("Selectionner tout");
    private Bouton deselectionner = new Bouton("Tout deselectionner");
    private Bouton retour = new Bouton("Retour");
    
    private String donnees;    
    
    private ListeDeDiffusion listeDiffusion;

    /**
     * Constructeur de la vue en cas d'ajout
     * @param donnee : type de personnes contenu dans les listes (locataire ou utilisateur)
     */
    public Vue_Ajout_Listes(String donnee) {
        super("Création d'une liste de diffusion");
        this.donnees = donnee;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        
        //Titre
        titre = new JLabel("Les listes");
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_infos, BorderLayout.NORTH);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_premiere_ligne.setLayout(new GridLayout(1,4));
        panneau_premiere_ligne.add(nom_label);
        panneau_premiere_ligne.add(nom);
        panneau_premiere_ligne.add(buttonRadioLocataires);
        panneau_premiere_ligne.add(buttonRadioUtilisateurs);
        group_donnees.add(buttonRadioLocataires);
        group_donnees.add(buttonRadioUtilisateurs);
        buttonRadioLocataires.setSelected(true);
       
        panneau_recherches.setLayout(new GridLayout(1,5));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        panneau_recherches.add(trier_label);
        panneau_recherches.add(tri);
        panneau_recherches.add(panneau_boutons_radios);
        remplirComboBox();
        
        panneau_infos.setLayout(new GridLayout(2,1));
        panneau_infos.add(panneau_premiere_ligne);
        panneau_infos.add(panneau_recherches);
        
        //Ajout des différents boutons
        panneau_boutons.setLayout(new GridLayout(1,4));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(selectionnerTout);
        panneau_boutons.add(deselectionner);
        panneau_boutons.add(retour);
        
        panneau_boutons_radios.setLayout(new GridLayout(1,4));
        panneau_boutons_radios.add(buttonRadioEgal);
        panneau_boutons_radios.add(buttonRadioSuperieur);
        panneau_boutons_radios.add(buttonRadioInferieur);
        panneau_boutons_radios.add(nombre);
        panneau_boutons_radios.setVisible(false);
        group_signe.add(buttonRadioEgal);
        group_signe.add(buttonRadioSuperieur);
        group_signe.add(buttonRadioInferieur);
        
        nombre.setModel(new SpinnerNumberModel(40, 0, 200, 1));
        nombre.setEditor(new JSpinner.DefaultEditor(nombre));
        
        this.date.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.date.getDateEditor();
        editor.setEditable(false);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    /**
     * Constructeur de la vue en cas de modification
     * @param donnee : type de personnes contenu dans les listes (locataire ou utilisateur)
     * @param liste : liste de diffusion à modifier
     */
    public Vue_Ajout_Listes(String donnee, ListeDeDiffusion liste) {
        super("Modification d'une liste de diffusion");
        this.donnees = donnee;
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        
        //Titre
        titre = new JLabel("Les listes");
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_infos, BorderLayout.NORTH);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_premiere_ligne.setLayout(new GridLayout(1,4));
        panneau_premiere_ligne.add(nom_label);
        panneau_premiere_ligne.add(nom);
        panneau_premiere_ligne.add(buttonRadioLocataires);
        panneau_premiere_ligne.add(buttonRadioUtilisateurs);
        group_donnees.add(buttonRadioLocataires);
        group_donnees.add(buttonRadioUtilisateurs);
        if(donnee.equals("locataire"))
            buttonRadioLocataires.setSelected(true);
        else
            buttonRadioUtilisateurs.setSelected(true);
        panneau_recherches.setLayout(new GridLayout(1,5));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        panneau_recherches.add(trier_label);
        panneau_recherches.add(tri);
        panneau_recherches.add(panneau_boutons_radios);
        remplirComboBox();
        
        panneau_infos.setLayout(new GridLayout(2,1));
        panneau_infos.add(panneau_premiere_ligne);
        panneau_infos.add(panneau_recherches);
        
        //Ajout des différents boutons
        panneau_boutons.setLayout(new GridLayout(1,4));
        panneau_boutons.add(modifier);
        panneau_boutons.add(selectionnerTout);
        panneau_boutons.add(deselectionner);
        panneau_boutons.add(retour);
        
        panneau_boutons_radios.setLayout(new GridLayout(1,4));
        panneau_boutons_radios.add(buttonRadioEgal);
        panneau_boutons_radios.add(buttonRadioSuperieur);
        panneau_boutons_radios.add(buttonRadioInferieur);
        panneau_boutons_radios.add(nombre);
        panneau_boutons_radios.setVisible(false);
        group_signe.add(buttonRadioEgal);
        group_signe.add(buttonRadioSuperieur);
        group_signe.add(buttonRadioInferieur);
        
        nombre.setModel(new SpinnerNumberModel(0, 0, 200, 1));
        nombre.setEditor(new JSpinner.DefaultEditor(nombre));
        
        this.date.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.date.getDateEditor();
        editor.setEditable(false);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        //Remplissage des champs par les informations de la liste de diffusion
        listeDiffusion = liste;
        nom.setText(listeDiffusion.getNom());
    }

    /**
     *
     * @return la saisie du nom de la liste
     */
    public String getNom() {
        return nom.getText();
    }
    
    /**
     *
     * @return la date en format SQL dela date saisie lors du tri par date de naissance
     */
    public String getDateSaisie(){
        return new MyDate(this.date.getCalendar().get(Calendar.YEAR), this.date.getCalendar().get(Calendar.MONTH)+1, this.date.getCalendar().get(Calendar.DAY_OF_MONTH)).getDateSQL();
    }

    /**
     *
     * @return JDateChooser
     */
    public JDateChooser getDate() {
        return date;
    }
    
    /**
     *
     * @return le JTextField permettant d'effectuer une recherche
     */
    public JTextField getRecherche(){
        return this.recherche;
    }
    
    /**
     *
     * @return TableRowSorter
     */
    public TableRowSorter<TableModel> getSort(){
        return this.sort;
    }

    /**
     *
     * @return la JTable des personnes
     */
    public JTable getTable(){
        return table;
    }
    
    /**
     *
     * @return le JRadioButton des locataires
     */
    public JRadioButton getButtonRadioLocataires() {
        return buttonRadioLocataires;
    }

    /**
     *
     * @return le JRadioButton des utilisateurs
     */
    public JRadioButton getButtonRadioUtilisateurs() {
        return buttonRadioUtilisateurs;
    }

    /**
     *
     * @return la JComboBox permettant de choisir la catégorie du tri
     */
    public JComboBox getTri() {
        return tri;
    }
    
    /**
     *
     * @return le nombre choisi
     */
    public String getNombre() {
        return nombre.getValue()+"";
    }
    
    /**
     *
     * @return JSpinner
     */
    public JSpinner getNombreJSpinner(){
        return this.nombre;
    }

    /**
     * Méthode qui permet de modifier les données contenues dans la JTable
     * @param donnees : les nouvelles données
     */
    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    /**
     * Méthode qui permet de créer un nouveau tableau de personnes
     * @param donnees : nouvelles données
     * @param entetes : tableau des entetes
     */
    public void definirTableau(Object[][] donnees, String[] entetes) {
        this.tableau = new DefaultTableModel(donnees, entetes){
            //permet de faire en sorte qu'on ne puisse pas modifier le contenu de la JTable sauf la colonne des cases à cocher
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==0;
            }
        };
        //désactivation du tri des colonnes
        sort = new TableRowSorter<>(this.tableau);
        for(int i=0;i<entetes.length;i++)
            sort.setSortable(i, false);
        //Ajout du tableau des personnes
        this.table = new JTable(tableau){
            //permet d'afficher des cases à cocher
            @Override
            public Class getColumnClass(int column) {
                //renvoie Boolean.class
                return getValueAt(0, column).getClass(); 
            }
        };
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        centre.add(new JScrollPane(this.table), BorderLayout.CENTER);
    }
    
    /**
     * Méthode qui permet d'effacer le tableau et d'en ajouter un nouveau
     * @param donnees : nouvelles données
     * @param entetes : tableau des entetes
     */
    public void changerTableau(Object[][] donnees, String[] entetes){
        centre.removeAll();
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_infos, BorderLayout.NORTH);
        definirTableau(donnees, entetes);
        this.validate();
    }

    /**
     * Méthode qui permet d'ajouter un écouteur à un composant désigné par son nom
     * @param nomComposant le nom du composant sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    public void ajouterEcouteur(String nomComposant, ActionListener listener) {
        switch (nomComposant.toUpperCase()) {
            case "AJOUTER" ->
                ajouter.addActionListener(listener);
            case "MODIFIER" ->
                modifier.addActionListener(listener);
            case "SELECTIONNER TOUT" ->
                selectionnerTout.addActionListener(listener);
            case "TOUT DESELECTIONNER" ->
                deselectionner.addActionListener(listener);
            case "RETOUR" ->
                retour.addActionListener(listener);
                
            case "LOCATAIRES" ->
                buttonRadioLocataires.addActionListener(listener);
            case "UTILISATEURS" ->
                buttonRadioUtilisateurs.addActionListener(listener);
            
            case "EGAL" ->
                buttonRadioEgal.addActionListener(listener);
            case "SUPERIEUR" ->
                buttonRadioSuperieur.addActionListener(listener);
            case "INFERIEUR" ->
                buttonRadioInferieur.addActionListener(listener);
                
            case "TRI" ->
                tri.addActionListener(listener);
        } 
    }

    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter() {
        this.dispose();
    }
    
    /**
     * Méthode qui permet d'afficher ou d'enlever le panneau des boutons radios
     * permettant de filtrer les données en focntion de certains paramètres
     */
    public void afficherPanneauBoutonsRadios(){
        if(donnees.equals("locataire") && tri.getSelectedItem()!=null){
            if(tri.getSelectedItem().equals("Age") || tri.getSelectedItem().equals("Date de naissance")){
                this.panneau_boutons_radios.setVisible(true);
                this.panneau_boutons_radios.remove(3);
                if(tri.getSelectedItem().equals("Age"))
                    this.panneau_boutons_radios.add(nombre);
                else{
                    this.panneau_boutons_radios.add(date);
                }
            }else
                this.panneau_boutons_radios.setVisible(false);
        }else
            this.panneau_boutons_radios.setVisible(false);
    }
    
    /**
     * Méthode qui retourn le signe sélectionné
     * @return String
     */
    public String getBoutonRadioSigne(){
        if(this.buttonRadioEgal.isSelected())
            return "=";
        if(this.buttonRadioSuperieur.isSelected())
            return ">";
        if(this.buttonRadioInferieur.isSelected())
            return "<";
        return "";
    }

    /**
     * Méthode qui retourne la catégorie choisie
     * @return String
     */
    public String getCategorie(){
        if(tri.getSelectedItem()!=null){
            if(tri.getSelectedItem().equals("Date de naissance"))
                return "DateDeNaissance";
            else
                return (String) tri.getSelectedItem();
        }
        return "";
    }

    /**
     * Méthode qui permet de vérifier que tous les champs sont bien saisis
     * @throws EmptyFieldException :  l'exception est lancée si aucun nom de liste n'est saisi
     */
    public void verifierChamps() throws EmptyFieldException{
        if(this.nom.getText().equals("")){
            throw new EmptyFieldException("un nom de liste");
        }
    }
    
    /**
     * Méthode qui permet de réinitialiser le nom de la liste
     */
    public void reset(){
        this.nom.setText("");
    }
    
    /**
     * Méthode qui permet de rendre la vue visible
     */
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 800, 300);
        this.setVisible(true);
    }
    
    /**
     * Méthode qui permet de remplir la JComboBox avec les
     * différentes catégories en fonction du type de personne
     */
    public void remplirComboBox(){
        if(this.donnees.equals("locataire")){
            tri.removeAllItems();
            tri.addItem("Tous");
            tri.addItem("ID");
            tri.addItem("Nom");
            tri.addItem("Prénom");
            tri.addItem("Age");
            tri.addItem("Date de naissance");
        }
        else{
            tri.removeAllItems();
            tri.addItem("Tous");
            tri.addItem("ID");
            tri.addItem("Login");
            tri.addItem("Catégorie");
        }
    }
    
    /**
     * Méthode qui retourne la liste de diffusion modifiée
     * @return la liste modifiée
     */
    public ListeDeDiffusion getObjetModifie() {
        return new ListeDeDiffusion(this.listeDiffusion.getId(), this.nom.getText(), null);
    }
}