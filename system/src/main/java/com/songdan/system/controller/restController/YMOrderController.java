package com.songdan.system.controller.restController;


import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.vo.YMOrder;
import com.songdan.system.model.vo.YMProduceOrder;
import com.songdan.system.model.vo.YMPurchaseOrder;
import com.songdan.system.service.YMOrderService;
import com.songdan.system.service.YMPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping(value = "/wildhorse")
@RestController
public class YMOrderController {

    @Autowired
    private YMOrderService ymOrderService;

    @Autowired
    private YMPrintService ymPrintService;

    //保存订单
    @RequestMapping(value = "/saveOrderInfo")
    public Map<String,String> saveOrderInfo(HttpServletRequest request){
        String waterid = request.getParameter("id");
        String ordernum = request.getParameter("serial");
        String productid = request.getParameter("code");
        String productname = request.getParameter("name");
        String productname2 = request.getParameter("name2");
        String neijing = request.getParameter("neijing");
        int num = Integer.parseInt(request.getParameter("number").trim());
        String unit = request.getParameter("unit").equals("0") ?"只":"套";
        String date = request.getParameter("date");
        String price = request.getParameter("price").trim();
        String desc = request.getParameter("desc");
        String result = ymOrderService.saveYMOrder(waterid,ordernum,productid,productname,productname2,num,unit,date,desc,price,neijing);
        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }

        return message;
    }

    //导入Excel
    @RequestMapping(value = "/importYMOrders")
    public Map<String,String> importYMOrders(@RequestBody List<YMOrder> orders){

        //System.out.println("Enter Excel import!");
        //System.out.println("waterid NO.0:"+orders.get(0).getWaterid());

        String result = ymOrderService.saveImportExcel(orders);
        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("success","true");
            message.put("message","订单全部保存完成，输入订单数量:"+orders.size());
        }else{
            message.put("success","false");
            message.put("error",result);
        }
        return message;
    }

    //获取所有订单
    @RequestMapping(value = "/getAllOrder")
    public List getAllOrder(HttpServletRequest request){
        //System.out.println("进入getAllOrder,productname:"+request.getParameter("name"));
        List<YMUnprintOrder> orders = ymOrderService.getAllYMOrder();
        return orders;
    }

    //搜索订单
    @RequestMapping(value = "/getSearchOrder")
    public List getSearchOrder(HttpServletRequest request){
//        System.out.println("进入getSearchOrder");
        String waterid = request.getParameter("id");
        String ordernum = request.getParameter("serial");
        String productname = request.getParameter("name");
        String date = request.getParameter("date");
        List<YMUnprintOrder> orders = ymOrderService.getSearchYMOrder(waterid,ordernum,productname,date);
//        System.out.println("orders长度："+orders.size());
//        System.out.println("orders[0]:"+orders.get(0).getWaterid());
        return orders;
    }

    //获取今日输入订单
    @RequestMapping(value = "/getTodayOrder")
    public List getTodayOrder(){
        List<YMUnprintOrder> orders = ymOrderService.getTodayYMOrder();
        return orders;
    }

    //export采购订单
    @RequestMapping(value = "/exportPurchaseOrders")
    public List exportPurchaseOrders(@RequestBody List<YMUnprintOrder> orders){
        List<YMPurchaseOrder> purchaseOrders = ymPrintService.exportPurchaseOrder(orders);

        return purchaseOrders;
    }

    //export生产通知单
    @RequestMapping(value = "/exportProduceOrders")
    public List exportProduceOrders(@RequestBody List<YMUnprintOrder> orders){
        List<YMProduceOrder> produceOrders = ymPrintService.exportProduceOrder(orders);

        return produceOrders;
    }

    //print采购订单
    @RequestMapping(value = "/printPurchaseOrders")
    public void printPurchaseOrders(@RequestParam("nums") Object nums, HttpServletResponse response) throws Exception{
        ymPrintService.printPurchaseOrder(nums,response);
    }

    //print生产通知单

    //完成订单
    @RequestMapping(value = "/completeOrders")
    public boolean completeOrders(@RequestBody List<String> ids){
        //System.out.println("进入completeOrders");
        boolean res = ymOrderService.completeOrder(ids);
        return res;
    }

}
