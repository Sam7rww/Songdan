package com.songdan.system.controller.restController;


import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.vo.*;
import com.songdan.system.service.ExcelService;
import com.songdan.system.service.YMOrderService;
import com.songdan.system.service.YMPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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

    @Autowired
    private ExcelService excelService;

    private static Map<String,String> matchUnit = new HashMap<String,String>(){
        {
            put("只","0");
            put("张","1");
            put("套","2");
            put("0","只");
            put("1","张");
            put("2","套");
        }
    };

//    private static Map<String,String> matchState = new HashMap<String,String>(){
//        {
//            put("0","未打印");
//            put("0","已打印");
//        }
//    };

    //保存订单
    @RequestMapping(value = "/saveOrderInfo")
    public Map<String,String> saveOrderInfo(HttpServletRequest request){
        String waterid = request.getParameter("id");
        String ordernum = request.getParameter("serial").replace(" ","");
        String productid = request.getParameter("code").replace(" ","");
        String productname = request.getParameter("name");
        String productname2 = request.getParameter("name2");
        String neijing = request.getParameter("neijing");
        int num = Integer.parseInt(request.getParameter("number").trim());
        String unit = matchUnit.get(request.getParameter("unit").trim());
        String date = request.getParameter("date");
        String price = request.getParameter("price").trim();
        String desc = request.getParameter("desc");
        String gecengban = request.getParameter("gecengban").trim();
        String type = request.getParameter("calculate").trim();
        String result = ymOrderService.saveYMOrder(waterid,ordernum,productid,productname,productname2,num,unit,date,desc,price,neijing,gecengban,type);
        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }

        return message;
    }

    //导入Excel订单信息
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
        String indate = request.getParameter("indate");
        String neijing = request.getParameter("neijing");
        List<YMUnprintOrder> orders = ymOrderService.getSearchYMOrder(waterid,ordernum,productname,indate,date,neijing);
        return orders;
    }

    //查询订单
    @RequestMapping(value = "/findOrder")
    public Map<String,Object> findOrder(@RequestParam("waterid") String waterid){
//        System.out.println("Enter findOrder!!");
        List<String> orders = new ArrayList<>();
        orders.add(waterid);
        List<YMUnprintOrder> res = ymOrderService.findEachByWaterid(orders);
        Map<String,Object> message = new HashMap<String, Object>();
        if(res.size() == 0){
            message.put("result","fail");
            message.put("result","查询不到该订单。请确认输入");
        }else{
            message.put("result","pass");
            YMUnprintOrder temp = res.get(0);
            message.put("ordernum",temp.getOrdernum());
            message.put("productid",temp.getProductid());
            message.put("productname",temp.getProductname());
            message.put("productname2",temp.getProductname2());
            message.put("unit",matchUnit.get(temp.getUnit()));
            message.put("num",temp.getNum());
            message.put("outputdate",temp.getOutputdate());
            message.put("demand",temp.getDemand());
            message.put("price",temp.getPrice());
            message.put("neijing",temp.getNeijing());
            message.put("waijing",temp.getWaijing());
            message.put("yaxian",temp.getYaxian());
            message.put("banpian",temp.getBanpian());
            message.put("gecengban",temp.getGecengban());
            message.put("state",temp.getState());
        }
        return message;
    }

    //更新订单
    @RequestMapping(value = "/updateOrder")
    public Map<String,String> updateOrder(HttpServletRequest request){
        String waterid = request.getParameter("id");
        int num = Integer.parseInt(request.getParameter("number").trim());
        String date = request.getParameter("date");
        String price = request.getParameter("price").trim();
        String desc = request.getParameter("desc");
        int state = Integer.parseInt(request.getParameter("state").trim());
        String result = ymOrderService.updateYMOrder(waterid,num,date,desc,price,state);

        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }

        return message;
    }

    //删除订单
    @RequestMapping(value = "/deleteOrder")
    public Map<String,String> deleteOrder(@RequestParam("waterid") String waterid,@RequestParam("state") String state){

        String result = ymOrderService.deleteYMOrder(waterid,Integer.parseInt(state));

        Map<String,String> message = new HashMap<String, String>();
        if(result.equals("")){
            message.put("result","pass");
        }else{
            message.put("result",result);
        }

        return message;
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

    //print瓦楞纸板采购订单
    @RequestMapping(value = "/printWalengOrders")
    public void printWalengOrders(@RequestParam("nums") Object nums, HttpServletResponse response) throws Exception{
        ymPrintService.printWalengOrder(nums,response);
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

    //打印送检单
    @RequestMapping(value = "/printInspectOrder")
    public List printInspectOrder(HttpServletRequest request){
        String ids = request.getParameter("waterids");
        List<inspectOrder> lists = ymOrderService.inpectOrder(ids);
        if(lists == null){
            return null;
        }
        return lists;
    }
    //打印送货单

    //合并多个excel的sheet
    @ResponseBody
    @RequestMapping(value = "/uploadExcel")
    public Map<String,Object> uploadExcel(@RequestParam("target") MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException {
//        System.out.println("Enter Multi Excel!");
        Map<String,Object> result = new HashMap<>();
        // 原始名称
        String name = file.getOriginalFilename();
        if(name.length()<6|| !name.substring(name.length()-5).equals(".xlsx")){
            result.put("result","fail");
            return result;
        }

        List<YMmailOrder> temp = excelService.mergeSheets(file.getInputStream());
        result.put("result","pass");
        result.put("datas",temp);

        return result;
    }

    //合并多个excel
    @ResponseBody
    @RequestMapping(value = "/mergeExcel")
    public Map<String,Object> mergeExcel(@RequestParam("target") MultipartFile file, HttpServletRequest request, HttpSession session) throws IOException {
//        System.out.println("Enter Multi Excel!");
        Map<String,Object> result = new HashMap<>();
        // 原始名称
        String name = file.getOriginalFilename();
        if(name.length()<5|| !name.substring(name.length()-4).equals(".xls")){
            result.put("result","fail");
            result.put("file",name);
            return result;
        }

        String res = excelService.mergeExcels(file.getInputStream());
        if(res.equals("success")){
            result.put("result","pass");
        }else{
            result.put("result","fail");
            result.put("file",name);
        }
        return result;
    }

    @RequestMapping(value = "/clearExcel")
    public Map<String,Object> clearExcel(){
        Map<String,Object> result = new HashMap<>();
        List<YMmrgOrder> orders = excelService.clearExcels();
        result.put("result","pass");
        result.put("datas",orders);
        return result;
    }

}
