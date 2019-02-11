package org.springframework.web.servlet.mvc.method.annotation;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class HandlerAdapter {

    private List<HandlerMethodArgumentResolver> argumentResolvers;

    public HandlerAdapter(List<HandlerMethodArgumentResolver> argumentResolvers) {
        this.argumentResolvers = argumentResolvers;
    }

    public void invokeHandlerMethod(HttpServletRequest request,
                                    HttpServletResponse response, HandlerMethod handlerMethod){
        handlerMethod.setResolvers(argumentResolvers);
        handlerMethod.invokeAndHandle(request, response);
    }

}
