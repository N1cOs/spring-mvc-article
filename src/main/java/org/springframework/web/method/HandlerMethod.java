package org.springframework.web.method;

import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class HandlerMethod {

    private Method method;

    private Object bean;

    private Parameter[] methodParameters;

    private List<HandlerMethodArgumentResolver> resolvers;

    public HandlerMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
        methodParameters = method.getParameters();
    }

    public Object invokeAndHandle(HttpServletRequest request, HttpServletResponse response){
        Object[] args = getMethodArgumentValues(request, response);
        Object result = null;
        try {
            result = method.invoke(bean, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }

    public void setResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        this.resolvers = resolvers;
    }

    private Object[] getMethodArgumentValues(HttpServletRequest request, HttpServletResponse response){
        if(methodParameters.length == 0)
            return new Object[0];

        Object[] args = new Object[methodParameters.length];
        for(int i = 0; i < args.length; i++){
            Parameter parameter = methodParameters[i];
            for(HandlerMethodArgumentResolver argumentResolver : resolvers){
                if(argumentResolver.supports(parameter)){
                    args[i] = argumentResolver.resolveArgument(parameter, request, response);
                    break;
                }
            }
        }
        return args;
    }
}
