package com.songdan.system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

    @GetMapping(value = "/index")
    public String index(){
        return "hello Spring boot!";
    }
}
