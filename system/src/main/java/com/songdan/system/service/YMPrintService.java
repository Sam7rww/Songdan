package com.songdan.system.service;

import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.vo.YMProduceOrder;
import com.songdan.system.model.vo.YMPurchaseOrder;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface YMPrintService {

    /**
     * 导出采购订单表
     * @param
     * @return
     */
    public List<YMPurchaseOrder> exportPurchaseOrder(List<YMUnprintOrder> unprintOrders);

    /**
     * 导出生产通知单
     * @param
     * @return
     */
    public List<YMProduceOrder> exportProduceOrder(List<YMUnprintOrder> unprintOrders);

    /**
     * 打印采购订单表
     * @param
     * @return
     */
    public Object printPurchaseOrder(Object nums, HttpServletResponse response);

    /** 
     * 打印生产通知单
     * @param
     * @return
     */
    public Object printProduceOrder(Object nums, HttpServletResponse response);

    /**
     * 打印瓦楞纸板采购订单
     * @param
     * @return
     */
    public Object printWalengOrder(Object nums, HttpServletResponse response);
}
