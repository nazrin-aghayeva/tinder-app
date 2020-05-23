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

public class RegistrationServlet extends HttpServlet {
    private CookiesService cookiesService;
    private final Freemarker f = new Freemarker();
    private UsersService usersService;
    private final Connection connection;

    public RegistrationServlet(Connection connection) {
        this.connection = connection;
        this.usersService = new UsersService(new DAOUsersSql(connection));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> data = new HashMap<>();

        List<String> fields = new ArrayList<>();

        fields.add("Name");
        fields.add("Surname");
        fields.add("Image");
        fields.add("Email");
        fields.add("Position");
        fields.add("Gender");

        data.put("fields", fields);
        data.put("message", "Please Sign up");
        data.put("rout", "/reg");

        f.render("login.ftl", data, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ParameterFromRequest pfr = new ParameterFromRequest(req);

        cookiesService = new CookiesService(req,resp);
        String name = pfr.getStr("Name");
        String surname = pfr.getStr("Surname");
        String image = pfr.getStr("Image");
        String login = pfr.getStr("Email");
        String password = pfr.getStr("Password");
        String position = pfr.getStr("Position");
        String gender = pfr.getStr("Gender");


        User user = new User(login,password,name,surname,image,position,gender);
        usersService.add(user);

        cookiesService.addCookie(usersService.getUserId(user));

        resp.sendRedirect("/login");
    }
}
