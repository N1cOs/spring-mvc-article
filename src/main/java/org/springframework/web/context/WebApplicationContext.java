package org.springframework.web.context;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.servlet.HandlerMapping;

public class WebApplicationContext {

    private BeanFactory beanFactory;

    public WebApplicationContext(String basePackage){
        beanFactory = new BeanFactory(basePackage);
    }

    public HandlerMapping initHandlers(){
        HandlerMapping handlerMapping = new HandlerMapping();
        for (Object bean : beanFactory.getBeans().values()) {
            Class<?> beanType = bean.getClass();
            if(handlerMapping.isHandler(beanType))
                handlerMapping.detectHandlerMethods(bean);
        }
        return handlerMapping;
    }

}
