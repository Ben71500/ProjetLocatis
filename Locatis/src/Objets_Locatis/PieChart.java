package Objets_Locatis;
import DAO.Appartement_DAO;
import DAO.Campagne_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
 
public class PieChart extends ApplicationFrame {
    private Connection connBdd= ConnectionBDD.getInstance(new Connexion());
    private String data;
    private String titre;
   
   public PieChart( String titre ) {
        super(titre);
        this.titre = titre;
   }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
   
   public PieDataset createDataset() {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      double total = 0.0;
      double value = 0.0;
       switch(this.data){
          case "age" -> {
              Locataire_DAO loca = new Locataire_DAO(connBdd);
              ArrayList<Locataire> liste = (ArrayList<Locataire>) loca.getAll();
              total = liste.size();
              value = loca.compterLocataire(liste, 0, 19);
              if(value !=0){
                  dataset.setValue( "-18 ans" , (value/total)*100);
              }
              value = loca.compterLocataire(liste, 19, 36);
              if(value !=0){
                  dataset.setValue( "19 - 35 ans" , (value/total)*100);
              }
              value = loca.compterLocataire(liste, 36, 61);
              if(value !=0){
                  dataset.setValue( "36 - 60 ans" , (value/total)*100);
              }
              value = loca.compterLocataire(liste, 61, 150);
              if(value !=0){
                  dataset.setValue( "+61 ans" , (value/total)*100);
              }
            }
          case "campagne" -> {
              Campagne_DAO campagne = new Campagne_DAO(connBdd);
              total = campagne.getAllCampagne();
              if(total == 0){
                  total = 1;
              }
              value = campagne.getFinishCampagne();
              dataset.setValue( "Finis" ,(value/total)*100);
              value = campagne.getNowCampagne();
              dataset.setValue( "En Cours" , (value/total)*100);
              value = campagne.getNotBeginCampagne();
              dataset.setValue( "Non commencé" , (value/total)*100);
            }
          case "logement" -> {
              Appartement_DAO appart = new Appartement_DAO(connBdd);
              total = appart.getAllLogement();
              if(total == 0){
                  total = 1;
              }
              value = appart.getLogementFull();
              dataset.setValue( "Occupé" , (value/total)*100);
              value = appart.getLogementEmpty();
              dataset.setValue( "Vide" , (value/total)*100);
            }
      }
      return dataset;
   }
   
   public JFreeChart createChart( PieDataset dataset) {
      JFreeChart chart = ChartFactory.createPieChart(      
         this.titre,   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         true);

      return chart;
   }
   
   public JPanel createDemoPanel() {
      JFreeChart chart = createChart(createDataset());  
      return new ChartPanel( chart ); 
   }
}