package com.songdan.system.service;

import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.vo.YMOrder;

import java.util.List;

public interface YMOrderService {
    /**
     * 存储收到的野马订单信息
     * @param
     * @return
     */
    public String saveYMOrder(String waterid, String ordernum, String productid,
                              String productname, String productname2, int num, String unit,
                              String date, String demand, String price,String neijing,String gecengban);

    /**
     * 存储上传的Excel订单信息
     * @param
     * @return
     */
    public String saveImportExcel(List<YMOrder> orders);

    /**
     * 更新野马订单信息
     * @param
     * @return
     */
    public String updateYMOrder(String waterid, int num, String date, String demand,
                                String price,int state);

    /**
     * 删除野马订单信息
     * @param
     * @return
     */
    public String deleteYMOrder(String waterid, int state);

    /**
     * 获得全部未处理的野马订单信息
     * @param
     * @return
     */
    public List<YMUnprintOrder> getAllYMOrder();

    /**
     * 根据条件查询相关的未处理野马订单信息
     * @param
     * @return
     */
    public List<YMUnprintOrder> getSearchYMOrder(String waterid, String ordernum, String productname, String outputdate);

    /**
     * 获取当日输入的野马订单信息
     * @param
     * @return
     */
    public List<YMUnprintOrder> getTodayYMOrder();

    /**
     * 根据waterid数组获得相应的YMUnprintOrder List
     * @param
     * @return
     */
    public List<YMUnprintOrder> findEachByWaterid(List<String> waterids);

    /**
     * 完成选择的订单
     * @param
     * @return
     */
    public boolean completeOrder(List<String> ids);

}
