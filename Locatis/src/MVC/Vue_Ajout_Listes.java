package MVC;

import Locatis.*;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import interfaceGraphique.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.table.*;

public class Vue_Ajout_Listes extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_infos = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    private JPanel panneau_nom = new JPanel();
    private JPanel panneau_recherches = new JPanel();
    private JPanel panneau_boutons_radios = new JPanel();
    private JPanel panneau_donnees = new JPanel();
    private JPanel panneau_premiere_ligne = new JPanel();
    
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
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton selectionnerTout = new JButton("Selectionner tout");
    private JButton deselectionner = new JButton("Tout deselectionner");
    private JButton retour = new JButton("Retour");
    
    private String donnees;    
    
    private ListeDeDiffusion listeDiffusion;

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
        //centre.add(new JScrollPane(this.panneau_info), BorderLayout.CENTER);
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
        //panneau_boutons_radios.setVisible(false);
        
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
        
        nombre.setModel(new SpinnerNumberModel(0, 0, 200, 1));
        nombre.setEditor(new JSpinner.DefaultEditor(nombre));
        
        this.date.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.date.getDateEditor();
        editor.setEditable(false);
        /*this.date.getDateEditor().addPropertyChangeListener(
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    /*if(date.getCalendar().before(date.getCalendar()))
                        System.out.println("");*/
                    //System.out.println("d");
               /* }
        });*/
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_Ajout_Listes(String donnee, ListeDeDiffusion liste) {
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
        //centre.add(new JScrollPane(this.panneau_info), BorderLayout.CENTER);
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
        //panneau_boutons_radios.setVisible(false);
        
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
        
        listeDiffusion = liste;
        nom.setText(listeDiffusion.getNom());
    }

    public String getNom() {
        return nom.getText();
    }
    
    public String getDateSaisie(){
        return new MyDate(this.date.getCalendar().get(Calendar.YEAR), this.date.getCalendar().get(Calendar.MONTH)+1, this.date.getCalendar().get(Calendar.DAY_OF_MONTH)).getDateSQL();
    }

    public JDateChooser getDate() {
        return date;
    }
    
    

    public void setTitre(String titre) {
        this.titre.setText(titre);
    }

    public DefaultTableModel getTableau() {
        return tableau;
    }
    
    public int getLigne(){
        return this.table.getSelectedRow();
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

    public void setDonnees(String donnees) {
        this.donnees = donnees;
    }

    public void definirTableau(Object[][] donnees, String[] entetes) {
        this.tableau = new DefaultTableModel(donnees, entetes)
        {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column==0;
            }
        };
        
        sort = new TableRowSorter<>(this.tableau);
        for(int i=0;i<entetes.length;i++)
            sort.setSortable(i, false);
        //Ajout du tableau des locataires
        this.table = new JTable(tableau){
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
    
    public void changerTableau(Object[][] donnees, String[] entetes){
        centre.removeAll();
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_infos, BorderLayout.NORTH);
        definirTableau(donnees, entetes);
        this.validate();
    }

    /**
     * Ajouter un écouteur à un composant désigné par son nom
     *
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

    public void quitter() {
        //System.exit(0);
        this.dispose();
    }
    
    public void afficherPanneauBoutonsRadios(){
        if(donnees.equals("locataire") && tri.getSelectedItem()!=null){
            if(tri.getSelectedItem().equals("Age") || tri.getSelectedItem().equals("Ancienneté")){
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
    
    public String getBoutonRadioSigne(){
        if(this.buttonRadioEgal.isSelected())
            return "=";
        if(this.buttonRadioSuperieur.isSelected())
            return ">";
        if(this.buttonRadioInferieur.isSelected())
            return "<";
        return "";
    }
    
    public String getBoutonRadioDonnees(){
        if(this.buttonRadioLocataires.isSelected())
            return "Locataires";
        if(this.buttonRadioUtilisateurs.isSelected())
            return "Utilisateurs";
        else
            return "Signe";
    }

    public JRadioButton getButtonRadioLocataires() {
        return buttonRadioLocataires;
    }

    public JRadioButton getButtonRadioUtilisateurs() {
        return buttonRadioUtilisateurs;
    }

    public JComboBox getTri() {
        return tri;
    }
    
    
    
    public String getCategorie(){
        if(tri.getSelectedItem()!=null){
            if(tri.getSelectedItem().equals("Ancienneté"))
                return "Anciennete";
            else
                return (String) tri.getSelectedItem();
        }
        return "";
    }

    public String getNombre() {
        return nombre.getValue()+"";
    }
    
    public JSpinner getNombreJSpinner(){
        return this.nombre;
    }
    
    public void verifierChamps() throws EmptyFieldException{
        if(this.nom.getText().equals("")){
            throw new EmptyFieldException("un nom de liste");
        }
    }
    
    public void reset(){
        this.nom.setText("");
    }
    
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 800, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
    
    public void remplirComboBox(){
        if(this.donnees.equals("locataire")){
            tri.removeAllItems();
            tri.addItem("Tous");
            tri.addItem("ID");
            tri.addItem("Nom");
            tri.addItem("Prénom");
            tri.addItem("Age");
            tri.addItem("Ancienneté");
        }
        else{
            tri.removeAllItems();
            tri.addItem("Tous");
            tri.addItem("ID");
            tri.addItem("Login");
            tri.addItem("Catégorie");
        }
    }
    
    public ListeDeDiffusion getObjetModifie() {
        return new ListeDeDiffusion(this.listeDiffusion.getId(), this.nom.getText(), null);
    }
}