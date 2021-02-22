package com.songdan.system.model.vo;

public class inspectOrder {
    private String waterid;
    private String productname;
    private int num;
    private String length;
    private String wide;
    private String high;
    private String unit;
    private String ordernum;
    private double price;

    public inspectOrder() {
    }

    public inspectOrder(String waterid, String productname, int num, String length,
                        String wide, String high, String unit, String ordernum, double price) {
        this.waterid = waterid;
        this.productname = productname;
        this.num = num;
        this.length = length;
        this.wide = wide;
        this.high = high;
        this.unit = unit;
        this.ordernum = ordernum;
        this.price = price;
    }

    public String getWaterid() {
        return waterid;
    }

    public void setWaterid(String waterid) {
        this.waterid = waterid;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
