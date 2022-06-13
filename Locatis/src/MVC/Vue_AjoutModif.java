package MVC;

import interfaceGraphique.EmptyFieldException;
import interfaceGraphique.PasDeLignesSelectionneesException;
import interfaceGraphique.ValeurIncorrecteException;
import java.awt.event.ActionListener;

public interface Vue_AjoutModif {
    
    public void afficherVue();
    
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener);
    
    public void verifierChamps() throws EmptyFieldException, PasDeLignesSelectionneesException, ValeurIncorrecteException;
    
    public void reset();
    
    public void quitter();
    
    public Object getNouvelObjet();
    
    public Object getObjetModifie();
}