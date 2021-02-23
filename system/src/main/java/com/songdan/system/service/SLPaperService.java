package com.songdan.system.service;

import com.songdan.system.model.Entity.doubledear.SLpaper;

public interface SLPaperService {

    public String savepaper(String id,String name,String press,String type, String neijing);

    public String updatepaper(String id,String name,String press,String type,
                              String neijing,String waijing,String banpian,String yaxian);

    public SLpaper searchpaper(String id, String name);
}
