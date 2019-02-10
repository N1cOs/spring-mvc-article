package org.springframework.web.method;

import java.lang.reflect.Method;

public class HandlerMethod {

    private Method method;

    private Object bean;

    public HandlerMethod(Method method, Object bean) {
        this.method = method;
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public Object getBean() {
        return bean;
    }
}
