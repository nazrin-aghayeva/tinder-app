package services;

import dao.DAO;
import dao.DAOLikesSql;
import dao.DAOMessagesSql;
import dao.DAOUsersSql;
import entities.Like;
import entities.Message;
import entities.User;
import utils.Freemarker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MessagesService {
    private final int userId;
    private final int counterpartId;
    private Connection connection;
    private DAO<Message> messageDAO;
    private DAO<User> userDAO;
    private DAO<Like> likeDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Freemarker freemarker = new Freemarker();

    public MessagesService(int userId, int counterpartId, Connection connection, HttpServletRequest request, HttpServletResponse response) {
        this.userId = userId;
        this.counterpartId = counterpartId;
        this.connection = connection;
        this.messageDAO = new DAOMessagesSql(connection, userId);
        this.userDAO = new DAOUsersSql(connection);
        this.likeDAO = new DAOLikesSql(userId, connection);
        this.request = request;
        this.response = response;
    }

    public void sendMessage(String text) {
        messageDAO.add(new Message(userId, counterpartId, text));
    }


    public void generateLikedPage() {
        Map<String, Object> input = new HashMap<>();
        input.put("messages", 1);
        input.put("counterpart", userDAO.get(counterpartId));
        input.put("messageList", getFilteredMessages());
        input.put("users", getLikedUsersList(likeDAO.getAll()));
        freemarker.render("people-list.ftl", input, response);
    }


    private List<Message> getFilteredMessages() {
        return messageDAO.getAll().stream().filter(e -> e.getSenderId() == counterpartId || e.getReceiverId() == counterpartId).collect(Collectors.toList());
    }

    private List<User> getLikedUsersList(List<Like> likes) {
        return likes.stream().map(e -> userDAO.get(e.getLikedUserId())).collect(Collectors.toList());
    }
}
