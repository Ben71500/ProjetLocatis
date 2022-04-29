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
    //private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons = new JPanel();
    private JPanel panneau_recherches = new JPanel();
    
    private JLabel titre;
    private JLabel rechercher_label = new JLabel("Rechercher : ");
    
    private JTextField recherche = new JTextField();
    
    private DefaultTableModel tableau;
    private JTable table;
    private TableRowSorter<TableModel> sort;
    
    private JRadioButton buttonRadioEgal = new JRadioButton("=", true);
    private JRadioButton buttonRadioSuperieur = new JRadioButton(">");
    private JRadioButton buttonRadioInferieur = new JRadioButton("<");
    
    private ButtonGroup group = new ButtonGroup();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton selectionnerTout = new JButton("Selectionner tout");
    private JButton deselectionner = new JButton("Tout déselectionner");
    private JButton modifier = new JButton("Modifier");
    private JButton supprimer = new JButton("Supprimer");
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
        centre.add(this.panneau_recherches, BorderLayout.NORTH);
        //centre.add(new JScrollPane(this.panneau_info), BorderLayout.CENTER);
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_recherches.setLayout(new GridLayout(1,2));
        panneau_recherches.add(this.rechercher_label);
        panneau_recherches.add(this.recherche);
        
        //Ajout des différents boutons
        panneau_boutons.setLayout(new GridLayout(1,4));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(modifier);
        panneau_boutons.add(supprimer);
        panneau_boutons.add(retour);
        
        
        
        
        panneau_boutons.add(buttonRadioEgal);
        panneau_boutons.add(buttonRadioSuperieur);
        panneau_boutons.add(buttonRadioInferieur);
        group.add(buttonRadioEgal);
        group.add(buttonRadioSuperieur);
        group.add(buttonRadioInferieur);
        
        
        this.getContentPane().add(this.panneau);
        this.pack();
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
        //Ajout du tableau des locataires
        this.table = new JTable(tableau){
            private static final long serialVersionUID = 1L;
            /*@Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 1,4:
                        return Integer.class;
                    case 2,3,5,6,7:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }*/
            public Class getColumnClass(int column) {
                //renvoie Boolean.class
                return getValueAt(0, column).getClass(); 
      }
        };
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
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
        
            JRadioButton boutonRadio;
            boutonRadio = switch (nomBouton.toUpperCase()) {
                case "EGAL" ->
                    boutonRadio = buttonRadioEgal;
                case "SUPERIEUR" ->
                    boutonRadio = buttonRadioSuperieur;
                case "INFERIEUR" ->
                    boutonRadio = buttonRadioInferieur;
                default ->
                    null;
            };
            if (boutonRadio != null) {
                boutonRadio.addActionListener(listener);
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
}