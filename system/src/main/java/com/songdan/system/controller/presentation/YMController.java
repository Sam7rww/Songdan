package com.songdan.system.controller.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/wildhorse")
public class YMController {

    //野马页面
    @RequestMapping(value = "/inputorder")
    public String inputOrder(){
        return "wildhorse/inputorder";
    }

    @RequestMapping(value = "/changeorder")
    public String changeOrder(){
        return "wildhorse/changeorder";
    }

    @RequestMapping(value = "/printorder")
    public String printOrder(){return "wildhorse/printorder"; }

    @RequestMapping(value = "/printinspect")
    public String printInspect(){
        return "wildhorse/printinspect";
    }

    @RequestMapping(value = "/whpaper")
    public String wdpaper(){
        return "wildhorse/whpaper";
    }
}
