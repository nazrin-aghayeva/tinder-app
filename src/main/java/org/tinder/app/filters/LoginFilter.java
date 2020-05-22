package org.tinder.app.filters;

import org.tinder.app.dao.DAOUsersSql;
import org.tinder.app.entities.User;
import org.tinder.app.services.UsersService;
import org.tinder.app.utils.Freemarker;
import org.tinder.app.utils.ParameterFromRequest;
import org.eclipse.jetty.http.HttpMethod;

import javax.servlet.*;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.util.HashMap;
public class LoginFilter  implements Filter {

    private UsersService usersService;
    private Freemarker f = new Freemarker();
    private final Connection connection;

    public LoginFilter(Connection connection) {
        this.connection = connection;
        this.usersService = new UsersService(new DAOUsersSql(connection));
    }

    public void init(FilterConfig filterConfig) throws ServletException {

    }

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

                String login = pfr.getStr("Email");
                String password = pfr.getStr("Password");
                User user = new User(login, password);

                if (!usersService.checkUser(user)) {
                    throw new Exception("Incorrect login or password");
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                data.put("message", e.getMessage());
                data.put("rout","/login");
                f.render("noregistr.ftl", data,(HttpServletResponse) response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }

    public void destroy() {

    }
}
