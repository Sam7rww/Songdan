package com.songdan.system.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.songdan.system.dao.SLpaperDao;
import com.songdan.system.model.Entity.doubledear.SLUnprintOrder;
import com.songdan.system.model.Entity.doubledear.SLpaper;
import com.songdan.system.service.SLOrderService;
import com.songdan.system.service.SLPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class SLPrintServiceImpl implements SLPrintService {

    @Autowired
    private SLOrderService slOrderService;

    @Autowired
    private SLpaperDao sLpaperDao;

    @Override
    public Object printPurchaseOrder(Object nums, HttpServletResponse response) {
        String datas = nums.toString();
        String[] str = datas.split(",");
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            data.add(str[i]);
        }
        //用于存储YMUnprintOrder的每个for循环的值
        List list = new ArrayList<>();
        //查询选中的订单信息
        List<SLUnprintOrder> slorders = slOrderService.findEachByid(data);
        for (SLUnprintOrder temp:slorders) {
            //处理物料代码后5位
            String producttemp = "";
            if(temp.getProductid().length()<5){
                producttemp = temp.getProductid();
            }else{
                producttemp = temp.getProductid().substring(temp.getProductid().length()-5);
            }

            //获取图纸版本信息
            SLpaper sLpaper = sLpaperDao.findByProductid(temp.getProductid());
            String position = (sLpaper.getPostion()==null?"":sLpaper.getPostion());

            String[] arr = {producttemp,position,temp.getProductname(),
                    temp.getNeijing(),temp.getWaijing(),temp.getBanpian(),temp.getYaxian(),
                    temp.getOutputdate(),temp.getNum()+"",temp.getPress(),""};
            list.add(arr);
        }
        try {
            Document document = new Document(PageSize.A4.rotate());
            File f = File.createTempFile("双鹿电池采购订单", ".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(f));

            // 设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font titleFont =
                    new com.itextpdf.text.Font(bfChinese, 15, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font thFont =
                    new com.itextpdf.text.Font(bfChinese, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font nomalFont = new com.itextpdf.text.Font(bfChinese, 10,
                    com.itextpdf.text.Font.NORMAL);


            document.open();

            //标题
            Paragraph paragraphOne = new Paragraph("松旦包装采购订单",titleFont);
            paragraphOne.setAlignment(Element.ALIGN_CENTER);
            paragraphOne.setSpacingAfter(30);
            paragraphOne.setSpacingBefore(30);
            document.add(paragraphOne);
            //标题下附注
//            Paragraph paragraph1 = new Paragraph("供应商:松旦包装印刷有限公司",nomalFont);
//            paragraph1.setAlignment(Element.ALIGN_LEFT);
//            document.add(paragraph1);


            // table1
            PdfPTable table1 = new PdfPTable(11);
            table1.setWidthPercentage(100); // Width 100%
            float[] columnWidths = {0.05f,0.04f,0.18f,0.125f,0.125f,0.125f,0.125f,0.08f,0.05f,0.05f,0.05f};
            table1.setWidths(columnWidths);
            String[] ths = {"物料代码","版本","物料名称","内径","外径","板片","压线","日期","数量","变压","备注"};
            for (String th : ths) {
                Paragraph para = new Paragraph(th, thFont);
                para.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cell = new PdfPCell(para);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table1.addCell(cell);
            }
            document.add(table1);

            // table2

            for (int i = 0; i < list.size(); i++) {
                PdfPTable table2 = new PdfPTable(11);
                table2.setWidthPercentage(100); // Width 100%
                table2.setWidths(columnWidths);
                String[] arr = (String[]) list.get(i);
                for (int j = 0; j < arr.length; j++) {
                    Paragraph para = new Paragraph(arr[j], nomalFont);
                    para.setAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell = new PdfPCell(para);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                }
                document.add(table2);
            }

            document.close();

            PdfReader reader = new PdfReader(f.getAbsolutePath());

            StringBuffer script = new StringBuffer();
            script.append("this.print({bUI: false,bSilent: true,bShrinkToFit: false});")
                    .append("\r\nthis.closeDoc();");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                PdfStamper stamp = new PdfStamper(reader, bos);
//                stamp.setViewerPreferences(PdfWriter.HideMenubar | PdfWriter.HideToolbar
//                        | PdfWriter.HideWindowUI);
                stamp.addJavaScript(script.toString());
                stamp.close();
            } catch (DocumentException e) {
            }
            response.getOutputStream().write(bos.toByteArray());
            return null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Object printWalengOrder(Object nums, HttpServletResponse response) {
        String datas = nums.toString();
        String[] str = datas.split(",");
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            data.add(str[i]);
        }
        //用于存储YMUnprintOrder的每个for循环的值
        List list = new ArrayList<>();
        //查询选中的订单信息
        List<SLUnprintOrder> slorders = slOrderService.findEachByid(data);
        for (SLUnprintOrder temp:slorders) {
            String[] arr = {"","",temp.getBanpian(),temp.getYaxian(),temp.getNum()+"",temp.getOutputdate(),""};
            list.add(arr);
        }
        try {
            Document document = new Document(PageSize.A4.rotate());
            File f = File.createTempFile("双鹿电池瓦楞纸采购订单", ".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(f));

            // 设置字体
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
            com.itextpdf.text.Font titleFont =
                    new com.itextpdf.text.Font(bfChinese, 15, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font thFont =
                    new com.itextpdf.text.Font(bfChinese, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font nomalFont = new com.itextpdf.text.Font(bfChinese, 10,
                    com.itextpdf.text.Font.NORMAL);


            document.open();

            //标题
            Paragraph paragraphOne = new Paragraph("松旦包装瓦楞纸采购订单",titleFont);
            paragraphOne.setAlignment(Element.ALIGN_CENTER);
            paragraphOne.setSpacingAfter(30);
            paragraphOne.setSpacingBefore(30);
            document.add(paragraphOne);
            //标题下附注
//            Paragraph paragraph1 = new Paragraph("供应商:松旦包装印刷有限公司",nomalFont);
//            paragraph1.setAlignment(Element.ALIGN_LEFT);
//            document.add(paragraph1);


            // table1
            PdfPTable table1 = new PdfPTable(7);
            table1.setWidthPercentage(100); // Width 100%
            float[] columnWidths = {0.15f, 0.15f,0.20f,0.20f,0.10f,0.10f,0.10f};
            table1.setWidths(columnWidths);
            String[] ths = {"材质代号","楞型","板片尺寸","压线尺寸","数量","交货日期","备注"};
            for (String th : ths) {
                Paragraph para = new Paragraph(th, thFont);
                para.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cell = new PdfPCell(para);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table1.addCell(cell);
            }
            document.add(table1);

            // table2

            for (int i = 0; i < list.size(); i++) {
                PdfPTable table2 = new PdfPTable(7);
                table2.setWidthPercentage(100); // Width 100%
                table2.setWidths(columnWidths);
                String[] arr = (String[]) list.get(i);
                for (int j = 0; j < arr.length; j++) {
                    Paragraph para = new Paragraph(arr[j], nomalFont);
                    para.setAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell = new PdfPCell(para);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table2.addCell(cell);
                }
                document.add(table2);
            }

            document.close();

            PdfReader reader = new PdfReader(f.getAbsolutePath());

            StringBuffer script = new StringBuffer();
            script.append("this.print({bUI: false,bSilent: true,bShrinkToFit: false});")
                    .append("\r\nthis.closeDoc();");

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            try {
                PdfStamper stamp = new PdfStamper(reader, bos);
//                stamp.setViewerPreferences(PdfWriter.HideMenubar | PdfWriter.HideToolbar
//                        | PdfWriter.HideWindowUI);
                stamp.addJavaScript(script.toString());
                stamp.close();
            } catch (DocumentException e) {
            }
            response.getOutputStream().write(bos.toByteArray());
            return null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
