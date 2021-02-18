package com.songdan.system.service.impl;

import com.songdan.system.dao.YMUnprintOrderDao;
import com.songdan.system.dao.YMpaperDao;
import com.songdan.system.dao.YMprintOrderDao;
import com.songdan.system.dao.search.YMUnprintSearchDao;
import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.Entity.wildhorse.YMpaper;
import com.songdan.system.model.Entity.wildhorse.YMprintOrder;
import com.songdan.system.model.vo.YMOrder;
import com.songdan.system.service.YMOrderService;
import com.songdan.system.service.util.ComputeNeiJing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class YMOrderServiceImpl implements YMOrderService {

    @Autowired
    private YMUnprintOrderDao ymunprint;

    @Autowired
    private YMUnprintSearchDao search;

    @Autowired
    private YMpaperDao paper;

    @Autowired
    private YMprintOrderDao ymprint;

    @Override
    public String saveYMOrder(String waterid, String ordernum, String productid, String productname, String productname2, int num, String unit, String outputdate, String demand, String price, String neijing) {
        java.util.Date date = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(date.getTime());//年月日
        ComputeNeiJing com = new ComputeNeiJing(neijing);
        boolean temp = ymunprint.existsByWaterid(waterid);
        if(temp){
            return "采购流水号不可重复";
        }
        YMUnprintOrder ymorder = new YMUnprintOrder(waterid,ordernum,productid,productname,productname2,num,unit,sDate,outputdate,demand,Double.parseDouble(price),neijing,com.getWaiJing(),com.getBanPian(),com.getYaXian());
        YMUnprintOrder ym = ymunprint.save(ymorder);
        if(ym != null){
            return "";
        }else{
            return "保存失败，检查输入";
        }
    }

    @Override
    public String saveImportExcel(List<YMOrder> orders) {
        java.util.Date date = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(date.getTime());//年月日

        for(int i=0;i<orders.size();i++){
            YMOrder order = orders.get(i);
            boolean exists = ymunprint.existsByWaterid(order.getWaterid());
            if(exists){
                continue;
            }
            YMUnprintOrder ymorder = new YMUnprintOrder(order.getWaterid(),order.getOrdernum(),order.getProductid(),order.getProductname(),order.getProductname2(),order.getNum(),order.getUnit(),sDate,order.getOutputdate(),order.getDemand(),Double.parseDouble(order.getPrice().trim()),"","","","");
            System.out.println("ymunprintorder key id:"+ymorder.getId());
            YMpaper targetPaper = paper.findByProductid(order.getProductid());
            if(targetPaper!=null){//找的到图纸
                ymorder.setNeijing(targetPaper.getNeijing());
                ymorder.setWaijing(targetPaper.getWaijing());
                ymorder.setBanpian(targetPaper.getBanpian());
                ymorder.setYaxian(targetPaper.getYaxian());
            }else{//找不到图纸
                if(order.getNeijing().equals("")){
                    return "采购流水号："+order.getWaterid()+"，图纸不存在，内径不可为空！";
                }
                if(order.getCalculate().equals("default")){//计算外径，并保存图纸
                    ComputeNeiJing com = new ComputeNeiJing(order.getNeijing());
                    ymorder.setNeijing(order.getNeijing());
                    ymorder.setWaijing(com.getWaiJing());
                    ymorder.setBanpian(com.getBanPian());
                    ymorder.setYaxian(com.getYaXian());
                    YMpaper p = new YMpaper(order.getProductid(),order.getProductname(),order.getNeijing(),com.getWaiJing(),com.getBanPian(),com.getYaXian(),order.getCalculate());
                    YMpaper paperRes = paper.save(p);
                    if(paperRes == null){
                        return "图纸保存失败，请检查图纸相关输入";
                    }
                }else{return "暂不支持非默认计算方式";}
                YMUnprintOrder ym = ymunprint.save(ymorder);
                if(ym == null){
                    return "采购流水号："+order.getWaterid()+"保存失败，请检查输入！必须项不可为空";
                }
            }
        }

        return "";
    }

    @Override
    public List<YMUnprintOrder> getAllYMOrder() {
        List<YMUnprintOrder> ymorderlist = ymunprint.findAll();
        return ymorderlist;
    }

    @Override
    public List<YMUnprintOrder> getSearchYMOrder(String waterid, String ordernum, String productname, String outputdate) {

        if(!waterid.equals("")){
            YMUnprintOrder order = ymunprint.findByWaterid(waterid);
            List<YMUnprintOrder> ymorderlist = new ArrayList<>();
            if(order.getState()!=0){
                return ymorderlist;
            }
            ymorderlist.add(order);
            return ymorderlist;
        }else{
            //方法1，通过EntityManager，自己append sql
            //List<YMUnprintOrder> ymorderlist = search.search(ordernum,productname,outputdate);
            //方法2，通过Specification实现动态查询，Root即root 获取实体类具体属性, CriteriaBuilder即cb 拼接查询条件的，拼接好查询条件之后，通过 CriteriaQuery即query 实现查询
            Specification<YMUnprintOrder> sf = (Specification<YMUnprintOrder>)(root, query, cb)->{
                //用于添加所有查询条件
                List<Predicate> p = new ArrayList<>();
                if (!ordernum.equals("")) {
                    Predicate p1 = cb.equal(root.get("ordernum").as(String.class), ordernum);
                    p.add(p1);
                }
                if (!productname.equals("")) {
                    Predicate p2 = cb.like(root.get("productname").as(String.class), "%"+productname+"%");
                    p.add(p2);
                }
                if (!outputdate.equals("")) {
                    Predicate p3 = cb.equal(root.get("outputdate").as(String.class), outputdate);
                    p.add(p3);
                }
                Predicate p4 = cb.equal(root.get("state").as(Integer.class),0);
                p.add(p4);
                Predicate[] pre = new Predicate[p.size()];
                Predicate and = cb.and(p.toArray(pre));     //查询条件 and
                //Predicate or = cb.or(p.toArray(pre));       //查询条件 or
                query.where(and);       //添加查询条件
                return query.getRestriction();
            };
            List<YMUnprintOrder> ymorderlist = ymunprint.findAll(sf);
            return ymorderlist;
        }
    }

    @Override
    public List<YMUnprintOrder> getTodayYMOrder() {
        java.util.Date date1 = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(date1.getTime());//年月日
        List<YMUnprintOrder> ymorderlist = ymunprint.findAllByInputdate(sDate);

        return ymorderlist;
    }

    @Override
    public List<YMUnprintOrder> findEachByWaterid(List<String> waterids) {
        List<YMUnprintOrder> orders = new ArrayList<>();
        for(int i=0;i<waterids.size();i++){
            YMUnprintOrder order = ymunprint.findByWaterid(waterids.get(i));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public boolean completeOrder(List<String> ids) {

        for(int i=0;i<ids.size();i++){
            String id = ids.get(i);
            ymunprint.updateState(id,1);
            YMUnprintOrder temp = ymunprint.findByWaterid(id);
            YMprintOrder printorder = new YMprintOrder(temp.getWaterid(),temp.getOrdernum(),temp.getProductid(),temp.getProductname(),temp.getProductname2(),temp.getNum(),temp.getUnit(),temp.getOutputdate(),temp.getDemand(),temp.getPrice());
            ymprint.save(printorder);
        }
        return true;
    }
}
