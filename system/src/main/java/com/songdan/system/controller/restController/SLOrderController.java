package com.songdan.system.controller.restController;

import com.songdan.system.model.Entity.doubledear.SLUnprintOrder;
import com.songdan.system.model.vo.SLOrder;
import com.songdan.system.service.SLOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/doubledear")
@RestController
public class SLOrderController {

    @Autowired
    private SLOrderService slOrderService;

    //保存订单
    @RequestMapping(value = "/saveSLOrderInfo")
    public Map<String,String> saveOrderInfo(HttpServletRequest request){
        String ordernum = request.getParameter("serial");
        String line = request.getParameter("line");
        String productid = request.getParameter("code");
        String productname = request.getParameter("name");
        String neijing = request.getParameter("neijing");
        String type = request.getParameter("calculate");
        int num = Integer.parseInt(request.getParameter("number").trim());
        String unit = request.getParameter("unit").trim();
        String indate = request.getParameter("indate");
        String date = request.getParameter("date");
        String backup = request.getParameter("backup");
        String result = slOrderService.saveSLOrder(ordernum,line,productid,productname,type,num,unit,
                backup,indate,date,neijing);
        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }

        return message;
    }

    //导入Excel订单信息
    @RequestMapping(value = "/importSLOrders")
    public Map<String,String> importSLOrders(@RequestBody List<SLOrder> orders){

        //System.out.println("Enter Excel import!");
        //System.out.println("waterid NO.0:"+orders.get(0).getWaterid());

        String result = slOrderService.saveImportExcel(orders);
        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("success","true");
            message.put("message","订单全部保存完成");
        }else{
            message.put("success","false");
            message.put("error",result);
        }
        return message;
    }

    //获取所有订单
    @RequestMapping(value = "/getAllOrders")
    public List getAllOrders(HttpServletRequest request){
        //System.out.println("进入getAllOrder,productname:"+request.getParameter("name"));
        List<SLUnprintOrder> orders = slOrderService.getAllSLOrder();
        return orders;
    }

    //搜索订单
    @RequestMapping(value = "/getSearchOrders")
    public List getSearchOrders(HttpServletRequest request){
//        System.out.println("进入getSearchOrder");
        String ordernum = request.getParameter("serial");
        String line = request.getParameter("line");
        String productname = request.getParameter("name");
        String date = request.getParameter("date");
        List<SLUnprintOrder> orders = slOrderService.getSearchSLOrder(ordernum,line,productname,date);
        return orders;
    }

    //完成订单
    @RequestMapping(value = "/completeSLOrders")
    public boolean completeSLOrders(@RequestBody List<String> ids){
        //System.out.println("进入completeOrders");
        boolean res = slOrderService.completeOrder(ids);
        return res;
    }
}
