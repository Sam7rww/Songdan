package com.songdan.system.dao;

import com.songdan.system.model.Entity.doubledear.SLpaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SLpaperDao extends JpaRepository<SLpaper,String> {

    public SLpaper save(SLpaper paper);

    //查询是否存在
    //@Query(nativeQuery = true,value = "select * from ympaper p where p.productid = ?2 and p.productname = ?1")
    public boolean existsByProductid(String productid);

    public SLpaper findByProductid(String productid);
}
