package org.tinder.app.dao;

import org.tinder.app.entities.Like;
import org.tinder.app.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOUsersSql implements DAO<User> {

    private Connection connection;
    private int userId;

    public DAOUsersSql(Connection connection) {
        this.connection = connection;
    }

    public User getActiveUser(int activeUserId) {
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
                String gender = rSet.getString("gender");

                result = new User(id, login, password, name, surname, imgUrl,position,gender);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;

    }

    public void add(User user) {
        String sql = "INSERT INTO users(login, password, name, surname,imgUrl,position,gender) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, user.getLogin());
            stm.setString(2, user.getPassword());
            stm.setString(3, user.getName());
            stm.setString(4, user.getSurname());
            stm.setString(5, user.getImgUrl());
            stm.setString(6, user.getPosition());
            stm.setString(7, user.getGender());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                String gender = rSet.getString("gender");

                result = new User(id, login, password, name, surname, imgUrl,position,gender);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public User get(int id) {
        User user = null;
        String sql = "SELECT users.name, users.surname,users.login,users.imgUrl,users.position,users.gender FROM users WHERE id = ?";
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
                String gender = rSet.getString("gender");

                user = new User(id,  name, surname,login, imgUrl,position,gender);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;

    }

    public void remove(int id) {
        throw new IllegalStateException("Shouldn't be implemented");
    }


    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT users.name, users.surname,users.login,users.imgUrl,users.position,users.gender FROM users WHERE id = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,userId);
            ResultSet rSet = stm.executeQuery();

            while (rSet.next()) {
                int id = rSet.getInt("id");
                String name = rSet.getString("name");
                String surname = rSet.getString("surname");
                String login = rSet.getString("login");
                String imgUrl = rSet.getString("imgUrl");
                String position = rSet.getString("position");
                String gender = rSet.getString("gender");
                User user = new User(id,name,surname,login,imgUrl,position,gender);
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}