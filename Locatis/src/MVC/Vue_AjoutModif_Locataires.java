package MVC;

import Locatis.*;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import interfaceGraphique.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Vue_AjoutModif_Locataires extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel nom_label = new JLabel ("Nom : ");
    private JLabel prenom_label = new JLabel ("Prénom : ");
    private JLabel dateDeNaissance_label = new JLabel("Date de naissance : ");
    private JLabel mail_label = new JLabel("Adresse mail : ");
    private JLabel telephone_label = new JLabel("Numéro de téléphone : ");
    
    private JTextField nom = new JTextField();
    private JTextField prenom = new JTextField();
    private JDateChooser dateDeNaissance = new JDateChooser();
    private JTextField mail = new JTextField();
    private JTextField telephone = new JTextField();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Locataire leLocataire;
    
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
        
        Calendar calendar = new GregorianCalendar(leLocataire.getDateDeNaissance().getAnnee(), leLocataire.getDateDeNaissance().getMois()-1 , leLocataire.getDateDeNaissance().getJour());
        dateDeNaissance.setCalendar(calendar);
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
        
        panneau_info.setLayout(new GridLayout(5,2));
        panneau_info.add(this.nom_label);
        panneau_info.add(this.nom);
        panneau_info.add(this.prenom_label);
        panneau_info.add(this.prenom);
        panneau_info.add(this.dateDeNaissance_label);
        
        
        Calendar calendarDebut = new GregorianCalendar(2000, 0, 1);
        dateDeNaissance.setCalendar(calendarDebut);
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.dateDeNaissance.getDateEditor();
        editor.setEditable(false);
        this.dateDeNaissance.setMaxSelectableDate(Calendar.getInstance().getTime());
        
        panneau_info.add(this.dateDeNaissance);        
        panneau_info.add(this.mail_label);
        panneau_info.add(this.mail);
        panneau_info.add(this.telephone_label);
        
        // Formater un numéro de téléphone
      /*try {
         MaskFormatter formatter = new MaskFormatter("##########");
         //formatter.setPlaceholderCharacter('#');formatter.setPlaceholder("");
         telephone = new JFormattedTextField(formatter);
         
      } catch(Exception e) {
         e.printStackTrace();
      }*/
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
    
    public MyDate getDateDeNaissance(){
        return new MyDate(this.dateDeNaissance.getCalendar().get(Calendar.YEAR), this.dateDeNaissance.getCalendar().get(Calendar.MONTH)+1, this.dateDeNaissance.getCalendar().get(Calendar.DAY_OF_MONTH));
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
        this.dateDeNaissance.setCalendar(Calendar.getInstance());
        this.mail.setText("");
        this.telephone.setText("");
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException, ValeurIncorrecteException{
        if(this.nom.getText().equals("")){
            throw new EmptyFieldException("un nom");
        }else
        if(this.prenom.getText().equals("")){
            throw new EmptyFieldException("un prénom");
        }else
        if(this.dateDeNaissance.getCalendar() == null){
            throw new EmptyFieldException("une date de naissance");
        }else
        if(this.mail.getText().equals("")){
            throw new EmptyFieldException("une adresse mail");
        }else
        if(this.telephone.getText().equals("")){
            throw new EmptyFieldException("un numéro de téléphone");
        }else
        if(!Pattern.compile("[\\S]+@.+\\.[a-z]+").matcher(mail.getText()).matches()){
            throw new ValeurIncorrecteException("une adresse mail");
        }else
        if(!Pattern.compile("[0-9]{10}").matcher(telephone.getText()).matches()){
            throw new ValeurIncorrecteException("un numéro de téléphone");
        }
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Locataire getNouvelObjet() {
        
        return new Locataire(0, this.nom.getText(), this.prenom.getText(), this.getDateDeNaissance(), this.mail.getText(), this.telephone.getText());
    }

    @Override
    public Locataire getObjetModifie() {
        return new Locataire(this.leLocataire.getId(), this.nom.getText(), this.prenom.getText(), this.getDateDeNaissance(), this.mail.getText(), this.telephone.getText());           
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 350, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}