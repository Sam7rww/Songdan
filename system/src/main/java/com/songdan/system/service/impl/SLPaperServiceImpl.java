package com.songdan.system.service.impl;

import com.songdan.system.dao.SLpaperDao;
import com.songdan.system.model.Entity.doubledear.SLpaper;
import com.songdan.system.service.SLPaperService;
import com.songdan.system.service.util.ComputeNeijingSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SLPaperServiceImpl implements SLPaperService {

    @Autowired
    private SLpaperDao sLpaperDao;

    @Override
    public String savepaper(String id, String name, String press, String type, String neijing,String position) {
        boolean exist = sLpaperDao.existsByProductid(id);
        if(exist){
            return "图纸编号已存在，请确认。";
        }
        ComputeNeijingSL com = new ComputeNeijingSL(neijing,type);
        if(!com.assertInput()){
            return "内径输入格式有误，请检查。";
        }
        SLpaper paper = new SLpaper(id,name,neijing,com.getWaiJing(),com.getBanPian(),com.getYaXian(),type,press);
        paper.setPostion(position);
        SLpaper res = sLpaperDao.save(paper);
        if(res == null){
            return "图纸保存失败，请检查输入。";
        }else{
            return "";
        }
    }

    @Override
    public String updatepaper(String id, String name, String press, String type, String neijing, String waijing, String banpian, String yaxian,String position) {
        boolean exist = sLpaperDao.existsByProductid(id);
        if(exist){
            sLpaperDao.update(id,name,press,type,neijing,waijing,banpian,yaxian,position);
        }else{
            return "查找不到需要修改图纸,图纸名字不可修改";
        }

        return "";
    }

    @Override
    public SLpaper searchpaper(String id, String name) {
        SLpaper paper = null;
        if(!id.equals("")){
            paper = sLpaperDao.findByProductid(id);
        }else{
            return paper;
        }
        return paper;
    }
}
