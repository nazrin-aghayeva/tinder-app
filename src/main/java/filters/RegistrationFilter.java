package filters;

import dao.DAOUserSql;
import entities.User;
import org.eclipse.jetty.http.HttpMethod;
import services.UserService;
import utils.Freemarker;
import utils.ParameterFromRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

public class RegistrationFilter implements Filter {
    private UserService usersService;
    private Freemarker f = new Freemarker();
    private final Connection connection;

    public RegistrationFilter(Connection connection) {
        this.connection = connection;
        this.usersService = new UserService(new DAOUserSql(connection));
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

                String email = pfr.getStr("Email");
                String password = pfr.getStr("Password");
                User user = new User(email, password);

                if (usersService.loggedUser(user)) {
                    throw new Exception("Such user exists");
                }
                chain.doFilter(request, response);
            } catch (Exception e) {
                data.put("message", e.getMessage());
                data.put("rout","/reg");
                f.render("fail.ftl", data,(HttpServletResponse) response);
            }
        } else {
            chain.doFilter(request, response);
        }

    }


    @Override
    public void destroy() {

    }}
