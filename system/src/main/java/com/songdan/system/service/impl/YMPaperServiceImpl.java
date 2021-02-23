package com.songdan.system.service.impl;

import com.songdan.system.dao.YMpaperDao;
import com.songdan.system.model.Entity.wildhorse.YMpaper;
import com.songdan.system.service.YMPaperService;
import com.songdan.system.service.util.ComputeNeiJingYM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class YMPaperServiceImpl implements YMPaperService {

    @Autowired
    private YMpaperDao ympaperdao;

    @Override
    public String savepaper(String id, String name, String name2, String gecengban, String type, String neijing) {
        boolean exist = ympaperdao.existsByProductid(id);
        if(exist){
            return "图纸编号已存在，请确认。";
        }
        boolean exist2 = ympaperdao.existsByProductname(name);
        if(exist2){
            return "图纸名字已存在，请确认。";
        }
        ComputeNeiJingYM com = new ComputeNeiJingYM(neijing,type);
        if(!com.assertInput()){
            return "内径输入格式有误，请检查。";
        }
        YMpaper paper = new YMpaper(id,name,name2,neijing,com.getWaiJing(),com.getBanPian(),com.getYaXian(),type,gecengban);
        YMpaper res = ympaperdao.save(paper);
        if(res == null){
            return "图纸保存失败，请检查输入。";
        }else{
            return "";
        }
    }

    @Override
    public String updatepaper(String id, String name, String name2, String gecengban, String type,
                              String neijing, String waijing, String banpian, String yaxian) {
        boolean exist = ympaperdao.existsByProductname(name);
        if(exist){
            ympaperdao.update(id,name,name2,gecengban,type,neijing,waijing,banpian,yaxian);
        }else{
            return "查找不到需要修改图纸,图纸名字不可修改";
        }

        return "";
    }

    @Override
    public YMpaper searchpaper(String id, String name) {
        YMpaper paper = null;
        if(!name.equals("")){
            paper = ympaperdao.findByProductname(name);
        }else if(!id.equals("")){
            paper = ympaperdao.findByProductid(id);
        }else{
            return paper;
        }

        return paper;
    }
}
