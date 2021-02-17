package com.songdan.system.dao;

import com.songdan.system.model.Entity.wildhorse.YMprintOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YMprintOrderDao extends JpaRepository<YMprintOrder,String> {

    //添加
    public YMprintOrder save(YMprintOrder order);


}
