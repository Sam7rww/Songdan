package com.songdan.system.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import com.songdan.system.model.vo.YMProduceOrder;
import com.songdan.system.model.vo.YMPurchaseOrder;
import com.songdan.system.service.YMOrderService;
import com.songdan.system.service.YMPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class YMPrintServiceImpl implements YMPrintService {

    @Autowired
    private YMOrderService ymOrderService;

    @Override
    public List<YMPurchaseOrder> exportPurchaseOrder(List<YMUnprintOrder> unprintOrders) {
        List<YMPurchaseOrder> purchaseOrders = new ArrayList<>();
        for(int i=0;i<unprintOrders.size();i++){
            YMUnprintOrder temp = unprintOrders.get(i);
            YMPurchaseOrder purchaseOrder = new YMPurchaseOrder(temp.getWaterid(),temp.getOrdernum(),temp.getProductid(),temp.getProductname(),temp.getProductname2(),temp.getNum(),temp.getUnit(),temp.getOutputdate(),temp.getDemand(),temp.getPrice());
            purchaseOrders.add(purchaseOrder);
        }
        return purchaseOrders;
    }

    @Override
    public List<YMProduceOrder> exportProduceOrder(List<YMUnprintOrder> unprintOrders) {
        List<YMProduceOrder> produceOrders = new ArrayList<>();
        for(int i=0;i<unprintOrders.size();i++){
            YMUnprintOrder temp = unprintOrders.get(i);
            YMProduceOrder produceOrder = new YMProduceOrder(temp.getOrdernum(),temp.getProductid(),temp.getProductname(),temp.getNeijing(),temp.getWaijing(),temp.getBanpian(),temp.getYaxian(),temp.getNum(),temp.getUnit());
            produceOrders.add(produceOrder);
        }
        return produceOrders;
    }

    @Override
    public Object printPurchaseOrder(Object nums, HttpServletResponse response) {
//        System.out.println("enter printPurchaseOrder!!");
//        //告诉浏览器用什么软件可以打开此文件
//        //response.setHeader("content-Type", "application/pdf");
//        response.setContentType("application/pdf");
//        //下载文件的默认名称
//        //response.setHeader("Content-Disposition", "attachment;filename=XXX.pdf");
//        //设置中文
//        try {
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
//
//            Document document = new Document();
//            PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
//            // 打开文档
//            document.open();
//            //设置文档标题
//            document.addTitle("PDF");
//            //设置文档作者
//            document.addAuthor("码神");
//            document.addCreationDate();
//            //设置关键字
//            document.addKeywords("iText");
//            document.addLanguage("中文");
//            //表头
//            PdfPTable title = new PdfPTable(2);
//            title.addCell(new Paragraph("编号", FontChinese));
//            title.addCell(new Paragraph("名称", FontChinese));
//            document.add(title);
//
//            //User u = new User("1", "码神");
//            //插入数据
//            PdfPTable table = new PdfPTable(2);
//            table.addCell(new Paragraph("1", FontChinese));
//            table.addCell(new Paragraph("阮威威", FontChinese));
//            document.add(table);
//
//            document.close();
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        String datas = nums.toString();
        String[] str = datas.split(",");
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < str.length; i++) {
            data.add(str[i]);
        }
        //用于存储YMUnprintOrder的每个for循环的值
        List list = new ArrayList<>();
        //查询选中的订单信息
        List<YMUnprintOrder> ymorders = ymOrderService.findEachByWaterid(data);
        for (YMUnprintOrder temp:ymorders) {
            String[] arr = {temp.getWaterid(),temp.getOrdernum(),temp.getProductid(),temp.getProductname(),temp.getProductname2(),String.valueOf(temp.getNum()),temp.getUnit(),temp.getOutputdate(),temp.getDemand(),String.valueOf(temp.getPrice())};
            list.add(arr);
        }
        try {
            Document document = new Document(PageSize.A4.rotate());
            File f = File.createTempFile("野马电池采购订单", ".pdf");
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
            Paragraph paragraphOne = new Paragraph("野马电池采购订单",titleFont);
            paragraphOne.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraphOne);
            //标题下附注
            Paragraph paragraph1 = new Paragraph("供应商:松旦包装印刷有限公司",nomalFont);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph1);


            // table1
            PdfPTable table1 = new PdfPTable(10);
            table1.setWidthPercentage(100); // Width 100%
            String[] ths = {"采购流水号", "作业单号", "物料代码","物料名称", "别名", "数量","单位","交货日期","生产要求及噱头","含税单价"};
            for (int i = 0; i < ths.length; i++) {
                Paragraph para = new Paragraph(ths[i], thFont);
                para.setAlignment(Element.ALIGN_CENTER);
                PdfPCell cell = new PdfPCell(para);
                cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table1.addCell(cell);
            }
            document.add(table1);

            // table2

            for (int i = 0; i < list.size(); i++) {
                PdfPTable table2 = new PdfPTable(10);
                table2.setWidthPercentage(100); // Width 100%
                String[] arr = (String[]) list.get(i);
                for (int j = 0; j < arr.length; j++) {
                    Paragraph para = new Paragraph(arr[j], nomalFont);
                    para.setAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell = new PdfPCell(para);
                    cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
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
    public Object printProduceOrder(Object nums, HttpServletResponse response) {
        return null;
    }


}