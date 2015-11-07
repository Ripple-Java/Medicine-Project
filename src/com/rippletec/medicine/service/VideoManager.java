package com.rippletec.medicine.service;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.Video;

public interface VideoManager extends IManager<Video> {
    
    public static final String NAME = "VideoManager";

    List<Video> findRecentMeeting(PageBean pageBean, String param, Object value);

    void active(int id);

}
