package org.tinder.app.filters;

import org.tinder.app.services.CookiesService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StatusFilter implements Filter {
    private CookiesService cookiesService;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req;
        if (request instanceof HttpServletRequest) {
            req = (HttpServletRequest) request;
        } else {
            throw new IllegalArgumentException("ServletRequest should be instance of HttpServletRequest");
        }

        HttpServletResponse resp = (HttpServletResponse) response;
        cookiesService = new CookiesService(req, resp);
        if(cookiesService.getCookie() == null && !req.getRequestURI().matches("(/login|/reg)")){
            resp.sendRedirect("/login");
        } else{
            chain.doFilter(request,response);
        }


    }

    public void destroy() {

    }
}
