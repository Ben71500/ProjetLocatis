package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vue_AjoutModif_Maison extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel adresse_label = new JLabel ("Adresse : ");
    
    private JTextField adresse = new JTextField();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Maison maison;
    
    private static final long serialVersionUID = 1L;
    private JPanel pan;

    
    public Vue_AjoutModif_Maison(){
        super("Ajouter une maison");
        titre.setText("Ajouter une maison");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_AjoutModif_Maison(Maison uneMaison) {
        super("Modifier une maison");
        titre.setText("Modifier une maison");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.maison = uneMaison;
        adresse.setText(maison.getAdresse());
    }
    
    public void initialisation(){
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
          
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.panneau_info, BorderLayout.CENTER);
        centre.add(this.panneau_boutons, BorderLayout.SOUTH);
        
        panneau_info.setLayout(new GridLayout(3,2));
        panneau_info.add(this.adresse_label);
        panneau_info.add(this.adresse);
    }
    
    /**
     * Ajouter un écouteur à un bouton désigné par son nom
     *
     * @param nomBouton le nom du bouton sur lequel l'écouteur doit être ajouté
     * @param listener l'écouteur à ajouter
     */
    @Override
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        JButton bouton;
        bouton = switch (nomBouton.toUpperCase()) {
            case "AJOUTER" ->
                bouton = ajouter;
            case "MODIFIER" ->
                bouton = modifier;
            case "RETOUR" ->
                bouton = retour;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
    }
    
    @Override
    public void reset(){
        adresse.setText("");
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException{
        if(this.adresse.getText().equals("")){
            throw new EmptyFieldException("une adresse");
        }
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Maison getNouvelObjet() {
        return new Maison(0, this.adresse.getText());
    }

    @Override
    public Maison getObjetModifie() {
        return new Maison(this.maison.getID(), this.maison.getAdresse());
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 350, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}