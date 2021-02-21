package com.songdan.system.service.impl;

import com.songdan.system.dao.SLUnprintOrderDao;
import com.songdan.system.dao.SLpaperDao;
import com.songdan.system.model.Entity.doubledear.SLUnprintOrder;
import com.songdan.system.model.Entity.doubledear.SLpaper;
import com.songdan.system.model.vo.SLOrder;
import com.songdan.system.service.SLOrderService;
import com.songdan.system.service.util.ComputeNeijingSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SLOrderServiceImpl implements SLOrderService {

    @Autowired
    private SLUnprintOrderDao slunprint;

    @Autowired
    private SLpaperDao paper;

    @Override
    public String saveImportExcel(List<SLOrder> orders) {

        for(int i=0;i<orders.size();i++){
            SLOrder order = orders.get(i);
            String orderline = order.getOrdernum()+"/"+order.getLine();
            if(order.getOrdernum().equals("") || order.getLine().equals("")){
                continue;
            }
            boolean exists = slunprint.existsByOrderlineAndProductid(orderline,order.getProductid());
            if(exists){
                continue;
            }
            SLUnprintOrder slorder = new SLUnprintOrder(order.getOrdernum(),order.getLine(),
                    orderline,order.getInputdate(),order.getOutputdate(),
                    order.getProductid(),order.getProductname(),order.getBackup(),order.getNum(),order.getUnit(),
                    "","","","");
            //System.out.println("ymunprintorder outputdate :"+ymorder.getOutputdate());
            SLpaper targetPaper = paper.findByProductid(order.getProductid());
            if(targetPaper!=null){//找的到图纸
                //System.out.println("find paper");
                slorder.setNeijing(targetPaper.getNeijing());
                slorder.setWaijing(targetPaper.getWaijing());
                slorder.setBanpian(targetPaper.getBanpian());
                slorder.setYaxian(targetPaper.getYaxian());
            }else{//找不到图纸
                //System.out.println("can't find paper");
                if(order.getNeijing().equals("")){
                    return "销售凭证："+orderline+"，图纸不存在，内径不可为空！";
                }
                String calculate = order.getType();
                //计算外径，并保存图纸
                ComputeNeijingSL com = new ComputeNeijingSL(order.getNeijing(),calculate);
                if(!com.assertInput()){
                    return "销售凭证："+orderline+",内径输入格式有误";
                }
                slorder.setNeijing(order.getNeijing());
                slorder.setWaijing(com.getWaiJing());
                slorder.setBanpian(com.getBanPian());
                slorder.setYaxian(com.getYaXian());
                SLpaper p = new SLpaper(order.getProductid(),order.getProductname(),order.getNeijing(),com.getWaiJing(),com.getBanPian(),com.getYaXian(),order.getType());
                SLpaper paperRes = paper.save(p);
                if(paperRes == null){
                    return "图纸保存失败，请检查图纸相关输入";
                }

            }
            SLUnprintOrder ym = slunprint.save(slorder);
            if(ym == null){
                return "销售凭证："+order.getOrdernum()+"/"+order.getLine()+"保存失败，请检查输入！必填项不可为空";
            }
        }

        return "";
    }

    @Override
    public List<SLUnprintOrder> getAllSLOrder() {
        List<SLUnprintOrder> orders = slunprint.findAll();
        return orders;
    }

    @Override
    public List<SLUnprintOrder> getSearchSLOrder(String ordernum, String line, String productname, String outputdate) {
        Specification<SLUnprintOrder> sf = (Specification<SLUnprintOrder>)(root, query, cb)->{
            //用于添加所有查询条件
            List<Predicate> p = new ArrayList<>();
            if (!ordernum.equals("")) {
                Predicate p1 = cb.equal(root.get("ordernum").as(String.class), ordernum);
                p.add(p1);
            }
            if(!line.equals("")){
                Predicate p5 = cb.equal(root.get("line").as(String.class),line);
                p.add(p5);
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
        List<SLUnprintOrder> slorderlist = slunprint.findAll(sf);
        //this.cutOrdernum(ymorderlist);
        return slorderlist;
    }

    @Override
    public boolean completeOrder(List<String> ids) {

        for(int i=0;i<ids.size();i++){
            int id = Integer.parseInt(ids.get(i).trim());
            slunprint.updateState(id,1);
        }
        return true;
    }
}
