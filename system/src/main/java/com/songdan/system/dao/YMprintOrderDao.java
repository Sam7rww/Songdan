package com.songdan.system.dao;

import com.songdan.system.model.Entity.wildhorse.YMprintOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface YMprintOrderDao extends JpaRepository<YMprintOrder,String> {

    //添加
    public YMprintOrder save(YMprintOrder order);

    //删去
    @Modifying
    @Transactional
    public void deleteByWaterid(String waterid);

    public boolean existsByWaterid(String waterid);

}
