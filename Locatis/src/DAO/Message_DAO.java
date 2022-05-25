package DAO;

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
            return !statement.execute("insert into message (ID_message, Objet, Contenu) values("
                    + obj.getId() + " , '"
                    + obj.getObjet() + "' , '"
                    + obj.getMessage() + "')"
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
                    + "Objet='" + obj.getObjet()+ "' , "
                    + "Contenu='" + obj.getMessage()+ "' , "
                    + " where ID_message=" + obj.getId()
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
                    res.getString("Objet"),
                    res.getString("Contenu")
            );
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public Message selectByName(String message) {
        try {
            Statement statement = this.connection.createStatement();
            ResultSet res = statement.executeQuery("Select * from message where Objet='" + message + "'");
            res.next();

            return new Message(res.getInt("ID_message"), res.getString("Objet"), res.getString("Contenu"));

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
                        res.getString("Objet"),
                        res.getString("Contenu")
                ));
            }
        } catch (SQLException ex) {
            return allMessages;
        }
        return allMessages;
    }    
}