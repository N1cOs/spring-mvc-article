package org.springframework.web.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.method.annotation.HttpRequestMethodArgumentResolver;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.HandlerAdapter;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

public class WebApplicationContext {

    public final static String BASE_PACKAGE_ATTRIBUTE = "basePackage";

    private ServletContext servletContext;

    private BeanFactory beanFactory;

    public WebApplicationContext(ServletContext servletContext){
        this.servletContext = servletContext;
        beanFactory = new BeanFactory(servletContext.getClassLoader());
    }

    public Object getBean(String name){
        return beanFactory.getBean(name);
    }

    public void init(){
        String basePackage = (String) servletContext.getAttribute(BASE_PACKAGE_ATTRIBUTE);
        beanFactory.instantiate(basePackage);
        beanFactory.addBean(DispatcherServlet.HANDLER_MAPPING_BEAN_NAME, initHandlerMapping());
        beanFactory.addBean(DispatcherServlet.HANDLER_ADAPTER_BEAN_NAME, initHandlerAdapter());
    }

    private HandlerMapping initHandlerMapping(){
        HandlerMapping handlerMapping = new HandlerMapping();
        for (Object bean : beanFactory.getBeans().values()) {
            Class<?> beanType = bean.getClass();
            if(handlerMapping.isHandler(beanType))
                handlerMapping.detectHandlerMethods(bean);
        }
        return handlerMapping;
    }

    private HandlerAdapter initHandlerAdapter(){
        return new HandlerAdapter(initArgumentResolvers());
    }

    private List<HandlerMethodArgumentResolver> initArgumentResolvers(){
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new RequestParamMethodArgumentResolver());
        resolvers.add(new HttpRequestMethodArgumentResolver());

        return resolvers;
    }
}
