package org.springframework.web.servlet;

import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class DispatcherServlet extends HttpServlet {

    private WebApplicationContext webApplicationContext;

    private HandlerMapping handlerMapping;

    public DispatcherServlet(WebApplicationContext context){
        webApplicationContext = context;
    }

    @Override
    public final void init() throws ServletException {
        webApplicationContext.init();
        handlerMapping = webApplicationContext.initHandlers();
    }
}
