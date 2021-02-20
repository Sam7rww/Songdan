package com.songdan.system.model.Entity.doubledear;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SLUnprintOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //作业单号
    private String ordernum;

    //行号
    private String line;

    //作业单号/行号
    private String orderline;

    //录入日期
    private String inputdate;

    //交货日期
    private String outputdate;

    //物料代码
    private String productid;

    //物料名称
    private String productname;

    //物料名称
    private String productname2;

    //数量
    private int num;

    //状态
    private int state;

    public SLUnprintOrder() {
    }

    public SLUnprintOrder(String ordernum, String line, String orderline, String inputdate, String outputdate, String productid, String productname, String productname2, int num) {
        this.ordernum = ordernum;
        this.line = line;
        this.orderline = orderline;
        this.inputdate = inputdate;
        this.outputdate = outputdate;
        this.productid = productid;
        this.productname = productname;
        this.productname2 = productname2;
        this.num = num;
        this.state = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdernum() {
        return ordernum;
    }

    public void setOrdernum(String ordernum) {
        this.ordernum = ordernum;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getOrderline() {
        return orderline;
    }

    public void setOrderline(String orderline) {
        this.orderline = orderline;
    }

    public String getInputdate() {
        return inputdate;
    }

    public void setInputdate(String inputdate) {
        this.inputdate = inputdate;
    }

    public String getOutputdate() {
        return outputdate;
    }

    public void setOutputdate(String outputdate) {
        this.outputdate = outputdate;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getProductname2() {
        return productname2;
    }

    public void setProductname2(String productname2) {
        this.productname2 = productname2;
    }
}
