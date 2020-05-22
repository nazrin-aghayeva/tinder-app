package org.tinder.app.servlets;

import org.tinder.app.services.CookiesService;
import org.tinder.app.services.LikesService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;

public class LikesServlet extends HttpServlet {

    private Connection connection;
    private CookiesService cookiesService;

    public LikesServlet(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cookiesService = new CookiesService(req,resp);
        int userId = Integer.parseInt(cookiesService.getCookie().getValue());
        LikesService likesService = new LikesService(userId, connection, req, resp);
        likesService.generateLikedPage();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
