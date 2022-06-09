package MVC;

import Locatis.*;
import interfaceGraphique.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

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
    private Utilisateur userConnecte;
    
    private static final long serialVersionUID = 1L;
    private JPanel pan;

    
    public Vue_AjoutModif_Utilisateurs(Utilisateur utilisateurConnecte){
        super("Ajouter un utilisateur");
        titre.setText("Ajouter un utilisateur");
        this.userConnecte=utilisateurConnecte;
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_AjoutModif_Utilisateurs(Utilisateur unUtilisateur, Utilisateur utilisateurConnecte) {
        super("Modifier un locataire");
        titre.setText("Modifier un Locataire");
        this.userConnecte=utilisateurConnecte;
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.user=unUtilisateur;
        login.setText(user.getLogin());
        mdp.setText(user.getMotDePasse());
        switch(user.getCat()){
            case "uti1" -> categorie.setSelectedItem("Utilisateur 1");
            case "uti2" -> categorie.setSelectedItem("Utilisateur 2");
            case "ges1" -> categorie.setSelectedItem("Gestionnaire 1");
            case "ges2" -> categorie.setSelectedItem("Gestionnaire 2");
            case "ges3" -> categorie.setSelectedItem("Gestionnaire 3");
            case "adm" -> categorie.setSelectedItem("Administrateur");
        }
        email.setText(user.getMail());
        password.setText(user.getPassword());
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
        
        this.categorie.addItem("Utilisateur 1");
        this.categorie.addItem("Utilisateur 2");
        if(!this.userConnecte.getCat().equals("ges1")){
            this.categorie.addItem("Gestionnaire 1");
            this.categorie.addItem("Gestionnaire 2");
            this.categorie.addItem("Gestionnaire 3");
            if(!this.userConnecte.getCat().equals("ges2"))
                this.categorie.addItem("Administrateur");
        }
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
        //JTextPane txt = new JTextPane();
        //txt.setEditable(false);
        JTextArea txt = new JTextArea();
        //JLabel txt = new JLabel();
        
        txt.setText(s);
        
        //txt.setHorizontalAlignment((int) CENTER_ALIGNMENT);
        
        /*StyledDocument doc = txt.getStyledDocument();
        SimpleAttributeSet centrer = new SimpleAttributeSet();
	StyleConstants.setAlignment(centrer, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, s.length(), centrer, false);
        */
        
        /*//Création d'un style
		SimpleAttributeSet style_normal = new SimpleAttributeSet();
		StyleConstants.setFontFamily(style_normal, "Calibri");
		StyleConstants.setFontSize(style_normal, 14);

		//Création du style pour l'affichage du titre
		SimpleAttributeSet style_titre = new SimpleAttributeSet();
		style_titre.addAttributes(style_normal);
		StyleConstants.setForeground(style_titre, Color.BLUE);
		StyleConstants.setUnderline(style_titre, true);
		StyleConstants.setFontSize(style_titre, 18);
		StyleConstants.setBold(style_titre, true);
		
		//Création du style qui permet de centrer le texte
		SimpleAttributeSet centrer = new SimpleAttributeSet();
		StyleConstants.setAlignment(centrer, StyleConstants.ALIGN_CENTER);
        */
        
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setOpaque(false);
        txt.setEditable(false);
        txt.setFocusable(false);
        
        //txt.invalidate();
        /*System.out.println(txt.getAlignmentX());
        System.out.println(txt.getAlignmentY());*/
        
        //txt.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
        //txt.setAlignmentY(JTextArea.CENTER_ALIGNMENT);
        
        txt.setBorder(BorderFactory.createLineBorder(Color.black));
        panneau_tableau.add(txt);
    }
    
    public String getCat(){
        return switch (this.categorie.getSelectedItem()+"") {
            case "Administrateur" -> "adm";
            case "Gestionnaire 1" -> "ges1";
            case "Gestionnaire 2" -> "ges2";
            case "Gestionnaire 3" -> "ges3";
            case "Utilisateur 1" -> "uti1";
            case "Utilisateur 2" -> "uti2";
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
        this.setBounds(100, 100, 1000, 700);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}