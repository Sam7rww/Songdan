package com.songdan.system.model.vo;

public class YMmrgOrder {

    //订单号
    private String code;

    //行号
    private String line;

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

    //订单数
    private String num;

    //已交付数
    private String submitnum;

    //未交付数
    private String unsubmitnum;

    //单位
    private String unit;

    //交货日期
    private String outputdate;

    //生产要求及噱头
    private String demand;

    //含税单价
    private double price;

    //含税金额
    private double allprice;

    //单据确认
    private String decision;

    //内径
    private String neijing;

    //计算方式
    private String calculate;

    //隔层板
    private String gecengban;

    public YMmrgOrder() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSubmitnum() {
        return submitnum;
    }

    public void setSubmitnum(String submitnum) {
        this.submitnum = submitnum;
    }

    public String getUnsubmitnum() {
        return unsubmitnum;
    }

    public void setUnsubmitnum(String unsubmitnum) {
        this.unsubmitnum = unsubmitnum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getOutputdate() {
        return outputdate;
    }

    public void setOutputdate(String outputdate) {
        this.outputdate = outputdate;
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

    public double getAllprice() {
        return allprice;
    }

    public void setAllprice(double allprice) {
        this.allprice = allprice;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getNeijing() {
        return neijing;
    }

    public void setNeijing(String neijing) {
        this.neijing = neijing;
    }

    public String getCalculate() {
        return calculate;
    }

    public void setCalculate(String calculate) {
        this.calculate = calculate;
    }

    public String getGecengban() {
        return gecengban;
    }

    public void setGecengban(String gecengban) {
        this.gecengban = gecengban;
    }
}
