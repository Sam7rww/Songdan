package com.songdan.system.dao;

import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;


@Repository
public interface YMUnprintOrderDao extends JpaRepository<YMUnprintOrder,String>, JpaSpecificationExecutor<YMUnprintOrder> {
    //添加
    public YMUnprintOrder save(YMUnprintOrder order);

    //获得全部
    //@Query(nativeQuery = true, value = "select * from ymunprintorder o where o.state = 0")
    @Query("select o from YMUnprintOrder o where o.state = 0")
    public List<YMUnprintOrder> findAll();

    //获得某日输入的全部订单
    //@Query(nativeQuery = true, value = "select * from ymunprintorder o where o.state = 0 and o.inputdate = ?1")
    @Query("select o from YMUnprintOrder o where o.state = 0 and o.inputdate = ?1")
    public List<YMUnprintOrder> findAllByInputdate(Date date);

    //获得一条
    public YMUnprintOrder findByWaterid(String waterid);

    //删去一条
    @Modifying
    @Transactional
    public void deleteByWaterid(String waterid);

    //查询存在
    public boolean existsByWaterid(String waterid);

    //条件查询
    public List<YMUnprintOrder> findAll(Specification<YMUnprintOrder> spec);

    //完成打印，更新状态
    @Modifying
    @Transactional
    @Query("update YMUnprintOrder a set a.state = ?2 where a.waterid = ?1")
    //@Query("update YMUnprintOrder set state =:state where waterid =:waterid")
    public void updateState(@Param("waterid") String waterid, @Param("state") int state);

    @Modifying
    @Transactional
    @Query("update YMUnprintOrder a set a.num=?2, a.outputdate=?3, a.price=?4, a.state=?5, a.demand=?6 where a.waterid = ?1")
    public void updateInfo(@Param("waterid") String waterid, @Param("num") int num,
                           @Param("outputdate") String outputdate,@Param("price") double price,
                           @Param("state") int state,@Param("demand") String demand);

}
