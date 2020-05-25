package org.tinder.app.services;

import org.tinder.app.dao.DAO;
import org.tinder.app.dao.DAOLikesSql;
import org.tinder.app.dao.DAOMessagesSql;
import org.tinder.app.dao.DAOUsersSql;
import org.tinder.app.entities.Like;
import org.tinder.app.entities.Message;
import org.tinder.app.entities.User;
import org.tinder.app.utils.Freemarker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagesService {
    private final int userId;
    private final int otherSideId;
    private Connection connection;
    private DAO<Message> messageDAO;
    private DAO<User> userDAO;
    private DAO<Like> likeDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Freemarker freemarker = new Freemarker();

    public MessagesService(int userId, int otherSideId, Connection connection, HttpServletRequest request, HttpServletResponse response) {
        this.userId = userId;
        this.otherSideId = otherSideId;
        this.connection = connection;
        this.messageDAO = new DAOMessagesSql(connection, userId);
        this.userDAO = new DAOUsersSql(connection);
        this.likeDAO = new DAOLikesSql(userId, connection);
        this.request = request;
        this.response = response;
    }

    public void sendMessage(String text) {
        messageDAO.add(new Message(userId, otherSideId, text));
    }


    public void generateLikedPage() {
        Map<String, Object> input = new HashMap<>();
        input.put("messages", 1);
        input.put("otherSide", userDAO.get(otherSideId));
        input.put("messageList", getFilteredMessages());
        input.put("users", getLikedUsersList(likeDAO.getAll()));
        freemarker.render("people-list.ftl", input, response);
    }


    private List<User> getLikedUsersList(List<Like> likes) {
        return likes
                .stream()
                .map(e -> userDAO.get(e.getLikedUserId()))
                .collect(Collectors.toList());
    }


    private List<Message> getFilteredMessages() {
        return messageDAO.getAll()
                .stream()
                .filter(e -> e.getSenderId() == otherSideId || e.getReceiverId() == otherSideId)
                .collect(Collectors.toList());
    }
}
