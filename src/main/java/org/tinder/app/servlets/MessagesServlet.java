package org.tinder.app.servlets;

import org.tinder.app.services.CookiesService;
import org.tinder.app.services.MessagesService;
import org.tinder.app.utils.ParameterFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class MessagesServlet extends HttpServlet {

    private Connection connection;
    private CookiesService cookiesService;

    public MessagesServlet(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cookiesService = new CookiesService(req, resp);
        int userId = Integer.parseInt(cookiesService.getCookie().getValue());
        ParameterFromRequest pfr = new ParameterFromRequest(req);
        int counterpartId = pfr.getInt("user");
        MessagesService messagesService = new MessagesService(userId, counterpartId, connection, req, resp);
        messagesService.generateLikedPage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cookiesService = new CookiesService(req, resp);
        int userId = Integer.parseInt(cookiesService.getCookie().getValue());
        ParameterFromRequest pfr = new ParameterFromRequest(req);
        int counterpartId = pfr.getInt("user");

        MessagesService messagesService = new MessagesService(userId, counterpartId, connection, req, resp);
        try {
            String text = pfr.getStr("text");
            messagesService.sendMessage(text);
        } catch (IllegalStateException e) {
            System.out.println("empty message");
        } finally {
            messagesService.generateLikedPage();
        }

    }
}
