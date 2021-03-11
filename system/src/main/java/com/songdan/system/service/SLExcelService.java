package com.songdan.system.service;

import com.songdan.system.model.vo.SLmrgOrder;

import java.io.InputStream;
import java.util.List;

public interface SLExcelService {
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
    public List<SLmrgOrder> clearExcels();
}
