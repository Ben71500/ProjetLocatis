/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import javax.swing.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
/**
 *
 * @author Benjamin
 */
public class Vue_Statistique extends Application{
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    /*PieChart graphiqueParTrancheAge = new PieChart();
    PieChart graphiqueParLogementOccuper = new PieChart();
    PieChart graphiqueDesCampagnesTerminer = new PieChart();*/
    private JToolBar barre = new JToolBar();
    
    public Vue_Statistique(){
        haut.add(barre);
        Application.launch("Coucou");
    }
    
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        haut.add(barre);
        Scene scene = new Scene(new Group());
        stage.setTitle("Imported Fruits");
        stage.setWidth(500);
        stage.setHeight(500);
         ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
            new PieChart.Data("Grapefruit", 13),
            new PieChart.Data("Oranges", 25),
            new PieChart.Data("Plums", 10),
            new PieChart.Data("Pears", 22),
            new PieChart.Data("Apples", 30));
        PieChart graphiqueFruit = new PieChart(pieChartData);
        graphiqueFruit.setTitle("Fruit importer");
        
        ((Group) scene.getRoot()).getChildren().add(graphiqueFruit);
        stage.setScene(scene);
        stage.show();
    }
}
