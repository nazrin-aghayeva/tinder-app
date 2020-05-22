package org.tinder.app.servlets;

import org.tinder.app.dao.DAOUsersSql;
import org.tinder.app.entities.User;
import org.tinder.app.utils.Freemarker;
import org.tinder.app.services.CookiesService;
import org.tinder.app.services.UsersService;
import org.tinder.app.utils.ParameterFromRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoginServlet extends HttpServlet {
    private CookiesService cookiesService;
    private final Freemarker f = new Freemarker();
    private UsersService usersService;
    private final Connection connection;

    public LoginServlet(Connection connection) {
        this.connection = connection;
        this.usersService = new UsersService(new DAOUsersSql(connection));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> data = new HashMap<>();

        List<String> fields = new ArrayList<>();
        fields.add("Email");

        data.put("fields", fields);
        data.put("message", "Please Sign in");
        data.put("rout", "/login");

        f.render("login.ftl", data, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ParameterFromRequest pfr = new ParameterFromRequest(req);

        cookiesService = new CookiesService(req,resp);
        String login = pfr.getStr("Email");
        String password = pfr.getStr("Password");

        User user = new User(login,password);

        cookiesService.addCookie(usersService.getUserId(user));

        resp.sendRedirect("/users");
    }
}
