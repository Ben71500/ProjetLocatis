package interfaceGraphique;

import javax.swing.*;

public class PopupInformation extends JFrame{
    
    public PopupInformation(String texte){
	JOptionPane.showMessageDialog(this,texte);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }   
}