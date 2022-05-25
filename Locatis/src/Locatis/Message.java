package Locatis;

public class Message {
    private int id;
    private String objet;
    private String message;
    
    public Message(int unID, String unObjet, String unMessage){
        this.id = unID;
        this.objet = unObjet;
        this.message = unMessage;
    }

    public int getId() {
        return id;
    }

    public String getObjet() {
        return objet;
    }

    public String getMessage() {
        return message;
    }
}