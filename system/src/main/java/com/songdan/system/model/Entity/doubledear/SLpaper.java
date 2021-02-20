package com.songdan.system.model.Entity.doubledear;

import javax.persistence.*;

@Entity
public class SLpaper {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String productid;

    @Column(unique = true, nullable = false)
    private String productname;

    private String productname2;

    private String neijing;

    private String waijing;

    private String banpian;

    private String yaxian;
    //图纸计算方法
    private String type;
    //图纸保存位置
    private String postion;

    public SLpaper() {
    }

    public SLpaper(String productid, String productname, String productname2, String neijing, String waijing, String banpian, String yaxian, String type) {
        this.productid = productid;
        this.productname = productname;
        this.productname2 = productname2;
        this.neijing = neijing;
        this.waijing = waijing;
        this.banpian = banpian;
        this.yaxian = yaxian;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }
}