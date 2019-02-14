package org.springframework.web.servlet.mvc.method;

import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RequestMappingInfo {

    private Set<String> patterns;

    private Set<RequestMethod> methods;

    public RequestMappingInfo(String[] patterns, RequestMethod[] methods) {
        this.patterns = Arrays.stream(patterns).collect(Collectors.toSet());
        this.methods = Arrays.stream(methods).collect(Collectors.toSet());
    }

    public RequestMappingInfo(Set<String> patterns, Set<RequestMethod> methods) {
        this.patterns = patterns;
        this.methods = methods;
    }
    
    public RequestMappingInfo combine(RequestMappingInfo beanInfo){
        Set<String> newPatterns = new HashSet<>();
        for(String beanPattern : beanInfo.patterns){
            for(String pattern : patterns)
                newPatterns.add(beanPattern + pattern);
        }
        HashSet<RequestMethod> newMethods = new HashSet<>(methods);
        newMethods.addAll(beanInfo.methods);
        return new RequestMappingInfo(newPatterns, newMethods);
    }

    public boolean matches(String path, HttpServletRequest request) {
        String method = request.getMethod();
        if(methods.stream().noneMatch(s -> s.matches(method)))
            return false;

        return patterns.stream().anyMatch(path::matches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patterns, methods);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj instanceof RequestMappingInfo){
            RequestMappingInfo beanInfo = (RequestMappingInfo) obj;
            return this.patterns.equals(beanInfo.patterns) && this.methods.equals(beanInfo.methods);
        }
        return false;
    }
}
