package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

public class Vue_AjoutModif_Utilisateurs extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    private JPanel panneau_tableau= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel login_label = new JLabel ("Login : ");
    private JLabel mdp_label = new JLabel ("Mot de passe : ");
    private JLabel cat_label = new JLabel("Catégorie : ");
    private JLabel email_label = new JLabel("Email : ");
    private JLabel password_label = new JLabel("Password (Email) : ");
    
    private JTextField login = new JTextField();
    private JTextField mdp = new JTextField();
    private JComboBox categorie = new JComboBox();
    private JTextField email = new JTextField();
    private JTextField password = new JTextField();
    
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
        panneau.add(this.panneau_boutons, BorderLayout.SOUTH);
          
        titre.setFont(new java.awt.Font("Tahoma", 1, 24));
        haut.add(this.titre);
        
        centre.setLayout(new GridLayout(1,2));
        centre.add(this.panneau_info);
        centre.add(this.panneau_tableau);
        
        panneau_info.setLayout(new GridLayout(5,2));
        panneau_info.add(this.login_label);
        panneau_info.add(this.login);
        panneau_info.add(this.mdp_label);
        panneau_info.add(this.mdp);
        
        this.categorie.addItem("Utilisateur");
        this.categorie.addItem("Gestionnaire");
        this.categorie.addItem("Administrateur");
        panneau_info.add(cat_label);
        panneau_info.add(categorie);
        panneau_info.add(email_label);
        panneau_info.add(email);
        panneau_info.add(password_label);
        panneau_info.add(password);
        
        afficherTableau();
    }
    
    public void afficherTableau(){
        
        panneau_tableau.setLayout(new GridLayout(6,2));
        
        ajouterCase("Administrateur");
        ajouterCase("Création des utilisateurs, création des destinataires, gestion des droits. Création, planification et lancement des campagnes. Consultation des statistiques.");
        ajouterCase("Gestionnaire 1");
        ajouterCase("Création, planification et lancement des campagnes sur les locataires et utilisateurs. Consultation des statistiques.");
        ajouterCase("Gestionnaire 2");
        ajouterCase("Création, planification et lancement des campagnes sur les locataires, utilisateurs et gestionnaires. Consultation des statistiques.");
        ajouterCase("Gestionnaire 3");
        ajouterCase("Création, planification et lancement des campagnes sur les locataires et toutes les catégories d'utilisateurs. Consultation des statistiques.");
        ajouterCase("Utilisateur 1");
        ajouterCase("Consultation des statistiques.");
        ajouterCase("Utilisateur 2");
        ajouterCase("Consultation des statistiques. Planification et lancement d’une campagne.");
    }
    
    public void ajouterCase(String s){
        JTextPane txt = new JTextPane();
        //txt.setEditable(false);
        txt.setText(s);
        
        /*txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setOpaque(false);
        System.out.println(txt.getAlignmentX());
        System.out.println(txt.getAlignmentY());*/
        
        //txt.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        //txt.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        txt.setBorder(BorderFactory.createLineBorder(Color.black));
        panneau_tableau.add(txt);
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
        email.setText("");
        password.setText("");
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException{
        if(this.login.getText().equals("")){
            throw new EmptyFieldException("un nom");
        }else
        if(this.mdp.getText().equals("")){
            throw new EmptyFieldException("un prénom");
        }
        else{
            if(this.email.getText().equals("")){
                throw new EmptyFieldException("un email");
            }
            else{
                if(this.password.getText().equals("")){
                    throw new EmptyFieldException("un password");
                }
            }
        }
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Utilisateur getNouvelObjet() {
        return new Utilisateur(0, this.login.getText(), this.mdp.getText(), this.getCat(), this.email.getText(), this.password.getText());
    }

    @Override
    public Utilisateur getObjetModifie() {
        return new Utilisateur(this.user.getId(), this.login.getText(), this.mdp.getText(), this.getCat(), this.email.getText(), this.password.getText());
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 1000, 500);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}