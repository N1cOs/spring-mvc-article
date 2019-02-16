package org.springframework.web;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public abstract class WebApplicationInitializer {

    public void onStartup(ServletContext context) {
        context.setAttribute(WebApplicationContext.BASE_PACKAGE_ATTRIBUTE, getPackageToScan());

        WebApplicationContext applicationContext = new WebApplicationContext(context);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);

        ServletRegistration.Dynamic registration =
                context.addServlet("dispatcherServlet", dispatcherServlet);
        registration.setLoadOnStartup(1);
        registration.addMapping(getServletMapping());
    }

    protected abstract String getServletMapping();

    protected abstract String getPackageToScan();
}
