package Locatis;

public class Message {
    private int id;
    private String message;
    private MyDate dateEcriture;
    
    public Message(int unID, String unMessage, MyDate uneDateEcriture){
        this.id = unID;
        this.message = unMessage;
        this.dateEcriture = uneDateEcriture;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public MyDate getDateEcriture() {
        return dateEcriture;
    }
}