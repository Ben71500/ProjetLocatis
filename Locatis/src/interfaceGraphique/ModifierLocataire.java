package interfaceGraphique;

import DAO.Locataire_DAO;
import DAO.Connexion;
import DAO.ConnectionBDD;
import javax.swing.*;
import Locatis.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
/*import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;*/
//import org.jdatepicker.impl.*;
//import javafx.scene.control.DatePicker;
//import java.util.DatePicker;
//import org.jdatepicker.*;
/*import javafx.application.Application;*/

public class ModifierLocataire extends JFrame implements ActionListener {
    
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
    
    private JComboBox jour = new JComboBox();
    private JComboBox mois = new JComboBox();
    private JComboBox annee = new JComboBox();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Utilisateur userConnecte;
    private Locataire leLocataire;
    
    private static final long serialVersionUID = 1L;
    private JPanel pan;
    /*UtilDateModel model = new UtilDateModel();
    JDatePanelImpl datePanel;
    JDatePickerImpl datePicker;*/
    
    public ModifierLocataire(Utilisateur user) {
        super("Ajouter un locataire");
        userConnecte=user;
        titre.setText("Ajouter un Locataire");
        initialisation();
        
        
        //DatePicker date= new DatePicker();
        
        /*this.datePanel = new JDatePanelImpl(model,null);
        SimpleDateFormat format=new SimpleDateFormat();
        this.datePicker = new JDatePickerImpl(datePanel,format);*/
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        this.ajouter.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public ModifierLocataire(Utilisateur user, Locataire loca) {
        super("Modifier un locataire");
        userConnecte=user;
        titre.setText("Modifier un Locataire");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        this.modifier.addActionListener(this);
        this.retour.addActionListener(this);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.leLocataire=loca;
        nom.setText(leLocataire.getNom());
        prenom.setText(leLocataire.getPrenom());
        age.setText(leLocataire.getAge()+"");
        //Date d=new Date();
        //Calendar clndr=new Calendar();
        //MyDate date=new MyDate(01,01,2022);
        /*date.set( Calendar.MONTH )=Calendar.JANUARY ;
        date.se*/
        //anciennete.setCalendar(leLocataire.getAnciennete());
        //anciennete.setCalendar(date);
        //anciennete.setDateFormatString("essai");
        //Date d=new Date(01,01,2022);
        //anciennete.setDate(d);
       // anciennete.setDate(leLocataire.getAnciennete());
        //anciennete.setText(leLocataire.getAnciennete()+"");
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
        //this.anciennete.setFocusable(false);
        
        panneau_info.add(this.anciennete);        
        panneau_info.add(this.mail_label);
        panneau_info.add(this.mail);
        panneau_info.add(this.telephone_label);
        panneau_info.add(this.telephone);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
            
            if(e.getSource()==ajouter){
                try{
                    verifierChamps();
                    MyDate arrivee = new MyDate(leLocataire.getAnciennete().getAnnee(),leLocataire.getAnciennete().getMois()-1,leLocataire.getAnciennete().getJour());
                    leLocataire=new Locataire(0 ,nom.getText(), prenom.getText(), Integer.parseInt(age.getText()), arrivee, mail.getText(), telephone.getText());
                    Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                    Locataire_DAO nouveauLocataire=new Locataire_DAO(connBdd);
                    nouveauLocataire.create(leLocataire);
                    PopupInformation popup=new PopupInformation("Le locataire "+leLocataire.getNom()+" "+leLocataire.getPrenom()+" a été ajouté.");
                    reset();
                }catch (EmptyFieldException ex) {
                    ex.afficherErreur();
                }
            }
        
        if(e.getSource()==modifier){
            try {
                verifierChamps();
                MyDate arrivee = new MyDate(leLocataire.getAnciennete().getAnnee(),leLocataire.getAnciennete().getMois()-1,leLocataire.getAnciennete().getJour());
                leLocataire = new Locataire(leLocataire.getId(),nom.getText(), prenom.getText(), Integer.parseInt(age.getText()), arrivee, mail.getText(), telephone.getText());
                Connection connBdd= ConnectionBDD.getInstance(new Connexion());
                Locataire_DAO nouveauLocataire=new Locataire_DAO(connBdd);
                nouveauLocataire.update(leLocataire);
                PopupInformation popup=new PopupInformation("Le locataire "+leLocataire.getNom()+" "+leLocataire.getPrenom()+" a bien été modifié.");
                this.dispose();
                SwingUtilities.invokeLater(new Runnable(){
                    public void run(){
                        GestionLocataires fenetre=new GestionLocataires(userConnecte);
                        fenetre.setBounds(100, 100, 550, 300);
                        fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        fenetre.setVisible(true);
                    }
                });
            } catch (EmptyFieldException ex) {
                ex.afficherErreur();
            }
            
        }
        if(e.getSource()==retour){
            this.dispose();
            SwingUtilities.invokeLater(new Runnable(){
                public void run(){
                    GestionLocataires fenetre=new GestionLocataires(userConnecte);
                    fenetre.setBounds(100, 100, 550, 300);
                    fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fenetre.setVisible(true);
                }
            });
        }
    }
    
    public void reset(){
        this.nom.setText("");
        this.prenom.setText("");
        this.age.setText("");
        //this.anciennete.setText("");
        this.mail.setText("");
        this.telephone.setText("");
    }
    
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
        /*if(this.anciennete.getText().equals("")){
            throw new EmptyFieldException("une anciennete");
        }else*/
        if(this.mail.getText().equals("")){
            throw new EmptyFieldException("une adresse mail");
        }else
        if(this.telephone.getText().equals("")){
            throw new EmptyFieldException("un numéro de téléphone");
        }
    }
}