package Controleurs;

import DAO.ConnectionBDD;
import DAO.Connexion;
import Modeles.Modele_Association;
import Vues.Vue_Association;
import Vues.Vue_Menu;
import Objets_Locatis.Appartement;
import Objets_Locatis.Batiment;
import Objets_Locatis.Locataire;
import Objets_Locatis.Maison;
import Objets_Locatis.Utilisateur;
import Exceptions.EmptyFieldException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Classe Controleur_Association implementant l'interface ActionListener
 * @author Benjamin Mathilde
 */
public class Controleur_Association implements ActionListener{

    private Vue_Association laVue;
    private Modele_Association unModele;
    private Utilisateur user;
    private java.sql.Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    /**
     * Constructeur de l'objet Controleur_Association
     * @param laVue : vue du controleur
     * @param unModele : modele du controleur
     * @param user : Utilisateur en cours d'utilisation
     */
    public Controleur_Association(Vue_Association laVue, Modele_Association unModele, Utilisateur user){
        this.laVue = laVue;
        this.unModele = unModele;
        this.user = user;
        this.laVue.ajouterEcouteur("Ajouter", this);
        this.laVue.ajouterEcouteur("Retirer", this);
        this.laVue.ajouterEcouteur("Retour", this);
        this.laVue.ajouterEcouteur("listeloca", this);
        ArrayList<Locataire> listeLocataire= this.unModele.getListeLocataire();
        for(int i = 0; i < listeLocataire.size(); i++){
            this.laVue.getLocataireList().addItem(listeLocataire.get(i));
            this.laVue.getLocataireListByLocataire().addItem(listeLocataire.get(i));
        }
        ArrayList<Appartement> listeAppartement = this.unModele.getListeAppartement();
        for(int i = 0; i < listeAppartement.size(); i++){
            this.laVue.getLogementList().addItem(listeAppartement.get(i));
        }
        ArrayList<Maison> listeMaison = this.unModele.getListMaison();
        for(int i = 0; i < listeMaison.size(); i++){
            this.laVue.getLogementList().addItem(listeMaison.get(i));
        }
        remplirComboBoxRetirer();
    }

    /**
     *
     * @return Vue_Association
     */
    public Vue_Association getVue() {
        return laVue;
    }
    
    /**
     * Méthode de la classe abstraite KeyAdapter
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = new JComboBox();
        if(e.getSource().getClass().isInstance(cb)){
            remplirComboBoxRetirer();
        }
        else{
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    try{    
                        Batiment batimentSelection;
                        if(this.laVue.getLogementList().getSelectedItem() != null){
                            if(this.laVue.getLogementList().getSelectedItem() instanceof Appartement){
                                batimentSelection = (Appartement) this.laVue.getLogementList().getSelectedItem();
                            }
                            else{
                                batimentSelection = (Maison) this.laVue.getLogementList().getSelectedItem();
                            }
                            Locataire locataireSelection = (Locataire) this.laVue.getLocataireList().getSelectedItem();
                            this.unModele.insertHabiter(batimentSelection.getID(), locataireSelection);
                            remplirComboBoxRetirer();
                        }
                        else{
                            throw new EmptyFieldException("Logement");
                        }
                    }catch(EmptyFieldException ex){}
                }
                case "RETOUR" -> {
                    this.laVue.quitter();
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){ 
                            Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(user), user);
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setBounds(100, 100, 350, 300);
                            controleur.getVue().setVisible(true);
                        }
                    });
                }
                case "RETIRER" -> {
                    try{    
                        Batiment batimentSelection;
                        if(this.laVue.getLogementListByLocataire().getSelectedItem() != null){
                            Locataire locataireSelection = (Locataire) this.laVue.getLocataireListByLocataire().getSelectedItem();
                            if(this.laVue.getLogementListByLocataire().getSelectedItem() instanceof Appartement){
                                batimentSelection = (Appartement) this.laVue.getLogementListByLocataire().getSelectedItem();
                            }
                            else{
                                batimentSelection = (Maison) this.laVue.getLogementListByLocataire().getSelectedItem();
                            }
                            this.unModele.removeHabiter(batimentSelection.getID(), locataireSelection);
                            this.laVue.getLogementListByLocataire().removeItemAt(this.laVue.getLogementListByLocataire().getSelectedIndex());
                        }
                        else{
                            throw new EmptyFieldException("Logement");
                        }
                    }catch(EmptyFieldException ex){}
                }
            }
        }
    }
    
    /**
     * Méthode qui vide et remplie les ComboBox pour mettre les logements associés aux Locataires
     */
    public void remplirComboBoxRetirer(){
        this.laVue.getLogementListByLocataire().removeAllItems();
        Locataire loca = (Locataire) this.laVue.getLocataireListByLocataire().getSelectedItem();
        ArrayList<Appartement> listeAppart = this.unModele.getListeAppartementByIdLocataire(loca.getId());
        ArrayList<Maison> listeMaison = this.unModele.getListMaisonByIdLocataire(loca.getId());
        for(int i = 0; i < listeAppart.size(); i++){
            this.laVue.getLogementListByLocataire().addItem(listeAppart.get(i));
        }
        for(int i = 0; i < listeMaison.size(); i++){
            this.laVue.getLogementListByLocataire().addItem(listeMaison.get(i));
        }
    }
}