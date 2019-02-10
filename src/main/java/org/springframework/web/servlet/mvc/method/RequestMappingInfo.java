package org.springframework.web.servlet.mvc.method;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;
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

    //info - bean request mapping
    public RequestMappingInfo combine(RequestMappingInfo info){
        Set<String> newPatterns = new HashSet<>();
        for(String beanPattern : info.patterns){
            for(String pattern : patterns)
                newPatterns.add(beanPattern + pattern);
        }
        HashSet<RequestMethod> newMethods = new HashSet<>(methods);
        newMethods.addAll(info.methods);
        return new RequestMappingInfo(newPatterns, newMethods);
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public Set<RequestMethod> getMethods() {
        return methods;
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
            RequestMappingInfo info = (RequestMappingInfo) obj;
            return this.patterns.equals(info.patterns) && this.methods.equals(info.methods);
        }
        return false;
    }
}
