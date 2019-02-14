package org.springframework.web;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.Set;

@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext context) {
        if (classes != null) {
            for(Class<?> clazz : classes){
                try {
                    WebApplicationInitializer initializer =
                            (WebApplicationInitializer) clazz.newInstance();
                    initializer.onStartup(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
