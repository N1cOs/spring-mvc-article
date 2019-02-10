package org.springframework.web.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerMapping {

    private final Map<RequestMappingInfo, HandlerMethod> mappingLookup = new HashMap<>();

    private final Map<String, List<RequestMappingInfo>>  urlLookup = new HashMap<>();

    public boolean isHandler(Class<?> beanType){
        return beanType.isAnnotationPresent(Controller.class)
                || beanType.isAnnotationPresent(RequestMapping.class);
    }

    public HandlerMethod getHandler(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String lookupPath = requestURI.substring(request.getContextPath().length());
        return lookupHandlerMethod(lookupPath, request);
    }

    public void detectHandlerMethods(Object handler){
        Class<?> beanType = handler.getClass();
        RequestMapping beanRequestMapping = beanType.getAnnotation(RequestMapping.class);
        RequestMappingInfo beanInfo = null;
        if(beanRequestMapping != null){
            beanInfo = new RequestMappingInfo(beanRequestMapping.path(), beanRequestMapping.methods());
        }

        for(Method method : beanType.getMethods()){
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if(requestMapping != null){
                RequestMappingInfo info = new RequestMappingInfo(requestMapping.path(), requestMapping.methods());

                if(beanInfo != null){
                    info = info.combine(beanInfo);
                }
                registerMapping(info, handler, method);
            }
        }
    }

    private HandlerMethod lookupHandlerMethod(String path, HttpServletRequest request){
        for(RequestMappingInfo info : mappingLookup.keySet()){
            if(info.matches(path, request)){
                return mappingLookup.get(info);
            }
        }
        return null;
    }

    private void registerMapping(RequestMappingInfo info, Object handler, Method method){
        HandlerMethod handlerMethod = new HandlerMethod(method, handler);
        mappingLookup.put(info, handlerMethod);

        //is pattern?
//        info.getPatterns().stream().filter(s -> s.indexOf('*') == -1 && s.indexOf('?') == -1).
//                forEach(s -> urlLookup.get(s).add(info));
    }
}
