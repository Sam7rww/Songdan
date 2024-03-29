package com.songdan.system.service;

import com.songdan.system.model.vo.YMOrder;
import com.songdan.system.model.vo.YMmailOrder;
import com.songdan.system.model.vo.YMmrgOrder;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    /**
     * 处理多sheet的Excel
     * @param
     * @return
     */
    public List<YMmailOrder> mergeSheets(InputStream inputStream);

    /**
     * 合并多个Excel
     * @param
     * @return
     */
    public String mergeExcels(InputStream inputStream);

    /**
     * 清空静态Excel list;
     * @param
     * @return
     */
    public List<YMmrgOrder> clearExcels();

    /**
     * 云星空新系统的 查询订单图纸
     * @param
     * @return
     */
    public List<YMOrder> searchExcel(InputStream inputStream);

}
