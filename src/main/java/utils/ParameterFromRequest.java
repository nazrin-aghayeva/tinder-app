package utils;

import javax.servlet.http.HttpServletRequest;

public class ParameterFromRequest {
    private final HttpServletRequest req;

    public ParameterFromRequest(HttpServletRequest req) {
        this.req = req;
    }

    public int getInt(String name) {
        if(req.getParameter(name) == null || req.getParameter(name).equals("")){
            throw new IllegalStateException(String.format("Parameter %s missing",name));
        }
        return Integer.parseInt(req.getParameter(name));
    }

    public String getStr(String name) {
        if(req.getParameter(name) == null || req.getParameter(name).equals("")){
            throw new IllegalStateException(String.format("Parameter %s missing",name));
        }
        return req.getParameter(name);

    }
}