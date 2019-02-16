package org.springframework.web.servlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.HandlerAdapter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DispatcherServlet extends HttpServlet {

    public final static String HANDLER_MAPPING_BEAN_NAME = "handlerMapping";

    public final static String HANDLER_ADAPTER_BEAN_NAME= "handlerAdapter";

    private WebApplicationContext webApplicationContext;

    private HandlerMapping handlerMapping;

    private HandlerAdapter handlerAdapter;

    public DispatcherServlet(WebApplicationContext context){
        webApplicationContext = context;
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HandlerMethod handler = handlerMapping.getHandler(request);
        if(handler == null)
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        else
            handlerAdapter.invokeHandlerMethod(request, response, handler);
    }

    @Override
    public void init() {
        webApplicationContext.init();
        handlerMapping =
                (HandlerMapping) webApplicationContext.getBean(HANDLER_MAPPING_BEAN_NAME);
        handlerAdapter =
                (HandlerAdapter) webApplicationContext.getBean(HANDLER_ADAPTER_BEAN_NAME);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doDispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doDispatch(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doDispatch(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doDispatch(req, resp);
    }
}
