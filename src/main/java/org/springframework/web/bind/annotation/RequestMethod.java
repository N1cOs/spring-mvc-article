package org.springframework.web.bind.annotation;

public enum RequestMethod {

    GET("GET"), POST("POST"), PUT("PUT"), DELETE("DELETE");

    private String value;

    RequestMethod(String value){
        this.value = value;
    }

    public boolean matches(String method){
        return value.equals(method);
    }
}
