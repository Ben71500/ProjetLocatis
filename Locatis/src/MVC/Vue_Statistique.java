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
import javax.swing.*;
//import org.jfree.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Vue_Statistique extends JFrame{
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    PieChart graphiqueParTrancheAge = new PieChart("Tranche d'âge");
    PieChart graphiqueParLogementOccuper = new PieChart("Logement occuper");
    PieChart graphiqueDesCampagnesTerminer = new PieChart("Campagne en cours");
    private JToolBar barre = new JToolBar();
    
    public Vue_Statistique(){
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        centre.setLayout(new GridLayout(1,3));
        this.add(panneau);
        this.add(centre);
        panneau.add(barre);
        graphiqueParLogementOccuper.setData("logement");
        centre.add(graphiqueParLogementOccuper.createDemoPanel());
        graphiqueParTrancheAge.setData("age");
        centre.add(graphiqueParTrancheAge.createDemoPanel());
        graphiqueDesCampagnesTerminer.setData("campagne");
        centre.add(graphiqueDesCampagnesTerminer.createDemoPanel());
        this.pack();
    }
}
