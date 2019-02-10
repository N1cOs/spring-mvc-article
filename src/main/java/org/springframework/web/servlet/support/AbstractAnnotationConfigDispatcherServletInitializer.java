package org.springframework.web.servlet.support;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public abstract class AbstractAnnotationConfigDispatcherServletInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext context) {
        WebApplicationContext applicationContext =
                new WebApplicationContext(context.getClassLoader(), getPackageToScan());
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

        ServletRegistration.Dynamic registration =
                context.addServlet("dispatcherServlet", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping(getServletMapping());
    }

    protected abstract String getServletMapping();

    protected abstract String getPackageToScan();
}
