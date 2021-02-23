package com.songdan.system.dao;

import com.songdan.system.model.Entity.doubledear.SLpaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SLpaperDao extends JpaRepository<SLpaper,String> {

    public SLpaper save(SLpaper paper);

    //查询是否存在
    //@Query(nativeQuery = true,value = "select * from ympaper p where p.productid = ?2 and p.productname = ?1")
    public boolean existsByProductid(String productid);

    public boolean existsByProductname(String productname);

    public SLpaper findByProductid(String productid);

    public SLpaper findByProductname(String productname);

    @Modifying
    @Transactional
    @Query("update SLpaper p set p.productid=?1,p.press=?3,p.type=?4,p.neijing=?5,p.waijing=?6,p.banpian=?7,p.yaxian=?8 where p.productname=?2")
    public void update(@Param("productid") String id, @Param("productname") String name,
                       @Param("press") String press, @Param("type") String t, @Param("neijing") String n,
                       @Param("waijing") String w, @Param("banpian") String b, @Param("yaxian") String y);
}
