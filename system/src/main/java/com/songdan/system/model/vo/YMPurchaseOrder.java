package com.songdan.system.model.vo;


public class  YMPurchaseOrder {

    //采购流水号
    private String waterid;

    //作业单号
    private String ordernum;

    //物料代码
    private String productid;

    //物料名称
    private String productname;

    //别名
    private String productname2;

    //数量
    private int num;

    //单位
    private String unit;

    //交货日期
    private String outputdate;

    //生产要求及噱头
    private String demand;

    //含税单价
    private double price;

    public YMPurchaseOrder(String waterid, String ordernum, String productid, String productname, String productname2, int num, String unit, String date, String demand, double price) {
        this.waterid = waterid;
        this.ordernum = ordernum;
        this.productid = productid;
        this.productname = productname;
        this.productname2 = productname2;
        this.num = num;
        this.unit = unit;
        this.outputdate = date;
        this.demand = demand;
        this.price = price;
    }

    public String getWaterid() {
        return waterid;
    }

    public void setWaterid(String waterid) {
        this.waterid = waterid;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductname2() {
        return productname2;
    }

    public void setProductname2(String productname2) {
        this.productname2 = productname2;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getOutputdate() {
        return outputdate;
    }

    public void setOutputdate(String outputdate) {
        this.outputdate = outputdate;
    }
}
