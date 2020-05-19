package services;

import dao.DAO;
import dao.DAOLikes;
import dao.DAOMessages;
import dao.DAOUserSql;
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
    private final int otherSideId;
    private Connection connection;
    private DAO<Message> messageDao;
    private DAO<User> userDao;
    private DAO<Like> likeDao;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Freemarker freemarker = new Freemarker();

    public MessagesService(int userId, int otherSideId, Connection connection, HttpServletRequest request, HttpServletResponse response) {
        this.userId = userId;
        this.otherSideId = otherSideId;
        this.connection = connection;
        this.messageDao = new DAOMessages(connection, userId);
        this.userDao = new DAOUserSql(connection);
        this.likeDao = new DAOLikes(userId, connection);
        this.request = request;
        this.response = response;
    }

    public void sendMessage(String text) {
        messageDao.add(new Message(userId, otherSideId, text));
    }


    public void generateLikedPage() {
        Map<String, Object> input = new HashMap<>();
        input.put("messages", 1);
        input.put("otherSideId", userDao.get(otherSideId));
        input.put("messageList", getFilteredMessages());
        input.put("users", getLikedUsersList(likeDao.getAll()));
        freemarker.render("people-list.ftl", input, response);
    }


    private List<Message> getFilteredMessages() {
        return messageDao.getAll()
                .stream()
                .filter(e -> e.getSenderId() == otherSideId || e.getReceiverId() == otherSideId)
                .collect(Collectors.toList());
    }

    private List<User> getLikedUsersList(List<Like> likes) {
        return likes.
                stream().
                map(e -> userDao.get(e.getLikedUserId()))
                .collect(Collectors.toList());
    }
}
