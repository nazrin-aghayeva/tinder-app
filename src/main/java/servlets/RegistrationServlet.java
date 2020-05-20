package servlets;

import dao.DAOUserSql;
import entities.User;
import services.CookiesService;
import services.UserService;
import utils.Freemarker;
import utils.ParameterFromRequest;

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
    private UserService usersService;
    private final Connection connection;

    public RegistrationServlet(Connection connection) {
        this.connection = connection;
        this.usersService = new UserService(new DAOUserSql(connection));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> data = new HashMap<>();

        List<String> fields = new ArrayList<>();

        fields.add("Name");
        fields.add("Surname");
        fields.add("Image");
        fields.add("Position");
        fields.add("Email");

        data.put("fields", fields);
        data.put("message", "Please sign up");
        data.put("rout", "/reg");

        f.render("form.ftl", data, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ParameterFromRequest pfr = new ParameterFromRequest(req);

        cookiesService = new CookiesService(req,resp);
        String name = pfr.getStr("Name");
        String surname = pfr.getStr("Surname");
        String image = pfr.getStr("Image");
        String position = pfr.getStr("Position");
        String email = pfr.getStr("Email");
        String password = pfr.getStr("Password");

        User user = new User(email,password,name,surname,image,position);
        usersService.addUser(user);

        cookiesService.addCookie(usersService.getUserId(user));

        resp.sendRedirect("/login");
    }
}
