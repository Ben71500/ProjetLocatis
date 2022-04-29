package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Vue_AjoutModif_Utilisateurs extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel login_label = new JLabel ("Login : ");
    private JLabel mdp_label = new JLabel ("Mot de passe : ");
    private JLabel cat_label = new JLabel("Catégorie : ");
    
    private JTextField login = new JTextField();
    private JTextField mdp = new JTextField();
    private JComboBox categorie = new JComboBox();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur user;
    
    private static final long serialVersionUID = 1L;
    private JPanel pan;

    
    public Vue_AjoutModif_Utilisateurs(){
        super("Ajouter un utilisateur");
        titre.setText("Ajouter un utilisateur");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_AjoutModif_Utilisateurs(Utilisateur unUtilisateur) {
        super("Modifier un locataire");
        titre.setText("Modifier un Locataire");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.user=unUtilisateur;
        login.setText(user.getLogin());
        mdp.setText(user.getMotDePasse());
        /*switch(user.getCat()){
            case "usr" -> categorie.setSelectedIndex(0);
            case "ges" ->categorie.setSelectedIndex(1);
            case "adm" -> categorie.setSelectedIndex(2);
        }*/
        categorie.setSelectedItem(user.getCat());
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
        panneau_info.add(this.login_label);
        panneau_info.add(this.login);
        panneau_info.add(this.mdp_label);
        panneau_info.add(this.mdp);
        
        this.categorie.addItem("Utilisateur");
        this.categorie.addItem("Gestionnaire");
        this.categorie.addItem("Administrateur");
        panneau_info.add(cat_label);
        panneau_info.add(categorie);
    }
    
    public String getCat(){
        return switch (this.categorie.getSelectedItem()+"") {
            case "Administrateur" -> "adm";
            case "Gestionnaire" -> "ges";
            case "Utilisateur" -> "uti";
            default -> "";
        };
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
        login.setText("");
        mdp.setText("");
        categorie.setSelectedIndex(0);
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException{
        if(this.login.getText().equals("")){
            throw new EmptyFieldException("un nom");
        }else
        if(this.mdp.getText().equals("")){
            throw new EmptyFieldException("un prénom");
        }
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Utilisateur getNouvelObjet() {
        return new Utilisateur(0, this.login.getText(), this.mdp.getText(), this.getCat());
    }

    @Override
    public Utilisateur getObjetModifie() {
        return new Utilisateur(this.user.getId(), this.login.getText(), this.mdp.getText(), this.getCat());
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 350, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}