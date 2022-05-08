package interfaceGraphique;

public class PasDeCaseCocheeException extends Exception{
    
    public PasDeCaseCocheeException() {
        super("Pas de case cochée");
    }
    
    public void afficherErreur(){
        new PopupInformation("Veuillez cocher au moins un locataire ");
    }
}