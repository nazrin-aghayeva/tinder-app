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

public class LoginFilter implements Filter {
    private UserService userService;
    private Freemarker fmr= new Freemarker();
    private Connection connection;

    public LoginFilter(Connection connection) {
        this.connection = connection;
        this.userService= new UserService(new DAOUserSql(connection));
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req;
        if (servletRequest instanceof HttpServletRequest){
            req= (HttpServletRequest) servletRequest;
        }
        else{
            throw new IllegalArgumentException("ServletRequest should be instance of HttpServletRequest");
        }

        HashMap<String, Object> data= new HashMap<>();

        if (HttpMethod.POST.name().equalsIgnoreCase(req.getMethod())) {
            try{
            ParameterFromRequest pfm = new ParameterFromRequest(req);

            String email = pfm.getStr("Email");
            String password = pfm.getStr("Password");
            User user = new User(email, password);


            if (!userService.checkUser(user)){
                throw new Exception("Incorrect email or password");
            }
                filterChain.doFilter(servletRequest, servletResponse);

            }
            catch (Exception ex){
                data.put("message", ex.getMessage());
                data.put("rout", "/login");
                fmr.render("fail.ftl",data, (HttpServletResponse) servletResponse);
            }
            }
        else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        }

    @Override
    public void destroy() {

    }
}
