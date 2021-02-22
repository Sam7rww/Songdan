package com.songdan.system.controller.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/doubledear")
public class SLController {

    //双鹿页面
    @RequestMapping(value = "/slinput")
    public String slinput(){
        return "doubledear/slinput";
    }

    @RequestMapping(value = "/slprint")
    public String printOrder(){return "doubledear/slprint"; }

    @RequestMapping(value = "/slpaper")
    public String slpaper(){
        return "doubledear/slpaper";
    }
}
