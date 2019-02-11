package org.springframework.web.method.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Parameter;

public interface HandlerMethodArgumentResolver {

    boolean supports(Parameter parameter);

    Object resolveArgument(Parameter parameter, HttpServletRequest request, HttpServletResponse response);
}
