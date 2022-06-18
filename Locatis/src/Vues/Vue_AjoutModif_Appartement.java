package Vues;

import Exceptions.ValeurIncorrecteException;
import Exceptions.EmptyFieldException;
import Objets_Locatis.Appartement;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;
import javax.swing.*;

public class Vue_AjoutModif_Appartement extends JFrame implements Vue_AjoutModif{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    private JPanel panneau_info = new JPanel();
    private JPanel panneau_boutons= new JPanel();
    
    private JLabel titre = new JLabel();
    private JLabel numero_Rue_label = new JLabel ("Numero Rue : ");
    private JLabel nom_Rue_label = new JLabel ("Nom Rue : ");
    private JLabel ville_label = new JLabel ("Ville : ");
    private JLabel codePostal_label = new JLabel ("Code Postal : ");
    private JLabel numeroEtage_label = new JLabel ("Nombre d'étage : ");
    private JLabel numeroAppart_label = new JLabel("Numéro d'appartement : ");
    
    private JTextField numeroRue = new JTextField();
    private JTextField nomRue = new JTextField();
    private JTextField ville = new JTextField();
    private JTextField codePostal = new JTextField();
    
    private JTextField nombreEtage = new JTextField();
    private JTextField numeroAppart = new JTextField();
    
    private JButton ajouter = new JButton("Ajouter");
    private JButton modifier = new JButton("Modifier");
    private JButton retour = new JButton("Retour");
    
    private Appartement appart;
    
    public Vue_AjoutModif_Appartement(){
        super("Ajouter un appartement");
        titre.setText("Ajouter un appartement");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(ajouter);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
    }
    
    public Vue_AjoutModif_Appartement(Appartement unAppart) {
        super("Modifier un appartement");
        titre.setText("Modifier un appartement");
        initialisation();
        
        panneau_boutons.setLayout(new GridLayout(1,2));
        panneau_boutons.add(modifier);
        panneau_boutons.add(retour);
        
        this.getContentPane().add(this.panneau);
        this.pack();
        
        this.appart=unAppart;
        numeroRue.setText(appart.getNumeroRue());
        nomRue.setText(appart.getNomRue());
        ville.setText(appart.getVille());
        codePostal.setText(appart.getCodePostal());
        nombreEtage.setText(appart.getEtage()+"");
        numeroAppart.setText(appart.getApart()+"");
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
        panneau_info.add(this.numero_Rue_label);
        panneau_info.add(this.numeroRue);
        
        panneau_info.add(this.nom_Rue_label);
        panneau_info.add(this.nomRue);
        
        panneau_info.add(this.ville_label);
        panneau_info.add(this.ville);
        
        panneau_info.add(this.codePostal_label);
        panneau_info.add(this.codePostal);
        
        panneau_info.add(this.numeroEtage_label);
        panneau_info.add(this.nombreEtage);
        panneau_info.add(this.numeroAppart_label);
        panneau_info.add(this.numeroAppart);
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
        numeroRue.setText("");
        nomRue.setText("");
        ville.setText("");
        codePostal.setText("");
        
        nombreEtage.setText("");
        numeroAppart.setText("");
    }
    
    @Override
    public void verifierChamps() throws EmptyFieldException, ValeurIncorrecteException{
        if(this.numeroRue.getText().equals("")){
            throw new EmptyFieldException("un numéro de rue");
        }else
        if(this.nomRue.getText().equals("")){
            throw new EmptyFieldException("un nom de rue");
        }else        
        if(this.ville.getText().equals("")){
            throw new EmptyFieldException("une ville");
        }else        
        if(this.codePostal.getText().equals("")){
            throw new EmptyFieldException("un code postal");
        }else
        if(!Pattern.compile("[0-9]{5}").matcher(codePostal.getText()).matches()){
            throw new ValeurIncorrecteException("un code postal");
        }else
        if(this.nombreEtage.getText().equals("")){
            throw new EmptyFieldException("un numéro d'étage");
        }else
        if(!Pattern.compile("[0-9]+").matcher(nombreEtage.getText()).matches()){
            throw new ValeurIncorrecteException("un numéro d'étage");
        }else
        if(this.numeroAppart.getText().equals("")){
            throw new EmptyFieldException("un numéro d'appartement");
        }else
        if(!Pattern.compile("[0-9]+").matcher(numeroAppart.getText()).matches()){
            throw new ValeurIncorrecteException("un numéro d'appartement");
        }
    }
    
    @Override
    public void quitter() {
        this.dispose();
    }

    @Override
    public Appartement getNouvelObjet() {
        return new Appartement(0, this.numeroRue.getText(), this.nomRue.getText(), this.ville.getText(), this.codePostal.getText(), Integer.parseInt(this.nombreEtage.getText()), Integer.parseInt(this.numeroAppart.getText()));
    }

    @Override
    public Appartement getObjetModifie() {
        return new Appartement(this.appart.getID(), this.numeroRue.getText(), this.nomRue.getText(), this.ville.getText(), this.codePostal.getText() , Integer.parseInt(this.nombreEtage.getText()), Integer.parseInt(this.numeroAppart.getText()));
    }

    @Override
    public void afficherVue() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(100, 100, 350, 300);
        //controleur.getVue().setSize(500,500);
        this.setVisible(true);
    }
}