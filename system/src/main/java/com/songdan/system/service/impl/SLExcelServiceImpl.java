package com.songdan.system.service.impl;

import com.songdan.system.dao.SLpaperDao;
import com.songdan.system.model.Entity.doubledear.SLpaper;
import com.songdan.system.model.vo.SLmrgOrder;
import com.songdan.system.service.SLExcelService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SLExcelServiceImpl implements SLExcelService {

    private static List<SLmrgOrder> SLExcels = new ArrayList<>();

    @Autowired
    private SLpaperDao paperDao;

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
                SLmrgOrder order = new SLmrgOrder();
                Row row = sheet.getRow(rIndex);
                if (row.getCell(0).getCellTypeEnum() == CellType.STRING) {
                    String numNo = "采购凭证";
                    if ((row.getCell(0).getStringCellValue().trim()).equals(numNo)) {
                        isHeadInfo = true;
                        continue;
                    }
                }

                row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(7).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(8).setCellType(Cell.CELL_TYPE_STRING);
                if((row.getCell(3).getStringCellValue().trim()).equals("")||
                        row.getCell(7).getStringCellValue().trim().equals("")||
                        row.getCell(8).getStringCellValue().trim().equals("")){
                    isHeadInfo = false;
                    continue;
                }

                if(isHeadInfo){ //接下来是数据，开始保存
                    Cell purchaseid = row.getCell(0);//采购凭证
                    order.setPurchaseorder(this.getCellString(purchaseid));
                    Cell lp = row.getCell(1);//行项目
                    order.setLineproject(this.getCellString(lp));
                    Cell sl = row.getCell(2);//交货计划行
                    order.setSubmitline(this.getCellString(sl));
                    Cell ordernum = row.getCell(3);//销售单号
                    order.setOrdernum(this.getCellString(ordernum));
                    Cell line = row.getCell(4);//行号
                    order.setLine(this.getCellString(line));
                    Cell net = row.getCell(5);//网络号
                    order.setNet(this.getCellString(net));
                    Cell indate = row.getCell(6);//录入日期
                    if(indate.getCellTypeEnum() == CellType.NUMERIC){
                        Date temp = indate.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        order.setInputdate(sdf.format(temp));
                    }else{
                        order.setInputdate(this.getCellString(indate));
                    }
                    Cell pid = row.getCell(7);//物料代码
                    order.setProductid(this.getCellString(pid));
                    Cell pname = row.getCell(8);//物料名称
                    order.setProductname(this.getCellString(pname));
                    Cell numb = row.getCell(9);//订单数
                    order.setNum(this.getCellString(numb));
                    Cell getnum = row.getCell(10);//已收货数量
                    order.setGetnum(this.getCellString(getnum));
                    Cell outnum = row.getCell(11);////发货数量
                    order.setOutputnum(this.getCellString(outnum));
                    Cell boxnum = row.getCell(12);//箱只数
                    order.setBoxnum(this.getCellString(boxnum));
                    Cell lumbernum = row.getCell(13);//托只数
                    order.setLumbernum(this.getCellString(lumbernum));
                    Cell unit = row.getCell(14);//单位
                    order.setUnit(this.getCellString(unit));
                    Cell dt = row.getCell(15);//交货日期
                    if(dt.getCellTypeEnum() == CellType.NUMERIC){
                        Date temp = dt.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        order.setOutputdate(sdf.format(temp));
                    }else{
                        order.setOutputdate(this.getCellString(dt));
                    }
                    Cell sbtlocation = row.getCell(16);//交货地点
                    order.setSubmitlocation(this.getCellString(sbtlocation));
                    Cell lstprint = row.getCell(17);//上次打印时间
                    order.setLastprint(this.getCellString(lstprint));
                    Cell svlocation = row.getCell(18);//库存地点
                    order.setSavelocation(this.getCellString(svlocation));
                    Cell pcg = row.getCell(19);//采购组
                    order.setPurchasegroup(this.getCellString(pcg));
                    Cell state = row.getCell(20);//状态
                    order.setState(this.getCellString(state));
                    Cell workshop = row.getCell(21);//工厂
                    order.setWorkshop(this.getCellString(workshop));
                    Cell wsname = row.getCell(22);//工厂名称
                    order.setWorkshopname(this.getCellString(wsname));

                    //自动填写内径
                    this.searchNJ(order,this.getCellString(pid));
                    SLExcels.add(order);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "success";
    }

    @Override
    public List<SLmrgOrder> clearExcels() {
        List<SLmrgOrder> lists = new ArrayList<>();
        lists.addAll(SLExcels);
        SLExcels.clear();
        return lists;
    }

    private String getCellString(Cell cell){
        String val = "";
        if(cell != null){
            cell.setCellType(Cell.CELL_TYPE_STRING);
            val = cell.getStringCellValue().trim();
        }
        return val;
    }

    private void searchNJ(SLmrgOrder order, String productid){
        SLpaper targetPaper = paperDao.findByProductid(productid.trim());
        if(targetPaper!=null){
            order.setNeijing(targetPaper.getNeijing());
            order.setType(targetPaper.getType());
            order.setPress(targetPaper.getPress());
        }else{
            order.setNeijing("");
            order.setType("");
            order.setPress("");
        }
    }
}
