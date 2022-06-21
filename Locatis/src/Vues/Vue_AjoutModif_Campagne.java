package Vues;

import Main.Panneau_Message;
import Objets_Locatis.ListeDeDiffusion;
import Objets_Locatis.Utilisateur;
import Objets_Locatis.MyTime;
import Objets_Locatis.MyDate;
import Objets_Locatis.Campagne;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import Exceptions.EmptyFieldException;
import Exceptions.PasDeLignesSelectionneesException;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.*;

public class Vue_AjoutModif_Campagne extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    private JPanel panneau_gauche = new JPanel();
    private JPanel panneau_titre = new JPanel();
    //private JPanel panneau_message = new JPanel();
    private JPanel panneau_droite = new JPanel();
    private JPanel panneau_temps = new JPanel();
    private JPanel panneau_heure = new JPanel();
    private JPanel panneau_listes = new JPanel();
    private Panneau_Message panneau_message = new Panneau_Message();
    
    private JLabel titre = new JLabel();
    private JLabel titreCampagne_label = new JLabel("Titre : ");
    /*private JLabel message_label = new JLabel("Message : ");*/
    private JLabel frequence_label = new JLabel("Fréquence : ");
    private JLabel dateDebut_label = new JLabel ("Date de début : ");
    private JLabel heure_label = new JLabel ("Heure : ");
    private JLabel heure2_label = new JLabel (" h ");
    private JLabel dateFin_label = new JLabel ("Date de fin : ");
    private JLabel listes_label = new JLabel("Listes de diffusion : ");
    
    private JTextField titreCampagne = new JTextField();
    /*private JTextArea message = new JTextArea();*/
    private JComboBox frequence = new JComboBox();
    private JDateChooser dateDebut = new JDateChooser();
    private JComboBox heure = new JComboBox();
    private JComboBox minute = new JComboBox();
    private JDateChooser dateFin = new JDateChooser();
    private JList listes;
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Campagne campagne;
    
    public Vue_AjoutModif_Campagne(ArrayList<ListeDeDiffusion> liste){
        super("Ajouter une campagne");
        titre.setText("Ajouter une campagne");
        remplirListe(liste);
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
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
        this.titreCampagne.setText(this.campagne.getTitre());
        heure.setSelectedIndex(this.campagne.getHeure().getHeure());
        minute.setSelectedIndex(this.campagne.getHeure().getMinute());
        Calendar calendarDebut = new GregorianCalendar(campagne.getDateDebut().getAnnee(), campagne.getDateDebut().getMois()-1 , campagne.getDateDebut().getJour());
        dateDebut.setCalendar(calendarDebut);
        
        Calendar calendarFin = new GregorianCalendar(campagne.getDateFin().getAnnee(), campagne.getDateFin().getMois()-1 , campagne.getDateFin().getJour());
        dateFin.setCalendar(calendarFin);
        frequence.setSelectedItem(campagne.getFrequence());
        panneau_message.setObjet(campagne.getObjetMail());
        panneau_message.setContenu(campagne.getMessageMail());
        selectionnerListes();
        
        if(user.getCat().equals("uti2")){
            titreCampagne.setEnabled(false);
            panneau_message.getObjet().setEnabled(false);
            panneau_message.getContenu().setEnabled(false);
            listes.setEnabled(false);
        }
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
        
        panneau_info.setLayout(new GridLayout(1,2));
        panneau_info.add(this.panneau_gauche);
        panneau_info.add(this.panneau_droite);
        
        panneau_titre.setLayout(new GridLayout(1,2));
        panneau_titre.add(this.titreCampagne_label);
        panneau_titre.add(this.titreCampagne);
        
        /*panneau_message.setLayout(new GridLayout(1,2));
        panneau_message.add(this.message_label);
        panneau_message.add(this.message);*/
        
        
        panneau_gauche.setLayout(new BorderLayout());
        panneau_gauche.add(panneau_titre, BorderLayout.NORTH);
        panneau_gauche.add(panneau_message, BorderLayout.CENTER);
        
        panneau_message.setVisible(true);panneau_message.validate();
        panneau_gauche.validate();
        
//        ArrayList<String> liste = new ArrayList<>();
//        liste.add("a");
//        liste.add("b");
//        liste.add("c");
//        liste.add("a");
//        liste.add("b");
//        liste.add("c");
//        liste.add("a");
//        liste.add("b");
//        liste.add("c");
//        liste.add("a");
//        liste.add("b");
//        liste.add("c");
//        remplirListe(liste);
        
        /*remplirComboBox(heure, LocalDateTime.now().getHour(), 24);*/
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
        
        /*panneau_listes.setLayout(new BorderLayout());
        panneau_listes.add(listes_label, BorderLayout.NORTH);
        panneau_listes.add(listes, BorderLayout.CENTER);*/
        panneau_listes.setLayout(new GridLayout(1,2));
        panneau_listes.add(listes_label);
        JScrollPane pane = new JScrollPane(listes);
        panneau_listes.add(pane);
        
        panneau_droite.add(panneau_temps);
        panneau_droite.add(panneau_listes);
        
        this.frequence.addItem("Une seule fois");
        this.frequence.addItem("Quotidien");
        this.frequence.addItem("Hebdomadaire");
        this.frequence.addItem("Mensuel");
        this.frequence.addItem("Annuel");
        
        this.dateDebut.setCalendar(Calendar.getInstance());
        JTextFieldDateEditor editor = (JTextFieldDateEditor) this.dateDebut.getDateEditor();
        editor.setEditable(false);
        this.dateDebut.setMinSelectableDate(Calendar.getInstance().getTime());
        this.dateDebut.getDateEditor().addPropertyChangeListener(
            new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent e) {
                    if(dateFin.getCalendar().before(dateDebut.getCalendar()))
                        dateFin.setDate(dateDebut.getDate());
                    dateFin.setMinSelectableDate(dateDebut.getDate());
                    /*LocalDate debutDate = LocalDate.of(getDateDebut().getAnnee(), getDateDebut().getMois(), getDateDebut().getJour());
                    if(LocalDate.now().isEqual(debutDate)){
     
                        heure.removeAllItems();
                        remplirComboBox(heure, LocalDateTime.now().getHour(), 24);
                        
                    }else{
                        heure.removeAllItems();
                        remplirComboBox(heure, 0, 24);
                    }*/
                }
        });
        
        this.dateFin.setCalendar(Calendar.getInstance());
        
        JTextFieldDateEditor editor2 = (JTextFieldDateEditor) this.dateFin.getDateEditor();
        editor2.setEditable(false);
    }

    public JComboBox getFrequence() {
        return frequence;
    }

    public void setFrequence(JComboBox frequence) {
        this.frequence = frequence;
    }
    
    
    
    public MyDate getDateDebut(){
        return new MyDate(this.dateDebut.getCalendar().get(Calendar.YEAR), this.dateDebut.getCalendar().get(Calendar.MONTH)+1, this.dateDebut.getCalendar().get(Calendar.DAY_OF_MONTH));
    }
    
    public MyDate getDateFin(){
        return new MyDate(this.dateFin.getCalendar().get(Calendar.YEAR), this.dateFin.getCalendar().get(Calendar.MONTH)+1, this.dateFin.getCalendar().get(Calendar.DAY_OF_MONTH));
    }
    
    public MyTime getHeure(){
        return new MyTime(Integer.parseInt(this.heure.getSelectedItem().toString()),Integer.parseInt(this.minute.getSelectedItem().toString()));
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
        if(nomBouton.toUpperCase().equals("FREQUENCE"))
            this.frequence.addActionListener(listener);
    }
    
    @Override
    public void reset(){
        titreCampagne.setText("");
        //message.setText("");
        frequence.setSelectedIndex(0);
        heure.setSelectedIndex(0);
        minute.setSelectedIndex(0);
        listes.clearSelection();
        this.panneau_message.reset();
        dateDebut.setCalendar(Calendar.getInstance());
        dateFin.setCalendar(Calendar.getInstance());
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException, PasDeLignesSelectionneesException{
        if(this.titreCampagne.getText().equals("")){
            throw new EmptyFieldException("un titre");
        }else
        if(this.panneau_message.getObjetTexte().equals("")){
            throw new EmptyFieldException("un objet");
        }else
        if(this.panneau_message.getContenuTexte().equals("")){
            throw new EmptyFieldException("un message");
        }else
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
                this.frequence.getSelectedItem().toString(), this.panneau_message.getObjetTexte(), this.panneau_message.getContenuTexte(), this.listes.getSelectedValuesList(), null);
    }

    @Override
    public Campagne getObjetModifie() {
        return new Campagne(this.campagne.getId(), this.titreCampagne.getText(), this.getDateDebut(), this.getDateFin(), this.getHeure(), this.frequence.getSelectedItem().toString(), this.panneau_message.getObjetTexte(), this.panneau_message.getContenuTexte(), this.listes.getSelectedValuesList(), null);
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 600, 600);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
        if(this.frequence.getSelectedItem().equals("Une seule fois"))
            this.dateFin.setEnabled(false);
        else
            this.dateFin.setEnabled(true);
    }
    
    public void remplirListe(ArrayList<ListeDeDiffusion> uneListe){
        DefaultListModel<ListeDeDiffusion> model = new DefaultListModel<>();
        for(int i=0;i<uneListe.size();i++){
            model.addElement(uneListe.get(i));
        }
        listes = new JList<>(model);
    }
    
    public void remplirComboBox(JComboBox comboBox, int min, int max){
        for(int i=min;i<max;i++){
            if(i<10)
                comboBox.addItem("0"+i);
            else
                comboBox.addItem(i);
        }
    }
    
    public void test(){
        System.out.println("test ok");
    }
    
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
