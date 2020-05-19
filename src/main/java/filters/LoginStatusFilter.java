package filters;

import services.CookiesService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class LoginStatusFilter implements Filter {

    private CookiesService cookiesService;
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
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        cookiesService = new CookiesService(req, resp);
        if(cookiesService.getCookie() == null && !req.getRequestURI().matches("(/login|/reg)")){
            resp.sendRedirect("/login");
        } else{
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
