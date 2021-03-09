package com.songdan.system.service.impl;

import com.songdan.system.dao.YMpaperDao;
import com.songdan.system.model.Entity.wildhorse.YMpaper;
import com.songdan.system.model.vo.YMmailOrder;
import com.songdan.system.service.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ExcelServiceImpl implements ExcelService {

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
                //System.out.println("firstRowIndex: " + firstRowIndex);
                //System.out.println("lastRowIndex: " + lastRowIndex);
                Boolean isHeadInfo = false;
                for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    //System.out.println("rIndex: " + rIndex);
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

                    row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                    row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
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
                        order.setOutputdate(this.getCellString(dt));
                        Cell demand = row.getCell(9);//生产要求及噱头
                        order.setDemand(this.getCellString(demand));
                        Cell price = row.getCell(10);//含税单价
                        order.setPrice(this.getCellString(price));

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
