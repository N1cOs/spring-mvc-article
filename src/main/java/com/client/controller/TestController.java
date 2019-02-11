package com.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(path = "/root")
public class TestController {

    @RequestMapping(path = "/test")
    public String test(HttpServletRequest request, @RequestParam("name") String name){
        return "Hello " + name;
    }

    @RequestMapping(path = {"/test2", "/test3"}, methods = {RequestMethod.GET})
    public String test2(){
        return "test2";
    }
}
