package com.client;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected String getServletMapping() {
        return "/";
    }

    @Override
    protected String getPackageToScan() {
        return "com.client";
    }
}
