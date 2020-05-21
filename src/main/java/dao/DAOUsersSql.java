package dao;

import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DAOUsersSql implements DAO<User> {

    private Connection connection;

    public DAOUsersSql(Connection connection) {
        this.connection = connection;
    }

    public User getUserToShow(int activeUserId) {
        User result = null;

        String sql = "SELECT * FROM users WHERE id != ? AND id NOT IN (\n" +
                "    SELECT checked FROM checked WHERE checked = id AND userId = ? \n" +
                ") LIMIT 1";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, activeUserId);
            stm.setInt(2, activeUserId);
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                String name = rSet.getString("name");
                int id = rSet.getInt("id");
                String surname = rSet.getString("surname");
                String login = rSet.getString("login");
                String password = rSet.getString("password");
                String imgUrl = rSet.getString("imgUrl");
                String position = rSet.getString("position");

                result = new User(id, login, password, name, surname, imgUrl,position);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public void add(User user) {
        String sql = "INSERT INTO users(login, password, name, surname,imgUrl,position) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getName());
            stm.setString(4, user.getSurname());
            stm.setString(5, user.getImgUrl());
            stm.setString(6, user.getPosition());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        throw new IllegalStateException("Shouldn't be implemented");
    }

    public User getByLogin(User user) {
        User result = null;
        String sql = "SELECT * FROM users WHERE login = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getLogin());
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                String name = rSet.getString("name");
                String surname = rSet.getString("surname");
                int id = rSet.getInt("id");
                String login = rSet.getString("login");
                String password = rSet.getString("password");
                String imgUrl = rSet.getString("imgUrl");
                String position = rSet.getString("position");

                result = new User(id, login, password, name, surname, imgUrl,position);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public User get(int id) {
        User user = null;
        String sql = "SELECT users.name, users.surname,users.login,users.imgUrl,users.position FROM users WHERE id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                String name = rSet.getString("name");
                String surname = rSet.getString("surname");
                String login = rSet.getString("login");
                String imgUrl = rSet.getString("imgUrl");
                String position = rSet.getString("position");

                user = new User(id, login, name, surname, imgUrl,position);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }

    @Override
    public List<User> getAll() {
        throw new IllegalStateException("Shouldn't be implemented");
    }
}