package com.client.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping(path = "/root", methods = RequestMethod.POST)
public class GreetingController {

    @RequestMapping(path = "/greeting", methods = RequestMethod.GET)
    public String test(HttpServletRequest request,
                       @RequestParam(name = "name", defaultValue = "user") String name){
        String requestName = Optional.ofNullable(request.getParameter("name")).
                orElse("user");
        if(!name.equals(requestName))
            throw new IllegalArgumentException();
        return "Hello " + name;
    }
}
