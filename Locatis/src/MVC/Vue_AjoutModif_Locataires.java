package MVC;

import Locatis.*;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import interfaceGraphique.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class Vue_AjoutModif_Locataires extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel nom_label = new JLabel ("Nom : ");
    private JLabel prenom_label = new JLabel ("Prénom : ");
    private JLabel age_label = new JLabel("Age : ");
    private JLabel anciennete_label = new JLabel("Anciennete : ");
    private JLabel mail_label = new JLabel("Adresse mail : ");
    private JLabel telephone_label = new JLabel("Numéro de téléphone : ");
    
    private JTextField nom = new JTextField();
    private JTextField prenom = new JTextField();
    private JTextField age = new JTextField();
    private JDateChooser anciennete = new JDateChooser();
    private JTextField mail = new JTextField();
    private JTextField telephone = new JTextField();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Locataire leLocataire;
    
    private static final long serialVersionUID = 1L;
    private JPanel pan;

    
    public Vue_AjoutModif_Locataires(){
        super("Ajouter un locataire");
        titre.setText("Ajouter un locataire");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_AjoutModif_Locataires(Locataire loca) {
        super("Modifier un locataire");
        titre.setText("Modifier un Locataire");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.leLocataire=loca;
        nom.setText(leLocataire.getNom());
        prenom.setText(leLocataire.getPrenom());
        age.setText(leLocataire.getAge()+"");
        
        Calendar calendar = new GregorianCalendar(leLocataire.getAnciennete().getAnnee(), leLocataire.getAnciennete().getMois()-1 , leLocataire.getAnciennete().getJour());
        anciennete.setCalendar(calendar);
        mail.setText(leLocataire.getMail());
        telephone.setText(leLocataire.getTelephone());
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
        
        panneau_info.setLayout(new GridLayout(6,2));
        panneau_info.add(this.nom_label);
        panneau_info.add(this.nom);
        panneau_info.add(this.prenom_label);
        panneau_info.add(this.prenom);
        panneau_info.add(this.age_label);
        panneau_info.add(this.age);
        panneau_info.add(this.anciennete_label);
        
        this.anciennete.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.anciennete.getDateEditor();
        editor.setEditable(false);
        
        panneau_info.add(this.anciennete);        
        panneau_info.add(this.mail_label);
        panneau_info.add(this.mail);
        panneau_info.add(this.telephone_label);
        panneau_info.add(this.telephone);
    }
    
    /*public int getID(){
        return this.leLocataire.getId();
    }
    
    public String getNom(){
        return this.nom.getText();
    }
    
    public String getPrenom(){
        return this.prenom.getText();
    }
    
    public int getAge(){
        return Integer.parseInt(this.age.getText());
    }*/
    
    public MyDate getDateAnciennete(){
        return new MyDate(this.anciennete.getCalendar().get(Calendar.YEAR), this.anciennete.getCalendar().get(Calendar.MONTH)+1, this.anciennete.getCalendar().get(Calendar.DAY_OF_MONTH));
    }
    
    /*public String getMail(){
        return this.mail.getText();
    }
    
    public String getTelephone(){
        return this.telephone.getText();
    }*/
    
    /*public String getDonnees() {
        return donnees;
    }*/

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
        this.nom.setText("");
        this.prenom.setText("");
        this.age.setText("");
        this.anciennete.setCalendar(Calendar.getInstance());
        this.mail.setText("");
        this.telephone.setText("");
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException{
        if(this.nom.getText().equals("")){
            throw new EmptyFieldException("un nom");
        }else
        if(this.prenom.getText().equals("")){
            throw new EmptyFieldException("un prénom");
        }else
        if(this.age.getText().equals("")){
            throw new EmptyFieldException("un age");
        }else
        /*if(this.anciennete.getCalendar().equals(null)){
            throw new EmptyFieldException("une anciennete");
        }else*/
        if(this.mail.getText().equals("")){
            throw new EmptyFieldException("une adresse mail");
        }else
        if(this.telephone.getText().equals("")){
            throw new EmptyFieldException("un numéro de téléphone");
        }
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Locataire getNouvelObjet() {
        return new Locataire(0, this.nom.getText(), this.prenom.getText(), Integer.parseInt(this.age.getText()), this.getDateAnciennete(), this.mail.getText(), this.telephone.getText());
    }

    @Override
    public Locataire getObjetModifie() {
        return new Locataire(this.leLocataire.getId(), this.nom.getText(), this.prenom.getText(), Integer.parseInt(this.age.getText()), this.getDateAnciennete(), this.mail.getText(), this.telephone.getText());           
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 350, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}