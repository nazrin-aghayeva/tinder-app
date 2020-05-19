package dao;

import entities.Message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOMessages implements DAO<Message> {

    private Connection connection;
    private int senderId;


    public DAOMessages(Connection connection, int senderId) {
        this.connection = connection;
        this.senderId = senderId;
    }

    public DAOMessages(Connection connection) {
        this.connection = connection;
    }

    public void add(Message message) {
        try {
            java.lang.String sql = "INSERT INTO messages(senderId, receiverId, text) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getReceiverId());
            preparedStatement.setString(3, message.getMessageText());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new IllegalStateException("Something went wrong");
        }
    }

    public void remove(int id) {
        throw new IllegalStateException("Method is not supplied by this implementation");
    }

    public Message get(int messageId) {
        try {
            java.lang.String sql = "SELECT * FROM messages WHERE messageId = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, messageId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Message(resultSet.getInt("messageId"), resultSet.getInt("senderId"), resultSet.getInt("receiverId"), resultSet.getString("messageText"), resultSet.getTimestamp("time"));
            }
            return null;

        } catch (SQLException e) {
            throw new IllegalStateException("Something went wrong");
        }
    }

    public List<Message> getAll() {
        try {
            java.lang.String sql = "SELECT * FROM messages WHERE senderId = ? OR receiverId = ? ORDER BY time";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, senderId);
            statement.setInt(2, senderId);
            ResultSet resultSet = statement.executeQuery();
            List<Message> resultingMessagesList = new ArrayList<Message>();
            while (resultSet.next()) {
                String string = resultSet.getInt("receiverId") == senderId ? "received" : "sent";
                resultingMessagesList.add(new Message(resultSet.getInt("messageId"), resultSet.getInt("senderId"), resultSet.getInt("receiverId"), resultSet.getString("messageText"), string, resultSet.getTimestamp("time")));
            }

            return resultingMessagesList;


        } catch (SQLException e) {
            throw new IllegalStateException("Smth went wrong");
        }
    }
}
