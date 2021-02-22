package com.songdan.system.dao;

import com.songdan.system.model.Entity.wildhorse.YMpaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface YMpaperDao extends JpaRepository<YMpaper,String> {
    //添加
    public YMpaper save(YMpaper paper);

    //查询是否存在
    //@Query(nativeQuery = true,value = "select * from ympaper p where p.productid = ?2 and p.productname = ?1")
    public boolean existsByProductid(String productid);

    public boolean existsByProductname(String productname);

    public YMpaper findByProductid(String productid);

    public YMpaper findByProductname(String productname);

    @Modifying
    @Transactional
    @Query("update YMpaper p set p.productid=?1,p.productname2=?3,p.gecengban=?4,p.type=?5,p.neijing=?6,p.waijing=?7,p.banpian=?8,p.yaxian=?9 where p.productname=?2")
    public void update(@Param("productid") String id,@Param("productname") String name,@Param("productname2") String name2,
                          @Param("gecengban") String gcb,@Param("type") String t,@Param("neijing") String n,
                          @Param("waijing") String w,@Param("banpian") String b,@Param("yaxian") String y);
}
