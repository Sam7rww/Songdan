package com.songdan.system.dao;

import com.songdan.system.model.Entity.doubledear.SLUnprintOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SLUnprintOrderDao extends JpaRepository<SLUnprintOrder,Integer>, JpaSpecificationExecutor<SLUnprintOrder> {
    //添加
    public SLUnprintOrder save(SLUnprintOrder order);

    //获得全部
    @Query("select s from SLUnprintOrder s where s.state = 0")
    public List<SLUnprintOrder> findAll();

    //条件查询
    public List<SLUnprintOrder> findAll(Specification<SLUnprintOrder> sf);

    //完成打印，更新状态
    @Modifying
    @Transactional
    @Query("update SLUnprintOrder s set s.state = ?2 where s.id = ?1")
    public void updateState(@Param("id") int id, @Param("state") int state);

    //查询存在
    public boolean existsByOrderlineAndProductid(@Param("orderline") String orderline,@Param("productid") String productid);

    public SLUnprintOrder findById(int id);
}
