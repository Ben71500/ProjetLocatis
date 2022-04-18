package MVC;

import Locatis.Campagne;
import Locatis.Utilisateur;
import com.toedter.calendar.JDateChooser;
import interfaceGraphique.EmptyFieldException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class Vue_AjoutModif_Campagne extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel dateDebut_label = new JLabel ("Date de début : ");
    private JLabel dateFin_label = new JLabel ("Date de fin : ");
    private JLabel frequence_label = new JLabel("Fréquence : ");
    
    private JDateChooser dateDebut = new JDateChooser();
    private JDateChooser dateFin = new JDateChooser();
    private JComboBox frequence = new JComboBox();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Campagne campagne;
    
    public Vue_AjoutModif_Campagne(){
        super("Ajouter une campagne");
        titre.setText("Ajouter une campagne");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_AjoutModif_Campagne(Campagne camp) {
        super("Modifier une campagne");
        titre.setText("Modifier une campagne");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.campagne=camp;
        Calendar calendarDebut = new GregorianCalendar(campagne.getDateDebut().getAnnee(), campagne.getDateDebut().getMois()-1 , campagne.getDateDebut().getJour());
        dateDebut.setCalendar(calendarDebut);
        
        Calendar calendarFin = new GregorianCalendar(campagne.getDateFin().getAnnee(), campagne.getDateFin().getMois()-1 , campagne.getDateFin().getJour());
        dateDebut.setCalendar(calendarFin);
        //dateDebut.setText(user.getLogin());
        //dateFin.setText(user.getMotDePasse());
        /*switch(user.getCat()){
            case "usr" -> frequence.setSelectedIndex(0);
            case "ges" ->frequence.setSelectedIndex(1);
            case "adm" -> frequence.setSelectedIndex(2);
        }*/
        frequence.setSelectedItem(campagne.getFrequence());
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
        panneau_info.add(this.dateDebut_label);
        panneau_info.add(this.dateDebut);
        panneau_info.add(this.dateFin_label);
        panneau_info.add(this.dateFin);
        
        this.frequence.addItem("Utilisateur");
        this.frequence.addItem("Gestionnaire");
        this.frequence.addItem("Administrateur");
        panneau_info.add(frequence_label);
        panneau_info.add(frequence);
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
        /*dateDebut.setText("");
        dateFin.setText("");*/
        frequence.setSelectedIndex(0);
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException{
        /*if(this.dateDebut.getText().equals("")){
            throw new EmptyFieldException("un nom");
        }else
        if(this.dateFin.getText().equals("")){
            throw new EmptyFieldException("un prénom");
        }*/
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Utilisateur getNouvelObjet() {
        //return new Utilisateur(0, this.dateDebut.getText(), this.dateFin.getText(), this.frequence.getSelectedItem()+"");
        return null;
    }

    @Override
    public Utilisateur getObjetModifie() {
        //return new Utilisateur(this.user.getId(), this.dateDebut.getText(), this.dateFin.getText(), this.frequence.getSelectedItem()+"");
        return null;
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 350, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}
