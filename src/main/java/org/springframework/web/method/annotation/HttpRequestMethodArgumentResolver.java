package org.springframework.web.method.annotation;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class HttpRequestMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(Parameter parameter) {
        return HttpServletRequest.class.isAssignableFrom(parameter.getType());
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
        return request;
    }
}
