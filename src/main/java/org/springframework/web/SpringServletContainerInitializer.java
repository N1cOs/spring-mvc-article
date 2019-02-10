package org.springframework.web;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.lang.reflect.Modifier;
import java.util.Set;

@HandlesTypes(WebApplicationInitializer.class)
public class SpringServletContainerInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext context) throws ServletException {
        if (classes != null) {
            for(Class<?> clazz : classes){
                if (!Modifier.isAbstract(clazz.getModifiers())) {
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
}
