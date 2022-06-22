package Vues;

import Exceptions.EmptyFieldException;
import Exceptions.PasDeLignesSelectionneesException;
import Exceptions.ValeurIncorrecteException;
import java.awt.event.ActionListener;

/**
 * Interface qui décrit les vues permettant d'ajouter ou de modifier un élément
 * @author Benjamin Mathilde
 */
public interface Vue_AjoutModif {
    
    /**
     * Méthode qui permet de rendre la vue visible
     */
    public void afficherVue();
    
    /**
     * Méthode qui permet d'ajouter des écouteurs à un bouton
     * @param nomBouton : nom du bouton
     * @param listener : écouteur
     */
    public void ajouterEcouteurBouton(String nomBouton, ActionListener listener);
    
    /**
     * Méthode qui permet de vérifier que tous les champs sont bien saisis et valides
     * @throws EmptyFieldException : exception levée lorsqu'un champ est vide
     * @throws PasDeLignesSelectionneesException : exception levée lorsqu'aucune ligne n'est sélectionnée
     * @throws ValeurIncorrecteException : : exception levée lorsqu'une valeur incorrecte est saisie
     */
    public void verifierChamps() throws EmptyFieldException, PasDeLignesSelectionneesException, ValeurIncorrecteException;
    
    /**
     * Méthode qui permet de réinitialiser tous les champs
     */
    public void reset();
    
    /**
     * Méthode qui permet de fermer la fenêtre
     */
    public void quitter();
    
    /**
     * Méthode qui retourne l'objet à ajouter
     * @return l'objet à ajouter
     */
    public Object getNouvelObjet();
    
    /**
     * Méthode qui retourne l'objet à modifier
     * @return l'objet à modifier
     */
    public Object getObjetModifie();
}