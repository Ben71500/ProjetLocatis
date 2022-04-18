package interfaceGraphique;

import DAO.*;
import Locatis.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Test {
    
    public static void main(String[]args){
        SwingUtilities.invokeLater(new Runnable(){
        public void run(){
                SeConnecter fenetre=new SeConnecter();
                fenetre.setBounds(100, 100, 350, 200);
                fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fenetre.setVisible(true);
            }
        });
        
        /*
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                MenuPrincipal fenetre=new MenuPrincipal();
                fenetre.setBounds(100, 100, 350, 300);
                fenetre.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                fenetre.setVisible(true);
            }
        });*/
        
        /*JOptionPane popup = new JOptionPane();
        int choix = JOptionPane.showConfirmDialog(popup,"Confirmer la suppression ?","Supprimer", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        System.out.println(choix);*/
    }
    
}
