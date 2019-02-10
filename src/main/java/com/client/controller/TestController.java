package com.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(path = "/root")
public class TestController {

    @RequestMapping(path = "/test")
    public String test(){
        return "test";
    }

    @RequestMapping(path = {"/test2", "/test3"}, methods = {RequestMethod.GET})
    public String test2(){
        return "test2";
    }
}
