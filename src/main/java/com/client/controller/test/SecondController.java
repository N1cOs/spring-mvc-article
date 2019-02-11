package com.client.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/root")
public class SecondController {

    @RequestMapping(path = "/second")
    public String secondTest(@RequestParam("name") String name){
        return "Hello " + name + ". It's second controller";
    }
}
