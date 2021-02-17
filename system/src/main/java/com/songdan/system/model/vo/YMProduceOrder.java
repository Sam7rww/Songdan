package com.songdan.system.model.vo;

public class YMProduceOrder {

    //作业单号
    private String ordernum;

    //物料代码
    private String productid;

    //物料名称
    private String productname;

    //内径
    private String neijing;

    //外径
    private String waijing;

    //板片
    private String banpian;

    //压线
    private String yaxian;

    //数量
    private int num;

    //单位
    private String unit;

    public YMProduceOrder(String ordernum, String productid, String productname, String neijing, String waijing, String banpian, String yaxian, int num, String unit) {
        this.ordernum = ordernum;
        this.productid = productid;
        this.productname = productname;
        this.neijing = neijing;
        this.waijing = waijing;
        this.banpian = banpian;
        this.yaxian = yaxian;
        this.num = num;
        this.unit = unit;
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

    public String getNeijing() {
        return neijing;
    }

    public void setNeijing(String neijing) {
        this.neijing = neijing;
    }

    public String getWaijing() {
        return waijing;
    }

    public void setWaijing(String waijing) {
        this.waijing = waijing;
    }

    public String getBanpian() {
        return banpian;
    }

    public void setBanpian(String banpian) {
        this.banpian = banpian;
    }

    public String getYaxian() {
        return yaxian;
    }

    public void setYaxian(String yaxian) {
        this.yaxian = yaxian;
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
}
