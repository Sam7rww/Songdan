package com.songdan.system.controller.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping(value = "/login")
    public String login(){
        return "admin/login";
    }
}
