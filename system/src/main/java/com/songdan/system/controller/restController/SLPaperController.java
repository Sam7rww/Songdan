package com.songdan.system.controller.restController;

import com.songdan.system.model.Entity.doubledear.SLpaper;
import com.songdan.system.service.SLPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/doubledear")
public class SLPaperController {

    @Autowired
    private SLPaperService slPaperService;

    @RequestMapping(value = "/searchSL")
    public Map<String,Object> searchSL(@Param("id") String id, @Param("name") String name){
        Map<String,Object> message = new HashMap<String, Object>();
        SLpaper temp = slPaperService.searchpaper(id,name);
        if(temp == null){
            message.put("result","fail");
            message.put("result","查询不到该订单。请确认输入");
        }else{
            message.put("result","pass");
            message.put("productid",temp.getProductid());
            message.put("productname",temp.getProductname());
            message.put("type",temp.getType());
            message.put("neijing",temp.getNeijing());
            message.put("waijing",temp.getWaijing());
            message.put("yaxian",temp.getYaxian());
            message.put("banpian",temp.getBanpian());
            message.put("press",temp.getPress());
            message.put("position",temp.getPostion());
        }
        return message;
    }

    @RequestMapping(value = "/addSL")
    public Map<String,Object> addSL(HttpServletRequest request){
//        System.out.println("Enter add paper");
        String productid = request.getParameter("productid").trim();
        String productname = request.getParameter("productname").trim();
        String press = request.getParameter("press").trim();
        String type = request.getParameter("calculate").trim();
        String neijing = request.getParameter("neijing").trim();
        String position = request.getParameter("position").trim();
        String result = slPaperService.savepaper(productid,productname,press,type,neijing,position);
        Map<String,Object> message = new HashMap<String, Object>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }
        return message;
    }

    @RequestMapping(value = "/updateSL")
    public Map<String,Object> updateSL(HttpServletRequest request){
//        System.out.println("Enter update paper");
        String productid = request.getParameter("productid");
        String productname = request.getParameter("productname");
        String press = request.getParameter("press").trim();
        String type = request.getParameter("calculate").trim();
        String neijing = request.getParameter("neijing");
        String waijing = request.getParameter("waijing");
        String banpian = request.getParameter("banpian");
        String yaxian = request.getParameter("yaxian");
        String position = request.getParameter("position");
        String result = slPaperService.updatepaper(productid,productname,
                press,type,neijing,waijing,banpian,yaxian,position);
        Map<String,Object> message = new HashMap<String, Object>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }
        return message;
    }

}
