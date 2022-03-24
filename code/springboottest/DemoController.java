package com.zen.pangolin.controller.rest.analysis;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        System.out.println("------hello()---------");
        return "hello!";
    }

    @RequestMapping(value = "/hello/get/{name}", method = RequestMethod.GET)
    public String getHello(@PathVariable("name") String name) {
        System.out.println("-------getHello()--------");
        return name + " hello ";
    }

    @RequestMapping(value = "/hello/post", method = RequestMethod.POST)
    public String postHello(@RequestParam("name") String name) {
        System.out.println("-------postHello()--------");
        return name + " hello ";
    }

}
