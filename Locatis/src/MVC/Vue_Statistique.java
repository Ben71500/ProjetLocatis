/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import javax.swing.*;
import org.jfree.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class Vue_Statistique extends JFrame{
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    /*PieChart graphiqueParTrancheAge = new PieChart();
    PieChart graphiqueParLogementOccuper = new PieChart();
    PieChart graphiqueDesCampagnesTerminer = new PieChart();*/
    private JToolBar barre = new JToolBar();
    
    public Vue_Statistique(){
        panneau.add(barre);
        panneau.setLayout(new BorderLayout());
        panneau.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panneau.add(this.haut, BorderLayout.NORTH);
        panneau.add(this.centre, BorderLayout.CENTER);
        JDialog ratioHommeFemmeJdialog = new JDialog();
        ratioHommeFemmeJdialog.setTitle("Ratio H/F");
        DefaultPieDataset pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Femme", 50);
        pieDataset.setValue("Homme", 50);
        JFreeChart pieChart = ChartFactory.createPieChart("Ratio H/F", pieDataset, true, false, false);
        panneau.setLayout(new GridLayout(1,1));
        ratioHommeFemmeJdialog.getContentPane().add(panneau, CENTER);
        ratioHommeFemmeJdialog.pack(); 
        ratioHommeFemmeJdialog.setVisible(true);
    }
    
}
