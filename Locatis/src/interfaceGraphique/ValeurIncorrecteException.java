package interfaceGraphique;

public class ValeurIncorrecteException extends Exception{
    
    public ValeurIncorrecteException(String message) {
        super(message);
    }
    
    public void afficherErreur(){
        new PopupInformation("Veuillez saisir "+this.getMessage()+" correcte");
    }   
}