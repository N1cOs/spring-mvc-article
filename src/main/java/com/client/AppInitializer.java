package com.client;

import org.springframework.web.WebApplicationInitializer;

public class AppInitializer extends WebApplicationInitializer {

    @Override
    protected String getServletMapping() {
        return "/";
    }

    @Override
    protected String getPackageToScan() {
        return "com.client";
    }
}
