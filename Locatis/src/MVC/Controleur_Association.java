/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Habiter_DAO;
import Locatis.Appartement;
import Locatis.Batiment;
import Locatis.Habiter;
import Locatis.Locataire;
import Locatis.Maison;
import Locatis.Utilisateur;
import com.sun.jdi.connect.spi.Connection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author Benjamin
 */
public class Controleur_Association implements ActionListener{

    private Vue_Association laVue;
    private Modele_Association unModele;
    private Utilisateur user;
    private java.sql.Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    
    public Controleur_Association(Vue_Association laVue, Modele_Association unModele, Utilisateur user){
        this.laVue = laVue;
        this.unModele = unModele;
        this.user = user;
        this.laVue.ajouterEcouteurBouton("Ajouter", this);
        this.laVue.ajouterEcouteurBouton("Retirer", this);
        this.laVue.ajouterEcouteurBouton("Retour", this);
        this.laVue.ajouterEcouteurBouton("listeloca", this);
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
        //this.laVue.pack();
    }

    public Vue_Association getVue() {
        return laVue;
    }

    public void setVue(Vue_Association laVue) {
        this.laVue = laVue;
    }

    public Modele_Association getModele() {
        return unModele;
    }

    public void setModele(Modele_Association unModele) {
        this.unModele = unModele;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = new JComboBox();
        if(e.getSource().getClass().isInstance(cb)){
            System.out.println("cc");
            remplirComboBoxRetirer();
        }
        else{
            JButton source = (JButton) e.getSource();
            switch (source.getText().toUpperCase()) {
                case "AJOUTER" -> {
                    Batiment batimentSelection;
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
                case "RETOUR" -> {
                    this.laVue.quitter();
                    SwingUtilities.invokeLater(new Runnable(){
                        public void run(){ 
                            Controleur_Menu controleur = new Controleur_Menu(new Vue_Menu(user),new Modele_Gestion("locataire"), user);
                            controleur.getVue().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            controleur.getVue().setBounds(100, 100, 350, 300);
                            controleur.getVue().setVisible(true);
                        }
                    });
                }
                case "RETIRER" -> {
                    Batiment batimentSelection;
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
            }
        }
        
    }
    
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