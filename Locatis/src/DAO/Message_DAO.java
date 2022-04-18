package DAO;

import DAO.DAO;
import Locatis.Message;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Message_DAO extends DAO<Message>{
    
    public Message_DAO(Connection connection) {
        super(connection);
    }

    @Override
    public boolean create(Message obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("insert into message (ID_message, Contenu, Date_Ecriture) values("
                    + obj.getId() + " , '"
                    + obj.getMessage() + "' , "
                    + obj.getDateEcriture().getDateSQL() + ")"
            );
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean delete(Message obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("delete from message where ID_message=" + obj.getId());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean update(Message obj) {
        try {
            Statement statement = this.connection.createStatement();
            return !statement.execute("update message set "
                    + "Contenu='" + obj.getMessage()+ "' , "
                    + "Date_Ecriture=" + obj.getDateEcriture().getDateSQL()
                    + " where  ID_message=" + obj.getId()
            );
        } catch (SQLException ex) {
            return false;
        }
        
    }

    @Override
    public Message selectById(int id) {

        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from message where ID_message=" + id);
            res.next();
            return new Message(res.getInt("ID_message"),
                    res.getString("Contenu"),
                    this.getMyDate(res.getDate("Date_Ecriture"))
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Message selectByName(String message) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from message where Contenu='" + message + "'");
            res.next();

            return new Message(res.getInt("ID_message"), res.getString("Contenu"), this.getMyDate(res.getDate("Date_Ecriture")));

        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public List<Message> getAll() {

        List<Message> allMessages = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from message");
            while (res.next()) {
                allMessages.add(new Message(
                        res.getInt("ID_message"),
                        res.getString("Contenu"),
                        this.getMyDate(res.getDate("Date_Ecriture"))
                ));
            }
        } catch (SQLException ex) {
            return allMessages;
        }
        return allMessages;
    }    
}

