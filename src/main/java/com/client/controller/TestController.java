package com.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/root")
public class TestController {

    @RequestMapping(path = "/test")
    public String test(){
        return "test";
    }
}
