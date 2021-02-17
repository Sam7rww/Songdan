package com.songdan.system.dao.search;

import com.songdan.system.dao.search.YMUnprintSearchDao;
import com.songdan.system.model.Entity.wildhorse.YMUnprintOrder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import java.util.List;

@Repository
public class YMUnprintImpl implements YMUnprintSearchDao {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public List<YMUnprintOrder> search(String ordernum, String productname, String outputdate) {
        StringBuffer buffer = new StringBuffer("select * from ymunprint_order where 1=1");
        System.out.println("Search order param: ordernum="+ordernum+",productname="+productname+",outputdate="+outputdate);
        if(!ordernum.equals("")){
            buffer.append(" and ordernum='"+ ordernum+"'");
        }
        if(!productname.equals("")){
            buffer.append(" and productname like '%"+productname+"%'");
        }
        if(!outputdate.equals("")){
            buffer.append(" and outputdate='"+outputdate+"'");
        }
        String sql = buffer.toString();
        System.out.println("sql:"+sql);
        EntityManager em = emf.createEntityManager();
        Query query = em.createNativeQuery(sql,YMUnprintOrder.class);
        List<YMUnprintOrder> list = query.getResultList();
        em.close();


        return list;
    }
}
