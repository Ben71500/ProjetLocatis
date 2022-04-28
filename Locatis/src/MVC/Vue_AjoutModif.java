package MVC;

import interfaceGraphique.EmptyFieldException;
import interfaceGraphique.PasDeLignesSelectionneesException;
import java.awt.event.ActionListener;

public interface Vue_AjoutModif {
    
    public void afficherVue();
    
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener);
    
    public void verifierChamps() throws EmptyFieldException, PasDeLignesSelectionneesException;
    
    public void reset();
    
    public void quitter();
    
    public Object getNouvelObjet();
    
    public Object getObjetModifie();
}