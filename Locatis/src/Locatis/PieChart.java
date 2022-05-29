package Locatis;
import DAO.Appartement_DAO;
import DAO.Campagne_DAO;
import DAO.ConnectionBDD;
import DAO.Connexion;
import DAO.Locataire_DAO;
import java.sql.Connection;
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
    String data;
    String titre;
   
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
      int total = 0;
      int value = 0;
       switch(this.titre){
          case "age" :
                Locataire_DAO loca = new Locataire_DAO(connBdd);
                total = loca.selectAllOfLocataire();
                value = loca.selectAgeBefore18();
                dataset.setValue( "-18" , (value/total)*100);  
                value = loca.selectAgeBetween19and35();
                dataset.setValue( "19 - 35" , (value/total)*100);   
                value = loca.selectAgeBetween36and60();
                dataset.setValue( "36 - 60" , (value/total)*100);  
                value = loca.selectAgeAfter61();
                dataset.setValue( "61+" , (value/total)*100); 
                break;
          case "campagne" : 
                Campagne_DAO campagne = new Campagne_DAO(connBdd);
                total = campagne.getAllCampagne();
                value = campagne.getFinishCampagne();
                dataset.setValue( "Finis" ,(value/total)*100);  
                value = campagne.getNowCampagne();
                dataset.setValue( "En Cours" , (value/total)*100);
                value = campagne.getNotBeginCampagne();
                dataset.setValue( "Non commencé" , (value/total)*100);
                break;
          case "logement" : 
                Appartement_DAO appart = new Appartement_DAO(connBdd);
                total = appart.getAllLogement();
                value = appart.getLogementFull();
                dataset.setValue( "Occupé" , (value/total)*100);  
                value = appart.getLogementEmpty();
                dataset.setValue( "Vide" , (value/total)*100);
                break;
      }
      return dataset;
   }
   
   public JFreeChart createChart( PieDataset dataset) {
      JFreeChart chart = ChartFactory.createPieChart(      
         this.titre,   // chart title 
         dataset,          // data    
         true,             // include legend   
         true, 
         false);

      return chart;
   }
   
   public JPanel createDemoPanel() {
      JFreeChart chart = createChart(createDataset());  
      return new ChartPanel( chart ); 
   }
}