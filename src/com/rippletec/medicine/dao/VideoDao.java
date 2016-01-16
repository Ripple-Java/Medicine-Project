package com.rippletec.medicine.dao;

import java.util.List;

import com.rippletec.medicine.bean.PageBean;
import com.rippletec.medicine.model.Video;

public interface VideoDao extends FindAndSearchDao<Video> {
    
    public static final String NAME = "VideoDao";
    
    public List<Video> findByTime(PageBean page, String param, Object value) ;

}
