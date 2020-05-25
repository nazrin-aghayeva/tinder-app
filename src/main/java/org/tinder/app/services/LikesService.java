package org.tinder.app.services;

import org.tinder.app.dao.DAOLikesSql;
import org.tinder.app.dao.DAOUsersSql;
import org.tinder.app.entities.Like;
import org.tinder.app.entities.User;
import org.tinder.app.utils.Freemarker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LikesService {

    private int userId;
    private Connection connection;
    private DAOLikesSql likeDao;
    private DAOUsersSql daoUsers;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Freemarker freemarker = new Freemarker();

    public LikesService(DAOLikesSql likeDao, DAOUsersSql daoUsers) {
        this.likeDao = likeDao;
        this.daoUsers = daoUsers;
    }

    public LikesService(int userId, Connection connection, HttpServletRequest request, HttpServletResponse response) {
        this.userId = userId;
        this.connection = connection;
        this.request = request;
        this.response = response;
        this.likeDao = new DAOLikesSql(userId, connection);
        this.daoUsers = new DAOUsersSql(connection);
    }

    public void generateLikedPage() {
        Map<String, Object> input = new HashMap<>();
        input.put("messages", 0);
        input.put("users", getLikedUsersList(likeDao.getAll()));
        freemarker.render("people-list.ftl", input, response);
    }


    private List<User> getLikedUsersList(List<Like> likes) {
        return likes.stream().map(e -> daoUsers.get(e.getLikedUserId())).collect(Collectors.toList());
    }


    public void addLike(Like like){
        if(likeDao.get(like.getLikedUserId()) == null){
            likeDao.add(like);
        }
    }

    public void removeLike(Like like){
        if(likeDao.get(like.getLikedUserId()) != null){
            likeDao.remove(like.getLikedUserId());
        }
    }

    public User getActiveUser(int activeUserId){
        User userToShow = daoUsers.getActiveUser(activeUserId);
        if(userToShow == null){
            likeDao.clearCheckedTable();
            return getActiveUser(activeUserId);
        }
        return userToShow;

    }

    public void addCheckedStatus(int dislikedUserId){
        likeDao.addCheckedStatus(dislikedUserId);
    }


}
