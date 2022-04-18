package interfaceGraphique;

import javax.swing.*;

public class PopupSupprimer extends JFrame{
    
    private int choix;
    
    public PopupSupprimer(){
        
        this.choix = JOptionPane.showConfirmDialog(this,"Confirmer la suppression ?","Supprimer", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public boolean getConfirmation(){
        return this.choix==0;
    }
}