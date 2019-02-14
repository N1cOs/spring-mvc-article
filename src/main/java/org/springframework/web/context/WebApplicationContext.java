package org.springframework.web.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.method.annotation.HttpRequestMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.HandlerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WebApplicationContext {

    private BeanFactory beanFactory;

    private String basePackage;

    public WebApplicationContext(ClassLoader classLoader, String basePackage){
        beanFactory = new BeanFactory(classLoader);
        this.basePackage = basePackage;
    }

    public void init(){
        beanFactory.instantiate(basePackage);
    }

    public HandlerMapping initHandlerMapping(){
        HandlerMapping handlerMapping = new HandlerMapping();
        for (Object bean : beanFactory.getBeans().values()) {
            Class<?> beanType = bean.getClass();
            if(handlerMapping.isHandler(beanType))
                handlerMapping.detectHandlerMethods(bean);
        }
        return handlerMapping;
    }

    public HandlerAdapter initHandlerAdapter(){
        return new HandlerAdapter(initArgumentResolvers());
    }

    private List<HandlerMethodArgumentResolver> initArgumentResolvers(){
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new HttpRequestMethodArgumentResolver());

        return resolvers;
    }
}
