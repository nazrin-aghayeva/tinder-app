package dao;

import java.util.List;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOUserSql implements DAO<User> {

    Connection connection;

    public DAOUserSql(Connection connection) {
        this.connection = connection;
    }

    public User getByLogin(User user){
        User selected= null;
        String SQL= "SELECT * FROM users WHERE login=?";
        try{
            PreparedStatement stm= connection.prepareStatement(SQL);
            stm.setString(1, user.getEmail());
            ResultSet result= stm.executeQuery();

            if (result.next()){
                int user_id= result.getInt("user_id");
                String name= result.getString("name");
                String surname= result.getString("surname");
                String password= result.getString("password");
                String email= result.getString("email");
                String position= result.getString("position");
                String photo_link= result.getString("photo_link");
                selected= new User(user_id,name,surname,password,email,position,photo_link);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selected;
    }

    public User getActiveUser(int activeUserId){
        User user=null;
        String SQL= "SELECT DISTINCT u.* FROM users u WHERE u.id!= ?" +
                " u.id NOT in (SELECT likedUserId FROM likes WHERE likedUserId=u.id AND userId= ? n" +
                ") LIMIT 1";
        try{
            PreparedStatement stm= connection.prepareStatement(SQL);
            stm.setInt(1,activeUserId);
            ResultSet resultSet=stm.executeQuery();

            if (resultSet.next()){
               int id= resultSet.getInt("user_id");
                String name=resultSet.getString("name");
                String surname=resultSet.getString("surname");
                String password=resultSet.getString("password");
                String email= resultSet.getString("email");
                String position=resultSet.getString("position");
                String photo_link=resultSet.getString("photo_link");
                user= new User(id,name,surname,password,email,position,photo_link);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void add(User user) {
        String SQL= "INSERT INTO users(name, surname, password, email,position,photo_link) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement statement= connection.prepareStatement(SQL);
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoto_link());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User get(int user_id) {
        User user= null;
        String SQL= "SELECT u.name, u.surname, u.email, u.position, u.photo_link FROM users u WHERE user_id=?";
        try {
            PreparedStatement stm = connection.prepareStatement(SQL);
            stm.setInt(1,user_id);
            ResultSet result= stm.executeQuery();

            if (result.next()){
                String name=result.getString("name");
                String surname= result.getString("surname");
                String email= result.getString("email");
                String position= result.getString("position");
                String photo_link= result.getString("photo_link");
                user= new User(user_id,name,surname,email,position,photo_link);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void remove(int id) {
        throw new RuntimeException("isn't supported");
    }


    @Override
    public List<User> getAll() {
        throw new RuntimeException("isn't supported");
    }
}
