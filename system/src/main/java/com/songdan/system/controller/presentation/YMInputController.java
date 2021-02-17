package com.songdan.system.controller.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/wildhorse")
public class YMInputController {

    @RequestMapping(value = "/inputorder")
    public String inputOrder(){
        return "wildhorse/inputorder";
    }

    @RequestMapping(value = "/changeorder")
    public String changeOrder(){
        return "wildhorse/";
    }

    @RequestMapping(value = "/printorder")
    public String printOrder(){return "wildhorse/printorder"; }
}
