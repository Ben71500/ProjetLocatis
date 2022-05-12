package MVC;

import Locatis.Message;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;

public class Panneau_Message extends JPanel{
    
    private JPanel haut = new JPanel();
    private JPanel centre = new JPanel();
    
    private JLabel objet_label = new JLabel ("Objet : ");
    private JLabel contenu_label = new JLabel ("Texte : ");
    
    private JTextField objet = new JTextField();
    private JTextArea contenu = new JTextArea();
    
    private Message message;
    
    public Panneau_Message(){
        
        this.setLayout(new BorderLayout());
        this.add(this.haut, BorderLayout.NORTH);
        this.add(this.centre, BorderLayout.CENTER);
        
        haut.setLayout(new GridLayout(1,2));
        haut.add(objet_label);
        haut.add(objet);
        
        centre.setLayout(new BorderLayout());
        centre.add(this.contenu_label, BorderLayout.NORTH);
        centre.add(this.contenu, BorderLayout.CENTER);
    }   
}