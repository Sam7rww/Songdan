package com.songdan.system.controller.restController;

import com.songdan.system.model.Entity.wildhorse.YMpaper;
import com.songdan.system.service.YMPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/wildhorse")
public class YMPaperController {

    @Autowired
    private YMPaperService paperService;

    @RequestMapping(value = "/searchYM")
    public Map<String,Object> searchYM(@Param("id") String id,@Param("name") String name){
        Map<String,Object> message = new HashMap<String, Object>();
        YMpaper temp = paperService.searchpaper(id,name);
        if(temp == null){
            message.put("result","fail");
            message.put("result","查询不到该订单。请确认输入");
        }else{
            message.put("result","pass");
            message.put("productid",temp.getProductid());
            message.put("productname",temp.getProductname());
            message.put("productname2",temp.getProductname2());
            message.put("type",temp.getType());
            message.put("neijing",temp.getNeijing());
            message.put("waijing",temp.getWaijing());
            message.put("yaxian",temp.getYaxian());
            message.put("banpian",temp.getBanpian());
            message.put("gecengban",temp.getGecengban());
        }
        return message;
    }

    @RequestMapping(value = "/addYM")
    public Map<String,Object> addYM(HttpServletRequest request){
//        System.out.println("Enter add paper");
        String productid = request.getParameter("productid").trim();
        String productname = request.getParameter("productname").trim();
        String productname2 = request.getParameter("productname2").trim();
        String gecengban = request.getParameter("gecengban").trim();
        String type = request.getParameter("calculate").trim();
        String neijing = request.getParameter("neijing").trim();
        String result = paperService.savepaper(productid,productname,productname2,gecengban,type,neijing);
        Map<String,Object> message = new HashMap<String, Object>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }
        return message;
    }

    @RequestMapping(value = "/updateYM")
    public Map<String,Object> updateYM(HttpServletRequest request){
//        System.out.println("Enter update paper");
        String productid = request.getParameter("productid");
        String productname = request.getParameter("productname");
        String productname2 = request.getParameter("productname2");
        String gecengban = request.getParameter("gecengban").trim();
        String type = request.getParameter("calculate").trim();
        String neijing = request.getParameter("neijing");
        String waijing = request.getParameter("waijing");
        String banpian = request.getParameter("banpian");
        String yaxian = request.getParameter("yaxian");
        String result = paperService.updatepaper(productid,productname,productname2,
                gecengban,type,neijing,waijing,banpian,yaxian);
        Map<String,Object> message = new HashMap<String, Object>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }
        return message;
    }
}
