package com.songdan.system.service.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HandleExcel {

//    public static void main(String[] args) {
//        String path = "./松旦浙江577.xlsx";
//        System.out.println("Test");
//        String excelPath = "./松旦浙江5777.xlsx";
//        handleExcel(excelPath);
//    }

    public static void handleExcel(String path) {
        String fileName = "" + System.currentTimeMillis();
        String fileType = "xlsx";
        try {
            File excel = new File(path);
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                fileName = split[0];
                fileType = split[1];
                Workbook wb;  //根据文件后缀（xls/xlsx）进行判断
                if ("xls".equals(split[1])) {
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                } else if ("xlsx".equals(split[1])) {
                    wb = new XSSFWorkbook(excel);
                } else {
                    System.out.println("文件类型错误!");
                    return;
                }

                int sheetNum = wb.getNumberOfSheets();
                List<Row> rowList = new ArrayList<>();
                boolean isHeadInfo = true;
                for (int sheetIdx = 0; sheetIdx < sheetNum; sheetIdx++) {
                    //开始解析
                    Sheet sheet = wb.getSheetAt(sheetIdx);     //读取sheet 0
                    int firstRowIndex = sheet.getFirstRowNum() + 1;   //第一行是列名，所以不读
                    int lastRowIndex = sheet.getLastRowNum();
                    System.out.println("firstRowIndex: " + firstRowIndex);
                    System.out.println("lastRowIndex: " + lastRowIndex);
                    for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                        System.out.println("rIndex: " + rIndex);
                        Row row = sheet.getRow(rIndex);
                        if (isHeadInfo) {
                            rowList.add(row);
                            if (row.getCell(0).getCellTypeEnum() == CellType.STRING) {
                                String numNo = "序号";
                                if (row.getCell(0).getStringCellValue().equals(numNo)) {
                                    isHeadInfo = false;
                                }
                            }
                        } else if (row.getCell(0).getCellTypeEnum() == CellType.NUMERIC) {
                            rowList.add(row);
                        } else {
                            System.out.println("Error first column of the sheet:" + sheetIdx + ",row:" + rIndex);
                        }
//                        if (row != null) {
//                            int firstCellIndex = row.getFirstCellNum();
//                            int lastCellIndex = row.getLastCellNum();
//                            for (int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex++) {   //遍历列
//                                Cell cell = row.getCell(cIndex);
//                                if (cell != null) {
//                                    System.out.println(cell.toString());
//                                }
//                            }
//                        }
                    }
                }
                Workbook workbook = new SXSSFWorkbook();
                Sheet sheet = workbook.createSheet();
                for (int i = 0; i < rowList.size(); i++) {
                    Row curentRow = sheet.createRow(i);
                    for (int j = 0; j < rowList.get(i).getPhysicalNumberOfCells(); j++) {
                        Cell currentCell = curentRow.createCell(j, CellType.STRING);
                        Cell sourceCell = rowList.get(i).getCell(j);
                        String cellValue = null;
                        if (sourceCell.getCellTypeEnum() == CellType.STRING) {
                            cellValue = sourceCell.getStringCellValue();
                            currentCell.setCellValue(sourceCell.getStringCellValue());
                        } else if (sourceCell.getCellTypeEnum() == CellType.NUMERIC) {
                            if (HSSFDateUtil.isCellDateFormatted(sourceCell)) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                Date date = DateUtil
                                        .getJavaDate(sourceCell.getNumericCellValue());
                                cellValue = sdf.format(date);
                                currentCell.setCellValue(date);
                            } else {
                                cellValue = "" + sourceCell.getNumericCellValue();
                                currentCell.setCellValue(sourceCell.getNumericCellValue());

                            }
                        } else if (sourceCell.getCellTypeEnum() == CellType.BLANK) {
                            cellValue = "";
                            currentCell.setCellValue("");
                        } else if (sourceCell.getCellTypeEnum() == CellType.BOOLEAN) {
//                            cellValue = "" + sourceCell.getBooleanCellValue();
                            currentCell.setCellValue(sourceCell.getBooleanCellValue());
                        }
                        System.out.println(i + "," + j + "=" + cellValue);
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(fileName + "_合并." + fileType));
                workbook.write(out);
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

