package org.tinder.app.utils;

import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ResourceHandler;

public class ResourcesHandler {

    public ContextHandler generateResourceHandler(String resourceBase, String contextPath) {
        org.eclipse.jetty.server.handler.ResourceHandler authRes = new org.eclipse.jetty.server.handler.ResourceHandler();
        authRes.setResourceBase(resourceBase);
        authRes.setDirectoriesListed(true);
        ContextHandler authResHandler = new ContextHandler(contextPath);
        authResHandler.setHandler(authRes);
        return authResHandler;
    }
}
