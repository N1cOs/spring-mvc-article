package org.springframework.web;

import javax.servlet.ServletContext;

public interface WebApplicationInitializer {

    void onStartup(ServletContext context);
}
