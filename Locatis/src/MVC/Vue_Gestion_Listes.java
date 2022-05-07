package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.*;

public class Vue_Gestion_Listes extends JFrame {

    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_infos = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    private JPanel panneau_nom = new JPanel();
    private JPanel panneau_recherches = new JPanel();
    private JPanel panneau_boutons_radios = new JPanel();
    
    private JLabel titre;
    private JLabel nom_label = new JLabel("Nom de la liste de diffusion : ");
    private JLabel rechercher_label = new JLabel("Rechercher : ");
    private JLabel trier_label = new JLabel("Trier par : ");
    
    private JTextField nom = new JTextField();
    private JTextField recherche = new JTextField();
    private JTextField nombre = new JTextField();
    
    private JComboBox tri = new JComboBox();
    
    private DefaultTableModel tableau;
    private JTable table;
    private TableRowSorter<TableModel> sort;
    
    private JRadioButton buttonRadioEgal = new JRadioButton("=", true);
    private JRadioButton buttonRadioSuperieur = new JRadioButton(">");
    private JRadioButton buttonRadioInferieur = new JRadioButton("<");
    
    private ButtonGroup group = new ButtonGroup();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton selectionnerTout = new JButton("Selectionner tout");
    private JButton deselectionner = new JButton("Tout deselectionner");
    private JButton retour = new JButton("Retour");
    

    public Vue_Gestion_Listes() {
        super("Création d'une liste de diffusion");
        
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
        
        
        panneau_nom.setLayout(new GridLayout(1,2));
        panneau_nom.add(nom_label);
        panneau_nom.add(nom);
       
        panneau_recherches.setLayout(new GridLayout(1,5));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        panneau_recherches.add(trier_label);
        panneau_recherches.add(tri);
        panneau_recherches.add(panneau_boutons_radios);
        tri.addItem("Tous");
        tri.addItem("ID");
        tri.addItem("Nom");
        tri.addItem("Prénom");
        tri.addItem("Age");
        tri.addItem("Ancienneté");
        //panneau_boutons_radios.setVisible(false);
        
        panneau_infos.setLayout(new GridLayout(2,1));
        panneau_infos.add(panneau_nom);
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
        group.add(buttonRadioEgal);
        group.add(buttonRadioSuperieur);
        group.add(buttonRadioInferieur);
        
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }

    public String getNom() {
        return nom.getText();
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
            //private static final long serialVersionUID = 1L;
            @Override
            public Class getColumnClass(int column) {
                //renvoie Boolean.class
                return getValueAt(0, column).getClass(); 
            }
        };
        
        table.setSelectionMode(SINGLE_SELECTION);
        table.setRowSorter(sort);
        //table.setAutoCreateRowSorter(false);
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
            case "SELECTIONNER TOUT" ->
                selectionnerTout.addActionListener(listener);
            case "TOUT DESELECTIONNER" ->
                deselectionner.addActionListener(listener);
            case "RETOUR" ->
                retour.addActionListener(listener);
            
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
    
    public void verifierSelection() throws PasDeLignesSelectionneesException{
        if(this.table.getSelectedRowCount()==0){
            throw new PasDeLignesSelectionneesException("une liste");
        }
    }
    
    public void afficherPanneauBoutonsRadios(){
        if(tri.getSelectedItem().equals("Age") || tri.getSelectedItem().equals("Ancienneté"))
            this.panneau_boutons_radios.setVisible(true);
        else
            this.panneau_boutons_radios.setVisible(false);
    }
    
    
    public String getBoutonRadio(){
        if(this.buttonRadioEgal.isSelected())
            return "=";
        if(this.buttonRadioSuperieur.isSelected())
            return ">";
        if(this.buttonRadioInferieur.isSelected())
            return "<";
        return "";
    }
    
    public String getCategorie(){
        return (String) tri.getSelectedItem();
    }

    public int getNombre() {
        try {
            return Integer.parseInt(nombre.getText());
        }catch(NumberFormatException e){
            return -1;
        }  
    }
    
    public JTextField getNombreJTextField(){
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
}