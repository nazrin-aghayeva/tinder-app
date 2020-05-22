package org.tinder.app.filters;

import org.tinder.app.dao.DAOUsersSql;
import org.tinder.app.entities.User;
import org.tinder.app.services.UsersService;
import org.tinder.app.utils.Freemarker;
import org.eclipse.jetty.http.HttpMethod;
import org.tinder.app.utils.ParameterFromRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

public class RegistrationFilter implements Filter {
    private UsersService usersService;
    private Freemarker f = new Freemarker();
    private final Connection connection;

    public RegistrationFilter(Connection connection) {
        this.connection = connection;
        this.usersService = new UsersService(new DAOUsersSql(connection));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        HttpServletRequest req;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        } else {
            throw new IllegalArgumentException("ServletRequest should be instance of HttpServletRequest");
        }

        HashMap<String, Object> data = new HashMap<>();

        if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
            try {
                ParameterFromRequest pfr = new ParameterFromRequest(req);

                pfr.getStr("Name");
                pfr.getStr("Surname");
                pfr.getStr("Image");

                String login = pfr.getStr("Email");
                String password = pfr.getStr("Password");
                User user = new User(login, password);

                if (usersService.checkUserByLogin(user)) {
                    throw new Exception("Such user exists");
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                data.put("message", e.getMessage());
                data.put("rout","/reg");
                f.render("noregistr.ftl", data,(HttpServletResponse) response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }


    @Override
    public void destroy() {

    }
}
