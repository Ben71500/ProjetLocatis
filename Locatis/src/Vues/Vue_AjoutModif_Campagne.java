package Vues;

import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Utilisateur;
import Objets_Locatis.MyTime;
import Objets_Locatis.MyDate;
import Objets_Locatis.Campagne;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import Exceptions.EmptyFieldException;
import Exceptions.PasDeLignesSelectionneesException;
import Graphique.Panneau;
import Graphique.Bouton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

/**
 * Classe implémentant l'interface Vue_AjoutModif et qui décrit la vue permettant d'ajouter ou de modifier une campagne
 * @author Benjamin Mathilde
 */
public class Vue_AjoutModif_Campagne extends JFrame implements Vue_AjoutModif{
    
    private Panneau panneau = new Panneau();
    private Panneau haut = new Panneau();
    private Panneau centre = new Panneau();
    private Panneau panneau_info = new Panneau();
    private Panneau panneau_boutons= new Panneau();
    private Panneau panneau_gauche = new Panneau();
    private Panneau panneau_titre = new Panneau();
    private Panneau panneau_droite = new Panneau();
    private Panneau panneau_temps = new Panneau();
    private Panneau panneau_heure = new Panneau();
    private Panneau panneau_listes = new Panneau();
    private Panneau panneau_message = new Panneau();
    
    private JLabel titre = new JLabel();
    private JLabel titreCampagne_label = new JLabel("Titre : ");
    private JLabel frequence_label = new JLabel("Fréquence : ");
    private JLabel dateDebut_label = new JLabel ("Date de début : ");
    private JLabel heure_label = new JLabel ("Heure : ");
    private JLabel heure2_label = new JLabel (" h ");
    private JLabel dateFin_label = new JLabel ("Date de fin : ");
    private JLabel listes_label = new JLabel("Listes de diffusion : ");
    private JLabel objet_label = new JLabel ("Objet : ");
    private JLabel contenu_label = new JLabel ("Texte : ");
    
    private JTextField titreCampagne = new JTextField();
    private JTextField objet = new JTextField();
    private JTextArea contenu = new JTextArea();
    private JComboBox frequence = new JComboBox();
    private JDateChooser dateDebut = new JDateChooser();
    private JComboBox heure = new JComboBox();
    private JComboBox minute = new JComboBox();
    private JDateChooser dateFin = new JDateChooser();
    private JList listes;
    
    private Bouton ajouter = new Bouton("Ajouter");
    private Bouton modifier = new Bouton("Modifier");
    private Bouton retour = new Bouton("Retour");
    
    private Campagne campagne;
    
    private ImageIcon logo = new ImageIcon("../logo.jpg");
    
    /**
     * Constructeur de la vue en cas d'ajout
     * @param liste : liste des listes de diffusion
     */
    public Vue_AjoutModif_Campagne(ArrayList<ListeDeDiffusion> liste){
        super("Ajouter une campagne");
        this.setIconImage(logo.getImage());
        titre.setText("Ajouter une campagne");
        remplirListe(liste);
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    /**
     * Constructeur de la vue en cas de modification
     * @param liste : liste des listes de diffusion
     * @param camp : campagne à modifier
     * @param user : utilisateur qui modifie la campagne
     */
    public Vue_AjoutModif_Campagne(ArrayList<ListeDeDiffusion> liste, Campagne camp, Utilisateur user) {
        super("Modifier une campagne");
        titre.setText("Modifier une campagne");
        remplirListe(liste);
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.campagne=camp;
        //Remplissage des champs par les informations de la campagne
        this.titreCampagne.setText(this.campagne.getTitre());
        heure.setSelectedIndex(this.campagne.getHeure().getHeure());
        minute.setSelectedIndex(this.campagne.getHeure().getMinute());
        Calendar calendarDebut = new GregorianCalendar(campagne.getDateDebut().getAnnee(), campagne.getDateDebut().getMois()-1 , campagne.getDateDebut().getJour());
        dateDebut.setCalendar(calendarDebut);
        Calendar calendarFin = new GregorianCalendar(campagne.getDateFin().getAnnee(), campagne.getDateFin().getMois()-1 , campagne.getDateFin().getJour());
        dateFin.setCalendar(calendarFin);
        frequence.setSelectedItem(campagne.getFrequence());
        this.objet.setText(campagne.getObjetMail());
        this.contenu.setText(campagne.getMessageMail());
        selectionnerListes();
        
        //Si l'utilisateur est de la catégorie  "Utilisateur 2", alors il peut
        //uniquement lancer des campagnes mais pas modifier certains paramètres
        if(user.getCat().equals("uti2")){
            titreCampagne.setEnabled(false);
            this.objet.setEnabled(false);
            this.contenu.setEnabled(false);
            listes.setEnabled(false);
        }
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
        
        panneau_info.setLayout(new GridLayout(1,2));
        panneau_info.add(this.panneau_gauche);
        panneau_info.add(this.panneau_droite);
        
        panneau_titre.setLayout(new GridLayout(1,2));
        panneau_titre.add(this.titreCampagne_label);
        panneau_titre.add(this.titreCampagne);
        
        Panneau pHaut = new Panneau();
        Panneau pCentre = new Panneau();
        panneau_message.setLayout(new BorderLayout());
        panneau_message.add(pHaut, BorderLayout.NORTH);
        panneau_message.add(pCentre, BorderLayout.CENTER);
        pHaut.setLayout(new GridLayout(1,2));
        pHaut.add(objet_label);
        pHaut.add(objet);
        pCentre.setLayout(new BorderLayout());
        pCentre.add(this.contenu_label, BorderLayout.NORTH);
        pCentre.add(this.contenu, BorderLayout.CENTER);
        
        contenu.setLineWrap(true);
        contenu.setWrapStyleWord(true);
        
        panneau_gauche.setLayout(new BorderLayout());
        panneau_gauche.add(panneau_titre, BorderLayout.NORTH);
        panneau_gauche.add(panneau_message, BorderLayout.CENTER);
        
        remplirComboBox(heure, 0, 24);
        remplirComboBox(minute, 0, 60);
        panneau_heure.add(this.heure);
        panneau_heure.add(this.heure2_label);
        panneau_heure.add(this.minute);
        
        panneau_temps.setLayout(new GridLayout(4,2));
        panneau_temps.add(frequence_label);
        panneau_temps.add(frequence);
        panneau_temps.add(this.dateDebut_label);
        panneau_temps.add(this.dateDebut);
        panneau_temps.add(this.heure_label);
        panneau_temps.add(this.panneau_heure);
        panneau_temps.add(this.dateFin_label);
        panneau_temps.add(this.dateFin);
        
        panneau_listes.setLayout(new GridLayout(1,2));
        panneau_listes.add(listes_label);
        panneau_listes.add(new JScrollPane(listes));
        
        panneau_droite.add(panneau_temps);
        panneau_droite.add(panneau_listes);
        
        this.frequence.addItem("Une seule fois");
        this.frequence.addItem("Quotidien");
        this.frequence.addItem("Hebdomadaire");
        this.frequence.addItem("Mensuel");
        this.frequence.addItem("Annuel");
        
        //Initialisation de la date de début à aujourd'hui
        this.dateDebut.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.dateDebut.getDateEditor();
        editor.setEditable(false);
        //La date de début ne peut pas être avant aujourd'hui
        this.dateDebut.setMinSelectableDate(Calendar.getInstance().getTime());
        //Si on modifie la date de début alors la date de fin ne peut pas être avant celle-ci
        this.dateDebut.getDateEditor().addPropertyChangeListener(
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if(dateFin.getCalendar()!=null || dateDebut.getCalendar()!=null){
                        if(dateFin.getCalendar().before(dateDebut.getCalendar()) || frequence.getSelectedItem().equals("Une seule fois"))
                            dateFin.setDate(dateDebut.getDate());
                        dateFin.setMinSelectableDate(dateDebut.getDate());
                    }
                }
        });
        //Initialisation de la date de fin à aujourd'hui
        this.dateFin.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) this.dateFin.getDateEditor();
        editor2.setEditable(false);
    }
    
    /**
     * Méthode qui retourne la date de début en objet MyDate
     * @return MyDate
     */
    public MyDate getDateDebut(){
        return new MyDate(this.dateDebut.getCalendar().get(Calendar.YEAR), this.dateDebut.getCalendar().get(Calendar.MONTH)+1, this.dateDebut.getCalendar().get(Calendar.DAY_OF_MONTH));
    }
    
    /**
     * Méthode qui retourne la date de fin en objet MyDate
     * @return MyDate
     */
    public MyDate getDateFin(){
        return new MyDate(this.dateFin.getCalendar().get(Calendar.YEAR), this.dateFin.getCalendar().get(Calendar.MONTH)+1, this.dateFin.getCalendar().get(Calendar.DAY_OF_MONTH));
    }
    
    /**
     * Méthode qui retourne l'heure en objet MyTime
     * @return MyTime
     */
    public MyTime getHeure(){
        return new MyTime(Integer.parseInt(this.heure.getSelectedItem().toString()),Integer.parseInt(this.minute.getSelectedItem().toString()));
    }
    
    @Override
    public void ajouterEcouteurBouton(String nomComposant, ActionListener listener) {        
        switch (nomComposant.toUpperCase()) {
            case "AJOUTER" ->
                ajouter.addActionListener(listener);
            case "MODIFIER" ->
                modifier.addActionListener(listener);
            case "RETOUR" ->
                retour.addActionListener(listener);
            case "FREQUENCE" ->
                frequence.addActionListener(listener);
        }
    }
    
    @Override
    public void reset(){
        titreCampagne.setText("");
        frequence.setSelectedIndex(0);
        heure.setSelectedIndex(0);
        minute.setSelectedIndex(0);
        listes.clearSelection();
        objet.setText("");
        contenu.setText("");
        dateDebut.setCalendar(Calendar.getInstance());
        dateFin.setCalendar(Calendar.getInstance());
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException, PasDeLignesSelectionneesException{
        //Vérification que les champs ne soient pas vides
        if(this.titreCampagne.getText().equals("")){
            throw new EmptyFieldException("un titre");
        }else
        if(this.objet.getText().equals("")){
            throw new EmptyFieldException("un objet");
        }else
        if(this.contenu.getText().equals("")){
            throw new EmptyFieldException("un message");
        }else
        //Vérification qu'au moins une liste de diffusion est sélectionnée
        if(this.listes.getSelectedIndices().length==0)
            throw new PasDeLignesSelectionneesException("une liste de diffusion");
        else
        if(this.dateDebut.getCalendar() == null)
            throw new EmptyFieldException("une date de début");
        else
        if(this.dateFin.getCalendar() == null)
            throw new EmptyFieldException("une date de fin");
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Campagne getNouvelObjet() {
        return new Campagne(0, this.titreCampagne.getText(), this.getDateDebut(), this.getDateFin(), this.getHeure(),
            this.frequence.getSelectedItem().toString(), objet.getText(), contenu.getText(), this.listes.getSelectedValuesList(), null);
    }

    @Override
    public Campagne getObjetModifie() {
        return new Campagne(this.campagne.getId(), this.titreCampagne.getText(), this.getDateDebut(), this.getDateFin(), this.getHeure(),
            this.frequence.getSelectedItem().toString(), objet.getText(), contenu.getText(), this.listes.getSelectedValuesList(), null);
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Dimension tailleEcran = Toolkit.getDefaultToolkit().getScreenSize();
        int height = 600;
        int width = 500;
        this.setBounds((tailleEcran.width-width)/2, (tailleEcran.height-height)/2, width, height);
        this.setVisible(true);
        //Si la fréquence est "Une seule fois" alors la date de fin est désactivée
        if(this.frequence.getSelectedItem().equals("Une seule fois"))
            this.dateFin.setEnabled(false);
        else
            this.dateFin.setEnabled(true);
    }
    
    /**
     * Méthode qui permet de remplir la JList avec les listes de diffusion
     * @param uneListe : les listes de diffusion
     */
    public void remplirListe(ArrayList<ListeDeDiffusion> uneListe){
        DefaultListModel<ListeDeDiffusion> model = new DefaultListModel<>();
        for(int i=0;i<uneListe.size();i++){
            model.addElement(uneListe.get(i));
        }
        listes = new JList<>(model);
    }
    
    /**
     * Méthode qui permet de remplir une JComboBox avec des nombres entiers
     * @param comboBox : JComboBox à remplir
     * @param min : nombre entier minimum
     * @param max : nombre entier maximum
     */
    public void remplirComboBox(JComboBox comboBox, int min, int max){
        for(int i=min;i<max;i++){
            if(i<10)
                comboBox.addItem("0"+i);
            else
                comboBox.addItem(i);
        }
    }
    
    /**
     * Méthode qui permet de sélectionner les listes de diffusion d'une campagne
     */
    public void selectionnerListes(){
        int[] tableauIndices = new int[campagne.getListes().size()];
        for(int i=0;i<campagne.getListes().size();i++){
            for (int j=0; j<listes.getModel().getSize();j++){
                if(campagne.getListes().get(i).toString().equals(listes.getModel().getElementAt(j).toString())){
                    tableauIndices[i]=j;
                }
            }
        }
        listes.setSelectedIndices(tableauIndices);
    }
}