package com.songdan.system.service;

import com.songdan.system.model.Entity.doubledear.SLUnprintOrder;
import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.vo.SLOrder;

import java.util.List;

public interface SLOrderService {

    /**
     * 存储收到的双鹿订单信息
     * @param
     * @return
     */
    public String saveSLOrder(String ordernum, String line, String productid,
                              String productname, String type, int num, String unit,String backup,
                              String indate, String date,String neijing);

    /**
     * 存储上传的Excel订单信息
     * @param
     * @return
     */
    public String saveImportExcel(List<SLOrder> orders);

    /**
     * 获得全部未处理的双鹿订单信息
     * @param
     * @return
     */
    public List<SLUnprintOrder> getAllSLOrder();

    /**
     * 根据条件查询相关的未处理双鹿订单信息
     * @param
     * @return
     */
    public List<SLUnprintOrder> getSearchSLOrder(String ordernum,String line, String productname, String outputdate);

    /**
     * 完成选择的订单
     * @param
     * @return
     */
    public boolean completeOrder(List<String> ids);
}
