package com.songdan.system.service.impl;

import com.songdan.system.dao.UserDao;
import com.songdan.system.model.Entity.User;
import com.songdan.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String checkService(String username, String password) {
        User user = userDao.findByUsername(username);
        if(user == null){
            return "用户名错误，请检查。";
        }else if(!user.getPassword().equals(password)){
            return "密码错误，请检查。";
        }
        if(user.getType() == 0){
            return "厂长登录";
        }else if(user.getType() == 1){
            return "检验员登录";
        }else{
            return "登录信息错误，请联系管理员";
        }
    }
}
