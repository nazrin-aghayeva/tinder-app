package org.tinder.app.dao;

import org.tinder.app.entities.Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAOLikesSql implements DAO<Like> {

    private int userId;
    private Connection connection;


    public DAOLikesSql(int userId, Connection connection) {
        this.userId = userId;
        this.connection = connection;
    }


    public void addCheckedStatus(int checkedUserId){
        try {
            String sql = "INSERT INTO checked(userId,checked) VALUES (?,?)";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, checkedUserId);
            stm.execute();
        } catch (SQLException e) {
            System.out.println("duplicate entry");
        }
    }

    public void clearCheckedTable(){
        try {
            String sql = "DELETE FROM checked WHERE userId = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Like item) {
        String sql = "INSERT INTO likes(userId,liked) VALUES (?,?)";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, item.getLikedUserId());
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remove(int id) {
        String sql = "DELETE FROM likes WHERE userId = ? AND liked = ?";

        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, userId);
            stm.setInt(2, id);
            stm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Like get(int id) {
        Like like = null;
        String sql = "SELECT * FROM likes WHERE userId = ? AND liked = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,userId);
            stm.setInt(2,id);
            ResultSet rSet = stm.executeQuery();

            if (rSet.next()) {
                int userId = rSet.getInt("userId");
                int likedUserId = rSet.getInt("liked");
                like = new Like(userId,likedUserId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return like;
    }

    public List<Like> getAll() {
        List<Like> likes = new ArrayList<>();
        String sql = "SELECT * FROM likes WHERE userId = ?";
        try {
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1,userId);
            ResultSet rSet = stm.executeQuery();

            while (rSet.next()) {
                int userId = rSet.getInt("userId");
                int likedUserId = rSet.getInt("liked");
                Like like = new Like(userId,likedUserId);
                likes.add(like);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }
}
