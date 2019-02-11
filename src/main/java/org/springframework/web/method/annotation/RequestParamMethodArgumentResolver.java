package org.springframework.web.method.annotation;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public class RequestParamMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supports(Parameter parameter) {
        RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
        return requestParam != null && !requestParam.value().isEmpty();
    }

    @Override
    public Object resolveArgument(Parameter parameter, HttpServletRequest request, HttpServletResponse response) {
        String name = parameter.getAnnotation(RequestParam.class).value();
        return request.getParameter(name);
    }
}
