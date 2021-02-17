package com.songdan.system.model.Entity.wildhorse;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Date;

@Entity
public class YMUnprintOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
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

    //录入日期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date inputdate;

    //交货日期
    private String outputdate;

    //生产要求及噱头
    private String demand;

    //含税单价
    private double price;

    //内径
    private String neijing;

    //外径
    private String waijing;

    //板片
    private String banpian;

    //压线
    private String yaxian;
    //0:未打印
    private int state;

    public YMUnprintOrder(){
    }

    public YMUnprintOrder(String waterid, String ordernum, String productid, String productname, String productname2, int num, String unit, Date inputdate, String outputdate, String demand, double price, String neijing, String waijing, String banpian, String yaxian) {
        this.waterid = waterid;
        this.ordernum = ordernum;
        this.productid = productid;
        this.productname = productname;
        this.productname2 = productname2;
        this.num = num;
        this.unit = unit;
        this.inputdate = inputdate;
        this.outputdate = outputdate;
        this.demand = demand;
        this.price = price;
        this.neijing = neijing;
        this.waijing = waijing;
        this.banpian = banpian;
        this.yaxian = yaxian;
        this.state = 0;
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

    public Date getInputdate() {
        return inputdate;
    }

    public void setInputdate(Date inputdate) {
        this.inputdate = inputdate;
    }

    public String getOutputdate() {
        return outputdate;
    }

    public void setOutputdate(String outputdate) {
        this.outputdate = outputdate;
    }

    public int getState() {
        return state;
    }

    public void setState(int over) {
        this.state = over;
    }
}
