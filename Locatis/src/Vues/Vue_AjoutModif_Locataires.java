package Vues;

import Exceptions.ValeurIncorrecteException;
import Exceptions.EmptyFieldException;
import Graphique.Panneau;
import Graphique.Bouton;
import Objets_Locatis.Locataire;
import Objets_Locatis.MyDate;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;
import javax.swing.*;

/**
 * Classe implémentant l'interface Vue_AjoutModif et qui décrit la vue permettant d'ajouter ou de modifier un locataire
 * @author Benjamin Mathilde
 */
public class Vue_AjoutModif_Locataires extends JFrame implements Vue_AjoutModif{
    
    private Panneau panneau = new Panneau();
    private Panneau haut = new Panneau();
    private Panneau centre = new Panneau();
    private Panneau panneau_info = new Panneau();
    private Panneau panneau_boutons= new Panneau();
    
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
    
    private Bouton ajouter = new Bouton("Ajouter");
    private Bouton modifier = new Bouton("Modifier");
    private Bouton retour = new Bouton("Retour");
    
    private Locataire leLocataire;
    
    private ImageIcon logo = new ImageIcon("../logo.jpg");
    
    /**
     * Constructeur de la vue en cas d'ajout
     */
    public Vue_AjoutModif_Locataires(){
        super("Ajouter un locataire");
        this.setIconImage(logo.getImage());
        titre.setText("Ajouter un locataire");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    /**
     * Constructeur de la vue en cas de modification
     * @param loca : le locataire à modifier
     */
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
    
    /**
     * Méthode qui permet d'ajouter les éléments communs de la vue
     */
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
        
        panneau_info.add(this.telephone);
    }
    
    /**
     * Méthode qui retourne la date de naissance en objet MyDate
     * @return MyDate
     */
    public MyDate getDateDeNaissance(){
        return new MyDate(this.dateDeNaissance.getCalendar().get(Calendar.YEAR), this.dateDeNaissance.getCalendar().get(Calendar.MONTH)+1, this.dateDeNaissance.getCalendar().get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener) {
        Bouton bouton;
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
        //Vérification que les champs ne soient pas vides
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
        //Vérification de la validité de l'adresse mail
        if(!Pattern.compile("[\\S]+@.+\\.[a-z]+").matcher(mail.getText()).matches()){
            throw new ValeurIncorrecteException("une adresse mail");
        }else
        //Vérification de la validité du numéro de téléphone : 10 chiffres
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
        this.setVisible(true);
    }
}