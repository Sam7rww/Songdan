package com.songdan.system.service;

import com.songdan.system.model.Entity.wildhorse.YMpaper;

public interface YMPaperService {
    public String savepaper(String id,String name,String name2,String gecengban,String type, String neijing);

    public String updatepaper(String id,String name,String name2,String gecengban,String type,
                              String neijing,String waijing,String banpian,String yaxian);

    public YMpaper searchpaper(String id, String name);
}
