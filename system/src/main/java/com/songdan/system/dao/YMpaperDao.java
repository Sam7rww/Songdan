package com.songdan.system.dao;

import com.songdan.system.model.Entity.wildhorse.YMpaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface YMpaperDao extends JpaRepository<YMpaper,String> {
    //添加
    public YMpaper save(YMpaper paper);

    //查询是否存在
    //@Query(nativeQuery = true,value = "select * from ympaper p where p.productid = ?2 and p.productname = ?1")
    public boolean existsByProductid(String productid);

    public YMpaper findByProductid(String productid);
}
