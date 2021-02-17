package com.songdan.system.dao.search;

import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;

import java.util.List;


public interface YMUnprintSearchDao {

    //搜索
    public List<YMUnprintOrder> search(String ordernum, String productname, String outputdate);
}
