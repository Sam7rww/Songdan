package com.songdan.system.controller.restController;

import com.songdan.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/signin")
    public Map<String,String> signin(@Param("username") String username, @Param("password") String password){
        Map<String,String> message = new HashMap<>();
        String result = userService.checkService(username,password);
        if(result.equals("厂长登录")){
            message.put("result","pass");
            message.put("role","leader");
        }else if(result.equals("检验员登录")){
            message.put("result","pass");
            message.put("role","checker");
        }else{
            message.put("result","fail");
            message.put("info",result);
        }
        return message;
    }
}
