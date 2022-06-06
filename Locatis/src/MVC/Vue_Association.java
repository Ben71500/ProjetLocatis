/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Benjamin
 */
public class Vue_Association extends JFrame{
    
    private JPanel panneau = new JPanel();
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    
    private JComboBox logementList = new JComboBox();
    private JComboBox locataireList = new JComboBox();
    
    public Vue_Association(){
        this.centre.add(logementList);
        this.centre.add(locataireList);
        this.pack();
    }
    
}
