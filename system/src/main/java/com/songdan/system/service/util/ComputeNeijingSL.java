package com.songdan.system.service.util;

import java.math.BigDecimal;

public class ComputeNeijingSL {

    private double NJlength;

    private double NJwide;

    private double NJheight;

    private double WJlength;

    private double WJwide;

    private double WJheight;

    private boolean inputCorrectFlag;//判定输入是否为长*宽*高

    public ComputeNeijingSL(String nj,String type){
        double[] require = new double[3];
        if(type.equals("3")){
            require = new double[]{1, 0.6, 1.2};
        }else if(type.equals("5")){
            require = new double[]{1, 0.8, 1.4};
        }else{
            require = new double[]{1, 0.8, 1.4};
        }
        String[] njs = nj.split("\\*");
        int l = njs.length;
        if(l!=3){
            inputCorrectFlag = false;
        }else{
            inputCorrectFlag = true;
            this.NJlength = Double.valueOf(njs[0].trim());
            this.NJwide = Double.valueOf(njs[1].trim());
            this.NJheight = Double.valueOf(njs[2].trim());
            this.WJlength = NJlength + require[0];
            BigDecimal a = new BigDecimal(WJlength);
            WJlength = a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            this.WJwide = NJwide + require[1];
            BigDecimal b = new BigDecimal(WJwide);
            WJwide = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            this.WJheight = NJheight + require[2];
            BigDecimal c = new BigDecimal(WJheight);
            WJheight = c.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }

    public boolean assertInput(){
        return inputCorrectFlag;
    }

    public String getWaiJing(){
        return String.valueOf(WJlength)+"*"+String.valueOf(WJwide)+"*"+String.valueOf(WJheight);
    }

    public String getBanPian(){
        double l = (WJlength + WJwide)*2+4.5;
        //double temp = (WJwide + WJheight) * 10;
        double temp = (WJwide) * 10;
        double w = WJwide + WJheight;
        if(temp %2 != 0){
            w += 0.3;//奇数
        }else{
            w += 0.4;//偶数
        }
        BigDecimal a = new BigDecimal(l);
        l = a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        BigDecimal b = new BigDecimal(w);
        w = b.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(l)+"*"+String.valueOf(w);
    }

    public String getYaXian(){
        double temp = WJwide*10;
        double YXlength = WJwide;
        if(temp %2 != 0 ){
            YXlength += 0.3;//奇数
        }else{
            YXlength += 0.4;//偶数
        }
        YXlength /= 2;
        BigDecimal a = new BigDecimal(YXlength);
        YXlength = a.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(YXlength)+"+"+String.valueOf(WJheight)+"+"+String.valueOf(YXlength);
    }
}
