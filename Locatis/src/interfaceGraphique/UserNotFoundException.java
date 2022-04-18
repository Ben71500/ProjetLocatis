package interfaceGraphique;

public class UserNotFoundException extends Exception{
    
    public UserNotFoundException() {
        super("");
    }
    
    public void afficherErreur(){
        //new PopupInformation("Veuillez sélectionner "+this.getMessage());
    }
}
