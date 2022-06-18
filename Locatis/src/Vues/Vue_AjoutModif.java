package Vues;

import Exceptions.EmptyFieldException;
import Exceptions.PasDeLignesSelectionneesException;
import Exceptions.ValeurIncorrecteException;
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