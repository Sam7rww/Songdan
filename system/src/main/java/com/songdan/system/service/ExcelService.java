package com.songdan.system.service;

import com.songdan.system.model.vo.YMmailOrder;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    /**
     * 处理多sheet的Excel
     * @param
     * @return
     */
    public List<YMmailOrder> mergeSheets(InputStream inputStream);

}
