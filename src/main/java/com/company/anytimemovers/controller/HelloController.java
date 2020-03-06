package com.company.anytimemovers.controller;

import com.company.anytimemovers.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by admin
 */
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("hello")
    public Object hello(String msg){
        String str=helloService.hello(msg);
        return str;
    }


}
