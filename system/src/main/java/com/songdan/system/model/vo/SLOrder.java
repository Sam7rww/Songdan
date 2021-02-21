package com.songdan.system.model.vo;

public class SLOrder {

    //作业单号
    private String ordernum;

    //行号
    private String line;

    //录入日期
    private String inputdate;

    //交货日期
    private String outputdate;

    //物料代码
    private String productid;

    //物料名称
    private String productname;

    //备注
    private String backup;

    //数量
    private int num;

    //单位
    private String unit;

    //内径
    private String neijing;

    //计算方式
    private String type;

    public SLOrder() {
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

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
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

    public String getNeijing() {
        return neijing;
    }

    public void setNeijing(String neijing) {
        this.neijing = neijing;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
