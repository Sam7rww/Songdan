package com.songdan.system.service;

import javax.servlet.http.HttpServletResponse;

public interface SLPrintService {
    /**
     * 打印采购订单表
     * @param
     * @return
     */
    public Object printPurchaseOrder(Object nums, HttpServletResponse response);

    /**
     * 打印瓦楞纸板采购订单
     * @param
     * @return
     */
    public Object printWalengOrder(Object nums, HttpServletResponse response);
}
