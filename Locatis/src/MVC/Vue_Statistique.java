/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import Locatis.PieChart;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionListener;
import javax.swing.*;
//import org.jfree.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Vue_Statistique extends JFrame{
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JLabel titre = new JLabel("Statistique");
    private JPanel centre = new JPanel();
    private JPanel bas = new JPanel();
    private PieChart graphiqueParTrancheAge = new PieChart("Tranche d'âge");
    private PieChart graphiqueParLogementOccuper = new PieChart("Logement occuper");
    private PieChart graphiqueDesCampagnesTerminer = new PieChart("Campagne en cours");
    private JButton retour = new JButton("Retour");
    
    public Vue_Statistique(){
        
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        haut.add(titre);
        
        centre.setLayout(new GridLayout(1, 3));
        
        bas.add(retour);
        retour.setText("Retour");
        
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        panneau.add(this.bas, BorderLayout.SOUTH);
        
        this.add(panneau);
        
        this.pack();
    }

    public JPanel getPanneau() {
        return panneau;
    }

    public void setPanneau(JPanel panneau) {
        this.panneau = panneau;
    }

    public JPanel getHaut() {
        return haut;
    }

    public void setHaut(JPanel haut) {
        this.haut = haut;
    }

    public JLabel getTitre() {
        return titre;
    }

    public void setTitre(JLabel titre) {
        this.titre = titre;
    }

    public JPanel getCentre() {
        return centre;
    }

    public void setCentre(JPanel centre) {
        this.centre = centre;
    }

    public JPanel getBas() {
        return bas;
    }

    public void setBas(JPanel bas) {
        this.bas = bas;
    }

    public PieChart getGraphiqueParTrancheAge() {
        return graphiqueParTrancheAge;
    }

    public void setGraphiqueParTrancheAge(PieChart graphiqueParTrancheAge) {
        this.graphiqueParTrancheAge = graphiqueParTrancheAge;
    }

    public PieChart getGraphiqueParLogementOccuper() {
        return graphiqueParLogementOccuper;
    }

    public void setGraphiqueParLogementOccuper(PieChart graphiqueParLogementOccuper) {
        this.graphiqueParLogementOccuper = graphiqueParLogementOccuper;
    }

    public PieChart getGraphiqueDesCampagnesTerminer() {
        return graphiqueDesCampagnesTerminer;
    }

    public void setGraphiqueDesCampagnesTerminer(PieChart graphiqueDesCampagnesTerminer) {
        this.graphiqueDesCampagnesTerminer = graphiqueDesCampagnesTerminer;
    }

    public JButton getRetour() {
        return retour;
    }

    public void setRetour(JButton retour) {
        this.retour = retour;
    }
    
    public void ajouterEcouteur(String nomBouton, ActionListener listener) {
        JButton bouton;
        bouton = switch (nomBouton.toUpperCase()) {
            case "RETOUR" ->
                bouton = retour;
            default ->
                null;
        };
        if (bouton != null) {
            bouton.addActionListener(listener);
        }
    }
    
    public void quitter() {
        //System.exit(0);
        this.dispose();
    }
}
