package com.songdan.system.service.impl;

import com.songdan.system.dao.YMpaperDao;
import com.songdan.system.model.Entity.wildhorse.YMpaper;
import com.songdan.system.model.vo.YMOrder;
import com.songdan.system.model.vo.YMmailOrder;
import com.songdan.system.model.vo.YMmrgOrder;
import com.songdan.system.service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExcelServiceImpl implements ExcelService {

    private static List<YMmrgOrder> Excellists = Collections.synchronizedList(new ArrayList<>());

    @Autowired
    private YMpaperDao paperdao;

    @Override
    public List<YMmailOrder> mergeSheets(InputStream inputStream) {
        List<YMmailOrder> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            int sheetNum = workbook.getNumberOfSheets();
            for (int sheetIdx = 0; sheetIdx < sheetNum; sheetIdx++){
                //开始遍历sheet
                Sheet sheet = workbook.getSheetAt(sheetIdx);
                int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();
//                System.out.println("firstRowIndex: " + firstRowIndex);
//                System.out.println("lastRowIndex: " + lastRowIndex);
                boolean isHeadInfo = false;
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
//                    System.out.println("rIndex: " + rIndex);
                    YMmailOrder order = new YMmailOrder();
                    Row row = sheet.getRow(rIndex);
                    if (row.getCell(0).getCellTypeEnum() == CellType.STRING) {
                        String numNo = "序号";
                        if ((row.getCell(0).getStringCellValue().trim()).equals(numNo)) {
                            isHeadInfo = true;
                            continue;
                        }
                        String description = "说明";
                        if ((row.getCell(0).getStringCellValue().trim()).equals(description)) {
                            isHeadInfo = false;
                            continue;
                        }
                    }

                    row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                    if((row.getCell(1).getStringCellValue().trim()).equals("")||
                            row.getCell(2).getStringCellValue().trim().equals("")||
                            row.getCell(3).getStringCellValue().trim().equals("")){
                        isHeadInfo = false;
                        continue;
                    }


                    if(isHeadInfo){//接下来是数据，开始保存
                        Cell cid = row.getCell(0);//序号
                        order.setId(this.getCellString(cid));
                        Cell wid = row.getCell(1);//采购流水号
                        order.setWaterid(this.getCellString(wid));
                        Cell oid = row.getCell(2);//作业单号
                        order.setOrdernum(this.getCellString(oid));
                        Cell pid = row.getCell(3);//物料代码
                        order.setProductid(this.getCellString(pid));
                        Cell pname = row.getCell(4);//物料名称
                        order.setProductname(this.getCellString(pname));
                        Cell pname2 = row.getCell(5);//别名
                        order.setProductname2(this.getCellString(pname2));
                        Cell unit = row.getCell(6);//单位
                        order.setUnit(this.getCellString(unit));
                        Cell numb = row.getCell(7);//数量
                        order.setNum(this.getCellString(numb));
                        Cell dt = row.getCell(8);//交货日期
                        if(dt.getCellTypeEnum() == CellType.NUMERIC){
                            Date temp = dt.getDateCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            order.setOutputdate(sdf.format(temp));
                        }else{
                            String temp = this.getCellString(dt);
                            if((this.getCellString(dt)).contains("-")){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
                                temp = sdf2.format(sdf.parse(temp).getTime());
                            }
                            order.setOutputdate(temp);
                        }
                        Cell demand = row.getCell(9);//生产要求及噱头
                        order.setDemand(this.getCellString(demand));
                        Cell price = row.getCell(10);//含税单价
                        double pricetemp = Double.parseDouble(this.getCellString(price));
                        order.setPrice(pricetemp+"");

                        //自动填写内径
                        this.searchNJ(order,this.getCellString(pid));
                        list.add(order);
                    }

                }

            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public String mergeExcels(InputStream inputStream) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            //开始遍历第一个sheet里的内容
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
//            System.out.println("firstRowIndex: " + firstRowIndex);
//            System.out.println("lastRowIndex: " + lastRowIndex);
            boolean isHeadInfo = false;
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                //System.out.println("rIndex: " + rIndex);
                YMmrgOrder order = new YMmrgOrder();
                Row row = sheet.getRow(rIndex);
                if (row.getCell(0).getCellTypeEnum() == CellType.STRING) {
                    String numNo = "订单号";
                    if ((row.getCell(0).getStringCellValue().trim()).equals(numNo)) {
                        isHeadInfo = true;
                        continue;
                    }
                }

                row.getCell(2,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(3,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(9,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
                if((row.getCell(2).getStringCellValue().trim()).equals("")||
                        row.getCell(3).getStringCellValue().trim().equals("")||
                        row.getCell(9).getStringCellValue().trim().equals("")){
                    isHeadInfo = false;
                    continue;
                }

                if(isHeadInfo){//接下来是数据，开始保存
                    Cell cid = row.getCell(0);//订单号
                    order.setCode(this.getCellString(cid));
                    Cell line = row.getCell(1);//行号
                    order.setLine(this.getCellString(line));
                    Cell pid = row.getCell(2);//物料代码
                    order.setProductid(this.getCellString(pid));
                    Cell pname = row.getCell(3);//物料名称
                    order.setProductname(this.getCellString(pname));
                    Cell unit = row.getCell(4);//单位
                    order.setUnit(this.getCellString(unit));
                    Cell dt = row.getCell(5);//交货日期
                    if(dt.getCellTypeEnum() == CellType.NUMERIC){
                        Date temp = dt.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        order.setOutputdate(sdf.format(temp));
                    }else{
                        String temp = this.getCellString(dt);
                        if((this.getCellString(dt)).contains("-")){
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
                            temp = sdf2.format(sdf.parse(temp).getTime());
                        }
                        order.setOutputdate(temp);
                    }
                    Cell numb = row.getCell(6);//订单数
                    order.setNum(this.getCellString(numb));
                    Cell smtnum = row.getCell(7);//已交付数
                    order.setSubmitnum(this.getCellString(smtnum));
                    Cell unsmtnum = row.getCell(8);//未交付数
                    order.setUnsubmitnum(this.getCellString(unsmtnum));
                    Cell wid = row.getCell(9);//采购流水号
                    order.setWaterid(this.getCellString(wid));
                    Cell pname2 = row.getCell(10);//别名
                    order.setProductname2(this.getCellString(pname2));
                    Cell oid = row.getCell(11);//作业单号
                    order.setOrdernum(this.getCellString(oid));
                    Cell demand = row.getCell(12);//生产要求及噱头
                    order.setDemand(this.getCellString(demand));
                    Cell price = row.getCell(13);//含税单价
                    order.setPrice(Double.parseDouble(this.getCellString(price)));
                    Cell allprice = row.getCell(14);//含税金额
                    order.setAllprice(Double.parseDouble(this.getCellString(allprice)));
                    Cell decision = row.getCell(15);//单据确认
                    order.setDecision(this.getCellString(decision));

//                    System.out.println("price is:"+this.getCellString(price));
//                    System.out.println("price(double) is:"+Double.parseDouble(this.getCellString(price)));

                    //自动填写内径
                    this.searchMrgNJ(order,this.getCellString(pid));
                    Excellists.add(order);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }

    @Override
    public List<YMmrgOrder> clearExcels() {
        List<YMmrgOrder> orders = Collections.synchronizedList(new ArrayList<>());
        orders.addAll(Excellists);
        orders.sort(new Comparator<YMmrgOrder>() {
            @Override
            public int compare(YMmrgOrder o1, YMmrgOrder o2) {
                if(o1.getWaterid()==null||o2.getWaterid()==null){
                    return 0;
                }
                return o1.getWaterid().compareTo(o2.getWaterid());
            }
        });
        Excellists.clear();
//        System.out.println("ExcelLists的大小"+Excellists.size());
        return orders;
    }

//    @Override
//    public List<YMOrder> searchExcel(InputStream inputStream) {
//        List<YMOrder> list = new ArrayList<>();
//        Workbook workbook = null;
//        try {
//            workbook = WorkbookFactory.create(inputStream);
//            inputStream.close();
//
//            //开始遍历第一个sheet里的内容
//            Sheet sheet = workbook.getSheetAt(0);
//            int firstRowIndex = sheet.getFirstRowNum();
//            int lastRowIndex = sheet.getLastRowNum();
////            System.out.println("firstRowIndex: " + firstRowIndex);
////            System.out.println("lastRowIndex: " + lastRowIndex);
//            boolean isHeadInfo = false;
//            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
////                System.out.println("rIndex: " + rIndex);
//                YMOrder order = new YMOrder();
//                Row row = sheet.getRow(rIndex);
//                if (!isHeadInfo && row.getCell(0) != null && row.getCell(0).getCellTypeEnum() == CellType.STRING) {
//                    String numNo = "单据编号";
//                    if ((row.getCell(0).getStringCellValue().trim()).equals(numNo)) {
//                        isHeadInfo = true;
//                        //check head info is correst (each column is desired column)
//                        if (!row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("交货日期")||
//                                !row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("采购流水号")||
//                                !row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("物料编码")||
//                                !row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("物料名称")||
//                                !row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("采购数量")||
//                                !row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("需求单据编号")||
//                                !row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("采购单位")||
//                                !row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("辅助属性.版本号.编码")||
//                                !row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("含税单价")){
//                            return null;
//                        }
//                        continue;
//                    }
//                }
//
//                //end the YMorder search
//                row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
//                row.getCell(7,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
//                row.getCell(8,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellType(Cell.CELL_TYPE_STRING);
//                if((row.getCell(5,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim()).equals("")||
//                        row.getCell(7,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("")||
//                        row.getCell(8,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("")){
//                    isHeadInfo = false;
//                    continue;
//                }
//
//                if(isHeadInfo){//接下来是数据，开始保存
//                    Cell pid = row.getCell(7);//物料代码_left_part
//                    String productid = this.getCellString(pid);
//                    Cell extra = row.getCell(14);//物料代码_right_part
//                    String type = this.getCellString(extra).split("V")[1];
//                    productid = productid+type;
//                    order.setProductid(productid);
//                    Cell pname = row.getCell(8);//物料名称
//                    order.setProductname(this.getCellString(pname));
//                    Cell unit = row.getCell(13);//单位
//                    order.setUnit(this.getCellString(unit));
//                    Cell dt = row.getCell(4);//交货日期
//                    if(dt.getCellTypeEnum() == CellType.NUMERIC){
//                        Date temp = dt.getDateCellValue();
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                        order.setOutputdate(sdf.format(temp));
//                    }else{
//                        String temp = this.getCellString(dt);
//                        String d = temp.split(" ")[0];
//                        order.setOutputdate(d);
//                    }
//                    Cell numb = row.getCell(9);//订单数
//                    order.setNum(Integer.parseInt(this.getCellString(numb)));
//
//                    Cell wid = row.getCell(5);//采购流水号
//                    order.setWaterid(this.getCellString(wid));
//                    //别名
//                    order.setProductname2("");
//                    Cell oid = row.getCell(10);//作业单号
//                    order.setOrdernum(this.getCellString(oid));
//                    //生产要求及噱头
//                    order.setDemand("");
//                    Cell price = row.getCell(15);//含税单价
//                    order.setPrice(this.getCellString(price));
////                    System.out.println("price is:"+this.getCellString(price));
////                    System.out.println("price(double) is:"+Double.parseDouble(this.getCellString(price)));
//
//                    //自动填写内径
//                    this.updateExcelNJ(order,productid);
//                    list.add(order);
//                }
//
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//
//        return list;
//    }

    @Override
    public List<YMOrder> searchExcel(InputStream inputStream) {
        List<YMOrder> list = new ArrayList<>();
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(inputStream);
            inputStream.close();

            //开始遍历第一个sheet里的内容
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
//            System.out.println("firstRowIndex: " + firstRowIndex);
//            System.out.println("lastRowIndex: " + lastRowIndex);
            boolean isHeadInfo = false;
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
//                System.out.println("rIndex: " + rIndex);
                YMOrder order = new YMOrder();
                Row row = sheet.getRow(rIndex);
                if (!isHeadInfo && row.getCell(0) != null && row.getCell(0).getCellTypeEnum() == CellType.STRING) {
                    String numNo = "单据编号";
                    if ((row.getCell(0).getStringCellValue().trim()).equals(numNo)) {
                        isHeadInfo = true;
                        //check head info is correst (each column is desired column)
                        if (!row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("交货日期")||
                                !row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("采购流水号")||
                                !row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("物料编码")||
                                !row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("物料名称")||
                                !row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("采购数量")||
                                !row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("需求单据编号")||
                                !row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("采购单位")||
                                !row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("辅助属性.版本号.编码")||
                                !row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("含税单价")){
                            return null;
                        }
                        continue;
                    }
                }

                //end the YMorder search
                if(row.getCell(5) == null || row.getCell(7) == null|| row.getCell(8) == null){
                    isHeadInfo = false;
                    continue;
                }else if(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("合计")){
                    isHeadInfo = false;
                    continue;
                }else if(row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("")||
                        row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getStringCellValue().trim().equals("")){
                    isHeadInfo = false;
                    continue;
                }

                if(isHeadInfo){//接下来是数据，开始保存
                    Cell pid = row.getCell(7);//物料代码_left_part
                    String productid = pid.getStringCellValue();
                    Cell extra = row.getCell(14);//物料代码_right_part
                    String type = extra.getStringCellValue().split("V")[1];
                    productid = productid+type;
                    order.setProductid(productid);
                    Cell pname = row.getCell(8);//物料名称
                    order.setProductname(pname.getStringCellValue());
                    Cell unit = row.getCell(13);//单位
                    order.setUnit(unit.getStringCellValue());
                    Cell dt = row.getCell(4);//交货日期
                    if(dt.getCellTypeEnum() == CellType.NUMERIC){
                        Date temp = dt.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        order.setOutputdate(sdf.format(temp));
                    }else{
                        String temp = dt.getStringCellValue();
                        String d = temp.split(" ")[0];
                        order.setOutputdate(d);
                    }
                    Cell numb = row.getCell(9);//订单数
                    order.setNum((int)numb.getNumericCellValue());

                    Cell wid = row.getCell(5);//采购流水号
                    order.setWaterid(String.valueOf((int)wid.getNumericCellValue()));
                    //别名
                    order.setProductname2("");
                    Cell oid = row.getCell(10);//作业单号
                    order.setOrdernum(oid.getStringCellValue());
                    //生产要求及噱头
                    order.setDemand("");
                    Cell price = row.getCell(15);//含税单价
                    order.setPrice(String.valueOf(price.getNumericCellValue()));
//                    System.out.println("price is:"+this.getCellString(price));
//                    System.out.println("price(double) is:"+Double.parseDouble(this.getCellString(price)));

                    //自动填写内径
                    this.updateExcelNJ(order,productid);
                    list.add(order);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }

    private String getCellString(Cell cell){
        String val = "";
        if(cell != null){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            val = cell.getStringCellValue().trim();
        }
        return val;
    }

    private void searchNJ(YMmailOrder order,String productid){
        String temp = this.replaceExcess(productid);
        YMpaper targetPaper = paperdao.findByProductid(temp);
        if(targetPaper!=null){
            order.setNeijing(targetPaper.getNeijing());
            order.setCalculate(targetPaper.getType());
            order.setGecengban(targetPaper.getGecengban());
        }else{
            order.setNeijing("");
            order.setCalculate("");
            order.setGecengban("");
        }
    }

    private void searchMrgNJ(YMmrgOrder order,String productid){
        String temp = this.replaceExcess(productid);
        YMpaper targetPaper = paperdao.findByProductid(temp);
        if(targetPaper!=null){
            order.setNeijing(targetPaper.getNeijing());
            order.setCalculate(targetPaper.getType());
            order.setGecengban(targetPaper.getGecengban());
        }else{
            order.setNeijing("");
            order.setCalculate("");
            order.setGecengban("");
        }
    }

    private void updateExcelNJ(YMOrder order,String productid){
        String temp = this.replaceExcess(productid);
        YMpaper targetPaper = paperdao.findByProductid(temp);
        if(targetPaper!=null){
            order.setNeijing(targetPaper.getNeijing());
            order.setCalculate(targetPaper.getType());
            order.setGecengban(targetPaper.getGecengban());
        }else{
            order.setNeijing("");
            order.setCalculate("");
            order.setGecengban("");
        }
    }

    //删去字符串中的空格，制表符，回车，换行
    private String replaceExcess(String target){
        String dest = "";
        if(target!=null){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(target);
            dest = m.replaceAll("");
        }
        return dest;
    }
}
